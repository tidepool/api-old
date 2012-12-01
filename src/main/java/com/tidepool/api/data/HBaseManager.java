package com.tidepool.api.data;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.DateFormatter;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RandomRowFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tidepool.api.model.Account;
import com.tidepool.api.model.CodedAttribute;
import com.tidepool.api.model.CodedAttributeGroup;
import com.tidepool.api.model.CodedItem;
import com.tidepool.api.model.CodedItemLog;
import com.tidepool.api.model.CodingEvent;
import com.tidepool.api.model.CodingEventRollup;
import com.tidepool.api.model.CodingGroup;
import com.tidepool.api.model.Factor;
import com.tidepool.api.model.Highlight;
import com.tidepool.api.model.Invite;
import com.tidepool.api.model.MainGroup;
import com.tidepool.api.model.Photo;
import com.tidepool.api.model.RowMapper;
import com.tidepool.api.model.Team;
import com.tidepool.api.model.TeamAccount;
import com.tidepool.api.model.TrainingItem;

public class HBaseManager {
	
	private static final int HTABLE_POOL_SIZE = 10;

	@Value("${tidepool.hbase.quorum}") 
	private String hbaseQuorum;
	
	@Value("${tidepool.hbase.clientPort}") 
	private String hbaseClientPort;
	
	@Value("${tidepool.hbase.master}") 
	private String hbaseMaster;
	
	@Autowired
	private ShaPasswordEncoder encoder;
	
	private static HBaseManager instance = new HBaseManager();
	private Configuration conf = null;
	private HTablePool pool = null;
	
	private final String implicitElementTable = "implicit_element";
	private final String accountTable = "user_attributes";
	private final String elementDetailTable = "element_detail";
	private final String elementGroupTable = "element_group";
	private final String elementMainTable = "element_main";
	private final String explicitEventTable= "explicit_event";
	private final String teamTable= "team";
	private final String teamAccountMapTable= "team_account_map";
	private final String inviteTable= "invite";
	
	private final String explicitImageTable= "explicit_image";
	//private final String explicitImageTable= "image_content_detail";
	
	private final String explicitImageLogTable= "explicit_image_log";
	private final String countersTable= "counters";
		
	private final String trainingImageTable= "explicit_training_image";
	private final String trainingEventTable= "explicit_training_event";
	
	private final String factorTable = "element_factor";
	private final String elementBig5Table = "element_big_5";
	
	private final byte[] family_name_column = Bytes.toBytes("cf");
	
	private final byte[] pic_name_column = Bytes.toBytes("picture_id");
	
	public HBaseManager() {
		
	}
	
	public void initialize() {
		 conf = HBaseConfiguration.create();
		 conf.set("hbase.zookeeper.quorum", hbaseQuorum); 
		 conf.set("hbase.zookeeper.property.clientPort", hbaseClientPort);
		 conf.set("hbase.master", hbaseMaster);
		 pool = new HTablePool(conf, HTABLE_POOL_SIZE);
	}
	
	public static HBaseManager getInstance() {
		instance.initialize();
		return instance;
	}


	private Account getAccountFromId(String id) {
		Account account = null;
				
		try {
			HTableInterface table = pool.getTable(accountTable);
			Get get = new Get(Bytes.toBytes(id));					
			Result result = table.get(get);							
			account = new Account();				
			mapAccount(account, result);				
			return account;
			
		} catch(Exception e) {
			e.printStackTrace();
		} 

		return account;
	}
	
	public Account getAccountFromEmailInternal(String email) {
		Account account = null;
		ResultScanner scanner  = null;
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				family_name_column,
				Account.email_column,
				CompareOp.EQUAL,
				Bytes.toBytes(email)
		);
		filter.setFilterIfMissing(true);
		try {
			HTableInterface table = pool.getTable(accountTable);
			Scan scan = new Scan();
			
			scan.setFilter(filter);
			scanner = table.getScanner(scan);					
			for (Result result : scanner) {
				account = new Account();				
				mapAccount(account, result);				
				return account;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			scanner.close();
		}

		return account;
	}
	
	public Account getAccountFromEmail(String email) throws UsernameNotFoundException {
		Account account = getAccountFromEmailInternal(email);
		if (account == null)
			throw new UsernameNotFoundException(email);

		return account;
	}

	private void mapAccount(Account account, Result result) {
		account.setUserId(Bytes.toString(result.getRow()));
		
		if (result.containsColumn(family_name_column, Account.email_column)) {
			byte[] val = result.getValue(family_name_column, Account.email_column);
			account.setEmail(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.user_name_column)) {
			byte[] val = result.getValue(family_name_column, Account.user_name_column);
			account.setUsername(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.password_column)) {
			byte[] val = result.getValue(family_name_column, Account.password_column);
			account.setPassword(Bytes.toString(val));					
		}
		if (result.containsColumn(family_name_column, Account.element_group_id_column)) {
			byte[] val = result.getValue(family_name_column, Account.element_group_id_column);
			account.setElementGroupId(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.first_name)) {
			byte[] val = result.getValue(family_name_column, Account.first_name);
			account.setFirstName(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.last_name)) {
			byte[] val = result.getValue(family_name_column, Account.last_name);
			account.setLastName(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.birth_year_column)) {
			byte[] val = result.getValue(family_name_column, Account.birth_year_column);
			account.setBirthYear(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.education_column)) {
			byte[] val = result.getValue(family_name_column, Account.education_column);
			account.setEducation(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.gender_column)) {
			byte[] val = result.getValue(family_name_column, Account.gender_column);
			account.setGender(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.zipcode_column)) {
			byte[] val = result.getValue(family_name_column, Account.zipcode_column);
			account.setZipCode(Bytes.toString(val));					
		}
						
		if (result.containsColumn(family_name_column, Account.registration_level_id_column)) {
			byte[] val = result.getValue(family_name_column, Account.registration_level_id_column);
			account.setRegistrationLevel(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.explicit_image_folder_column)) {
			byte[] val = result.getValue(family_name_column, Account.explicit_image_folder_column);
			account.setExplicitImageFolder(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.account_status_column)) {
			byte[] val = result.getValue(family_name_column, Account.account_status_column);
			account.setAccountStatus(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.extraverted_column)) {
			byte[] val = result.getValue(family_name_column, Account.extraverted_column);
			account.setExtraverted(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.critical_column)) {
			byte[] val = result.getValue(family_name_column, Account.critical_column);
			account.setCritical(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.dependable_column)) {
			byte[] val = result.getValue(family_name_column, Account.dependable_column);
			account.setDependable(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.anxious_column)) {
			byte[] val = result.getValue(family_name_column, Account.anxious_column);
			account.setAnxious(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.open_column)) {
			byte[] val = result.getValue(family_name_column, Account.open_column);
			account.setOpen(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.reserved_column)) {
			byte[] val = result.getValue(family_name_column, Account.reserved_column);
			account.setReserved(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.sympathetic_column)) {
			byte[] val = result.getValue(family_name_column, Account.sympathetic_column);
			account.setSympathetic(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.disorganized_column)) {
			byte[] val = result.getValue(family_name_column, Account.disorganized_column);
			account.setDisorganized(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.calm_column)) {
			byte[] val = result.getValue(family_name_column, Account.calm_column);
			account.setCalm(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.conventional_column)) {
			byte[] val = result.getValue(family_name_column, Account.conventional_column);
			account.setConventional(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.artistic_appeal_column)) {
			byte[] val = result.getValue(family_name_column, Account.artistic_appeal_column);
			account.setArtistic_appeal(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.effective_user_interface_column)) {
			byte[] val = result.getValue(family_name_column, Account.effective_user_interface_column);
			account.setEffective_user_interface(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.interest_in_measurement_column)) {
			byte[] val = result.getValue(family_name_column, Account.interest_in_measurement_column);
			account.setInterest_in_measurement(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.understanding_personality_column)) {
			byte[] val = result.getValue(family_name_column, Account.understanding_personality_column);
			account.setUnderstanding_personality(Bytes.toInt(val));					
		}
	
		if (result.containsColumn(family_name_column, Account.interesting_dating_partners_column)) {
			byte[] val = result.getValue(family_name_column, Account.interesting_dating_partners_column);
			account.setInteresting_dating_partners(Bytes.toInt(val));					
		}
		
		if (result.containsColumn(family_name_column, Account.assess_code_column)) {
			byte[] val = result.getValue(family_name_column, Account.assess_code_column);
			account.setAssessCode((Bytes.toString(val)));					
		}
				
		if (result.containsColumn(family_name_column, Account.company_column)) {
			byte[] val = result.getValue(family_name_column, Account.company_column);
			account.setCompany((Bytes.toString(val)));					
		}
		
		if (result.containsColumn(family_name_column, Account.phone_number_column)) {
			byte[] val = result.getValue(family_name_column, Account.phone_number_column);
			account.setPhoneNumber((Bytes.toString(val)));					
		}
		
		if (result.containsColumn(family_name_column, Account.age_column)) {
			byte[] val = result.getValue(family_name_column, Account.age_column);
			account.setAge((Bytes.toString(val)));					
		}
		
		if (result.containsColumn(family_name_column, Account.job_title_column)) {
			byte[] val = result.getValue(family_name_column, Account.job_title_column);
			account.setJobTitle((Bytes.toString(val)));					
		}
				
	}
	
	public void saveAccount(Account account) {
		try {
			HTableInterface table = pool.getTable(accountTable);
			Put put = new Put(Bytes.toBytes(account.getUserId()));
			
			if (account.getEmail() != null) {
				put.add(family_name_column, Account.email_column, Bytes.toBytes(account.getEmail()));
			}
			
			if (account.getPassword() != null) {
				put.add(family_name_column, Account.password_column, Bytes.toBytes(account.getPassword()));
			}
			
			if (account.getFirstName() != null) {
				put.add(family_name_column, Account.first_name, Bytes.toBytes(account.getFirstName()));
			}
			
			if (account.getLastName() != null) {
				put.add(family_name_column, Account.last_name, Bytes.toBytes(account.getLastName()));	
			}
			
			if (account.getBirthYear() != null) {
				put.add(family_name_column, Account.birth_year_column, Bytes.toBytes(account.getBirthYear()));
			}
			
			if (account.getEducation() != null) {
				put.add(family_name_column, Account.education_column, Bytes.toBytes(account.getEducation()));
			}
			
			if (account.getGender() != null) {
				put.add(family_name_column, Account.gender_column, Bytes.toBytes(account.getGender()));
			}
			
			if (account.getZipCode() != null) {
				put.add(family_name_column, Account.zipcode_column, Bytes.toBytes(account.getZipCode()));
			}
			
			put.add(family_name_column, Account.registration_level_id_column, Bytes.toBytes(account.getRegistrationLevel()));
			
			if (account.getElementGroupId() != null) {
				put.add(family_name_column, Account.element_group_id_column, Bytes.toBytes(account.getElementGroupId()));
			}
			
			if (account.getExplicitImageFolder()!= null) {
				put.add(family_name_column, Account.explicit_image_folder_column, Bytes.toBytes(account.getExplicitImageFolder()));
			}			
			
			if (account.getAccountStatus() != null) {
				put.add(family_name_column, Account.account_status_column, Bytes.toBytes(account.getAccountStatus()));
			}	
			
			put.add(family_name_column, Account.extraverted_column, Bytes.toBytes(account.getExtraverted()));
			put.add(family_name_column, Account.critical_column, Bytes.toBytes(account.getCritical()));
			put.add(family_name_column, Account.dependable_column, Bytes.toBytes(account.getDependable()));
			put.add(family_name_column, Account.anxious_column, Bytes.toBytes(account.getAnxious()));
			put.add(family_name_column, Account.open_column, Bytes.toBytes(account.getOpen()));
			put.add(family_name_column, Account.reserved_column, Bytes.toBytes(account.getReserved()));
			put.add(family_name_column, Account.sympathetic_column, Bytes.toBytes(account.getSympathetic()));
			put.add(family_name_column, Account.disorganized_column, Bytes.toBytes(account.getDisorganized()));
			put.add(family_name_column, Account.calm_column, Bytes.toBytes(account.getCalm()));
			put.add(family_name_column, Account.conventional_column, Bytes.toBytes(account.getConventional()));
			
			put.add(family_name_column, Account.artistic_appeal_column, Bytes.toBytes(account.getArtistic_appeal()));
			put.add(family_name_column, Account.effective_user_interface_column, Bytes.toBytes(account.getEffective_user_interface()));
			put.add(family_name_column, Account.interest_in_measurement_column, Bytes.toBytes(account.getInterest_in_measurement()));
			put.add(family_name_column, Account.understanding_personality_column, Bytes.toBytes(account.getUnderstanding_personality()));
			put.add(family_name_column, Account.interesting_dating_partners_column, Bytes.toBytes(account.getInteresting_dating_partners()));
			
			if (account.getIp() != null) {
				put.add(family_name_column, Account.ip_column, Bytes.toBytes(account.getIp()));
			}
			
			if (account.getAssessCode() != null) { 
				put.add(family_name_column, Account.assess_code_column, Bytes.toBytes(account.getAssessCode()));
			}
			
			if (account.getCompany() != null) {
				put.add(family_name_column, Account.company_column, Bytes.toBytes(account.getCompany()));
			}
			
			if (account.getPhoneNumber() != null) {
				put.add(family_name_column, Account.phone_number_column, Bytes.toBytes(account.getPhoneNumber()));
			}
			
			if (account.getAge() != null) {
				put.add(family_name_column, Account.age_column, Bytes.toBytes(account.getAge()));
			}
			
			if (account.getJobTitle() != null) {
				put.add(family_name_column, Account.job_title_column, Bytes.toBytes(account.getJobTitle()));
			}
			
			table.put(put);			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			
		}	
	}
	
	public Account createAssessAccount(Account account) throws Exception{
			
			HTableInterface counter = pool.getTable(countersTable);
			try {
				long nextCounter = counter.incrementColumnValue(Bytes.toBytes("6"), family_name_column, Bytes.toBytes(accountTable), 1);
				account.setUserId(String.valueOf(nextCounter));
				saveAccount(account);
			} catch (IOException e) {			
				e.printStackTrace();
			}
			
			return account;
		}
		
	
	public Account createAccount(Account account) throws Exception{
		
		if (getAccountFromEmailInternal(account.getEmail()) != null) {
			throw new Exception("Account " + account.getEmail() + " already exists....");
		}
		
		HTableInterface counter = pool.getTable(countersTable);
		try {
			long nextCounter = counter.incrementColumnValue(Bytes.toBytes("6"), family_name_column, Bytes.toBytes(accountTable), 1);
			account.setUserId(String.valueOf(nextCounter));
			saveAccount(account);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		return account;
	}
	
	
	
	public List<Photo> getAllPhotos() {
		List<Photo> photos = new ArrayList<Photo>();		
		ResultScanner scanner  = null;
		try {
			HTableInterface table = pool.getTable(implicitElementTable);
			Scan scan = new Scan();
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {			
				Photo photo = new Photo();
				if (result.containsColumn(family_name_column, pic_name_column)) {
					byte[] val = result.getValue(family_name_column, pic_name_column);
					photo.setSrc(Bytes.toString(val));
				}
				photos.add(photo);			
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		return photos;
	}
	
		
	public Photo getPhotoFromPicName(String picName) {		
		Photo photo = null;
		ResultScanner scanner  = null;
		
		Filter filter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(picName));
		try {
			HTableInterface table = pool.getTable(implicitElementTable);
			Scan scan = new Scan();
			scan.setFilter(filter);
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {			
				Photo foundPhoto = new Photo();
				if (result.containsColumn(family_name_column, pic_name_column)) {
					byte[] val = result.getValue(family_name_column, pic_name_column);
					foundPhoto.setSrc(Bytes.toString(val));
				}
				return foundPhoto;				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			scanner.close();
		}		
		return photo;		
	}
	
	
	public CodedItem getCodedItemFromId(String id) {		
		CodedItem codedItem = null;

		try {
			HTableInterface table = pool.getTable(implicitElementTable);
			Get get = new Get(Bytes.toBytes(id));		
			Result result = table.get(get);

			codedItem = new CodedItem();								
			mapResultToCodedItem(codedItem, result);

		} catch(Exception e) {
			e.printStackTrace();
		} finally {}		
		return codedItem;	
	}
	
	
	public CodedItem getImageItemFromId(String id) {		
		CodedItem codedItem = null;
		try {
			HTableInterface table = pool.getTable(explicitImageTable);
			Get get = new Get(Bytes.toBytes(id));

			Result result = table.get(get);		
			codedItem = new CodedItem();								
			mapResultToCodedItem(codedItem, result);
			return codedItem;				
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			
		}		
		return codedItem;	
	}
	
public List<CodedItem> getFolderCodedItems(String folderType) {
		
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				family_name_column,
				Bytes.toBytes("folder_name"),
				CompareOp.EQUAL,
				Bytes.toBytes(folderType)
		);
		
		List<CodedItem> codedItems = new ArrayList<CodedItem>();
		ResultScanner scanner  = null;				
		try {
			HTableInterface table = pool.getTable(explicitImageTable);
			Scan scan = new Scan();
			scan.setFilter(filter);
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {			
				CodedItem codedItem = new CodedItem();								
				mapResultToCodedItem(codedItem, result);
				codedItems.add(codedItem);			
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			scanner.close();
		}
		
		Collections.sort(codedItems, new Comparator<CodedItem>() {
			public int compare(CodedItem o1, CodedItem o2) {
				if (o1.order_id == null) return -1;
				if (o2.order_id == null) return 1;
				return new Integer(o1.order_id).compareTo(new Integer(o2.order_id));
			}			
		});
				
		return codedItems;
	}

public List<CodedItem> getFolderCodedItemsForPictures(String folderType, String... pictureIds) {

	FilterList mainListFilter = new FilterList(FilterList.Operator.MUST_PASS_ONE);	
	SingleColumnValueFilter folderFilter = new SingleColumnValueFilter(
			family_name_column,
			Bytes.toBytes("folder_name"),
			CompareOp.EQUAL,
			Bytes.toBytes(folderType)
	);

	for (String pictureId : pictureIds) {		
		RowFilter rowFilter = new RowFilter(CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(pictureId.toString())) );
		FilterList rowList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		rowList.addFilter(folderFilter);
		rowList.addFilter(rowFilter);
		mainListFilter.addFilter(rowList);
	}

	List<CodedItem> codedItems = new ArrayList<CodedItem>();
	ResultScanner scanner  = null;				
	try {
		HTableInterface table = pool.getTable(explicitImageTable);
		Scan scan = new Scan();
		scan.setFilter(mainListFilter);
		scanner = table.getScanner(scan);		
		for (Result result : scanner) {			
			CodedItem codedItem = new CodedItem();								
			mapResultToCodedItem(codedItem, result);
			codedItems.add(codedItem);			
		}
	} catch(Exception e) {
		e.printStackTrace();
	} finally {			
		scanner.close();
	}

	return codedItems;
}


	
	public CodedItem getFolderCodedItem(String userId, String folderType) {
		
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				family_name_column,
				Bytes.toBytes("folder_name"),
				CompareOp.EQUAL,
				Bytes.toBytes(folderType)
		);
		
		CodedItem codedItem = null;
		CodedItem foundCodedItem = null;
		ResultScanner scanner  = null;				
		
		List<CodedItemLog> alreadyCoded = getCodedItemsForUserAndFolder(userId, folderType);
		HashMap<String, CodedItemLog> alreadyCodedMap = new HashMap<String, CodedItemLog>();
		for (CodedItemLog log : alreadyCoded) {
			alreadyCodedMap.put(log.getExplicitImageId(), log);
		}
		
		try {
			HTableInterface table = pool.getTable(explicitImageTable);
			Scan scan = new Scan();
			scan.setFilter(filter);
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {			
				codedItem = new CodedItem();								
				mapResultToCodedItem(codedItem, result);
				if (codedItem.getPicture_id() != null && !alreadyCodedMap.containsKey(codedItem.getId())) {
					foundCodedItem = codedItem;
					return foundCodedItem;				
				}				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			scanner.close();
		}
		
		return codedItem;
	}
	
	public void saveCodedItemLog(CodedItemLog log) {
		
		try {
						
			HTableInterface counter = pool.getTable(countersTable);
			long nextCounter = counter.incrementColumnValue(Bytes.toBytes("7"), family_name_column, Bytes.toBytes(explicitImageLogTable), 1);
			log.setId(nextCounter);
			
			HTableInterface table = pool.getTable(explicitImageLogTable);
			Put put = new Put(Bytes.toBytes(log.getId()));
			
			if (log.getExplicitImageId() != null) {
				put.add(family_name_column, CodedItemLog.explicit_image_id_column, Bytes.toBytes(log.getExplicitImageId()));
			}
			
			put.add(family_name_column,CodedItemLog.folder_type_column, Bytes.toBytes(log.getFolderType()));
			put.add(family_name_column,CodedItemLog.user_id_column, Bytes.toBytes(log.getUserId()));				
			table.put(put);		
								
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			
		}
	}
	
	public List<CodedItemLog> getCodedItemsForUserAndFolder(String id, String folder) {
		
		List<CodedItemLog> logs = new ArrayList<CodedItemLog>();
		
		List<Filter> filters = new ArrayList<Filter>();
		SingleColumnValueFilter filter0 = new SingleColumnValueFilter(
				family_name_column,
				CodedItemLog.folder_type_column,
				CompareOp.EQUAL,
				Bytes.toBytes(folder)
		);
		filters.add(filter0);
		
		SingleColumnValueFilter filter1 = new SingleColumnValueFilter(
				family_name_column,
				CodedItemLog.user_id_column,
				CompareOp.EQUAL,
				Bytes.toBytes(id)
		);
		filters.add(filter1);
		FilterList filterList = new FilterList(filters);
		
		HTableInterface table = pool.getTable(explicitImageLogTable);
		Scan scan = new Scan();
		scan.setFilter(filterList);
		ResultScanner scanner = null;
		try {
			scanner = table.getScanner(scan);
			for (Result result : scanner) {
				
				CodedItemLog log = new CodedItemLog();
				log.setId(Bytes.toLong(result.getRow()));
				if (result.containsColumn(family_name_column, CodedItemLog.explicit_image_id_column)) {
					byte[] val = result.getValue(family_name_column, CodedItemLog.explicit_image_id_column);
					log.setExplicitImageId(Bytes.toString(val));
				}
					
				if (result.containsColumn(family_name_column, CodedItemLog.folder_type_column)) {
					byte[] val = result.getValue(family_name_column, CodedItemLog.folder_type_column);
					log.setFolderType(Bytes.toString(val));
				}
				
				if (result.containsColumn(family_name_column, CodedItemLog.user_id_column)) {
					byte[] val = result.getValue(family_name_column, CodedItemLog.user_id_column);
					log.setUserId(Bytes.toString(val));
				}				
				logs.add(log);				
			}
				
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			scanner.close();
		} 
		
		return logs;			
	}
	
	
	public CodedItem getRandomCodedItem(String userId, String folderType) {		
		
		if (!StringUtils.isEmpty(folderType)) {
			return getFolderCodedItem(userId, folderType);
		}
		
		CodedItem codedItem = null;
		ResultScanner scanner  = null;		
		RandomRowFilter filter = new RandomRowFilter(.01F);
		
		try {
			HTableInterface table = pool.getTable(explicitImageTable);
			Scan scan = new Scan();
			scan.setFilter(filter);
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {			
				codedItem = new CodedItem();								
				mapResultToCodedItem(codedItem, result);
				if (codedItem.getPicture_id() != null) {
					return codedItem;				
				}				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			scanner.close();
		}
		
		return 	getRandomCodedItem(userId, folderType);
	}
	
	
	public void setCodedItem(CodedItem item) {
		Class<CodedItem> codedItemClass = CodedItem.class;			
		try {
			HTableInterface table = pool.getTable(implicitElementTable);
			Put put = new Put(Bytes.toBytes(item.pic_name));			
			for (Field field : codedItemClass.getFields()) {
				if (field.getType().equals(Integer.TYPE)) {					
					put.add(family_name_column, Bytes.toBytes(field.getName()), Bytes.toBytes(field.getInt(item)));
				}
				if (field.getType().equals(String.class)) {					
					String value = (String)field.get(item);					
					if (value != null) {
						put.add(family_name_column, Bytes.toBytes(field.getName()), Bytes.toBytes(value));
					}
				}
			}
			table.put(put);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			
		}					
	}
	

	private void mapResultToCodedItem(CodedItem codedItem, Result result) throws IllegalAccessException {
		Class<CodedItem> codedItemClass = CodedItem.class;
		
		codedItem.id = Bytes.toString(result.getRow());
		for (Field field : codedItemClass.getFields()) {
			
			if (result.containsColumn(family_name_column, field.getName().getBytes())) {
				byte[] val = result.getValue(family_name_column, field.getName().getBytes());
				
				if (field.getType().equals(Integer.TYPE)) {
					field.set(codedItem, Bytes.toInt(val));
				}
				
				if (field.getType().equals(String.class)) {
					field.set(codedItem, Bytes.toString(val));
				}				
			}
			
			String x0 = field.getName() + "_x0";
			String y0 = field.getName() + "_y0";
			String x1 = field.getName() + "_x1";
			String y1 = field.getName() + "_y1";
			String height = field.getName() + "_height";
			String width = field.getName() + "_width";
			Highlight highlight = null;
			if (result.containsColumn(family_name_column, x0.getBytes())) {
				highlight = new Highlight();
				
				byte[] val = result.getValue(family_name_column, x0.getBytes());
				highlight.setX0(Bytes.toDouble(val));
				
				if (result.containsColumn(family_name_column, y0.getBytes())) {
					val = result.getValue(family_name_column, y0.getBytes());
					highlight.setY0(Bytes.toDouble(val));
				}
				
				if (result.containsColumn(family_name_column, x1.getBytes())) {
					val = result.getValue(family_name_column, x1.getBytes());
					highlight.setX1(Bytes.toDouble(val));
				}
				
				if (result.containsColumn(family_name_column, y1.getBytes())) {
					val = result.getValue(family_name_column, y1.getBytes());
					highlight.setY1(Bytes.toDouble(val));
				}
				
				if (result.containsColumn(family_name_column, height.getBytes())) {
					val = result.getValue(family_name_column, height.getBytes());
					highlight.setHeight(Bytes.toDouble(val));
				}
				
				if (result.containsColumn(family_name_column, width.getBytes())) {
					val = result.getValue(family_name_column, width.getBytes());
					highlight.setWidth(Bytes.toDouble(val));
				}
			}
			
			if (highlight != null) {
				codedItem.getHighlightMap().put(field.getName(), highlight);
			}
			
		}
	}

	public HashMap<String, CodedAttribute> getCodedElementsMap() {
		HashMap<String, CodedAttribute> map = new HashMap<String, CodedAttribute>();
		for (CodedAttribute attribute : getCodedElements()) {
			map.put(attribute.element, attribute);
		}
		return map;
	}
	
	public List<CodedAttribute> getCodedElements() {
		List<CodedAttribute> attributes = new ArrayList<CodedAttribute>();			
		ResultScanner scanner  = null;
		try {
			HTableInterface table = pool.getTable(elementDetailTable);
			Scan scan = new Scan();
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {			
				CodedAttribute attribute = new CodedAttribute();
				mapResultToCodedAttribute(attribute, result);
				attributes.add(attribute);			
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		return attributes;
	}
	
	public List<CodedAttribute> getCodedElementsForGroup(String groupId) {
		List<CodedAttribute> attributes = new ArrayList<CodedAttribute>();			
		ResultScanner scanner  = null;
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				family_name_column,
				CodedAttribute.element_group_id_column,
				CompareOp.EQUAL,
				Bytes.toBytes(groupId));
		try {
			HTableInterface table = pool.getTable(elementDetailTable);
			Scan scan = new Scan();
			scan.setFilter(filter);
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {			
				CodedAttribute attribute = new CodedAttribute();
				mapResultToCodedAttribute(attribute, result);
				attributes.add(attribute);			
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		return attributes;
	}
	
	
	private void mapResultToCodedAttribute(CodedAttribute codedAttribute, Result result) throws IllegalAccessException {
		Class<CodedAttribute> codedItemClass = CodedAttribute.class;
		for (Field field : codedItemClass.getFields()) {			
			if (result.containsColumn(family_name_column, field.getName().getBytes())) {
				byte[] val = result.getValue(family_name_column, field.getName().getBytes());				
				if (field.getType().equals(Integer.TYPE)) {
					field.set(codedAttribute, Bytes.toInt(val));
				}
				
				if (field.getType().equals(String.class)) {
					field.set(codedAttribute, Bytes.toString(val));
				}				
			}
		}
	}

	public List<CodedAttributeGroup> getCodedAttributeGroup(String id) {
		List<CodedAttributeGroup> groups = new ArrayList<CodedAttributeGroup>();		
		try {
			HTableInterface table = pool.getTable(elementGroupTable);
			Get get  = new Get(Bytes.toBytes(id));
			Result result = table.get(get);		
			for(byte[] currentFamily: result.getMap().keySet()) {				
				for(byte[] mapId : result.getMap().get(currentFamily).keySet()) {
					String value = Bytes.toString(mapId);
					if (value != null && (!value.equals("element_group_description") && !value.equals("element_group_id"))) {
						CodedAttributeGroup attribute = new CodedAttributeGroup();					
						attribute.element_group_id = id;
						attribute.element_group = value;
						groups.add(attribute);
					}
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		} finally {

		}				
		return groups;		
	}
	
	
	public List<CodingGroup> getCodedGroups() {
		List<CodingGroup> codedGroups = new ArrayList<CodingGroup>();		
		ResultScanner scanner  = null;
		try {
			HTableInterface table = pool.getTable(elementGroupTable);
			Scan scan = new Scan();			
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {			
				CodingGroup codingGroup = new CodingGroup();
				codingGroup.setId(Bytes.toString(result.getRow()));
				List<CodedAttributeGroup> groups = new ArrayList<CodedAttributeGroup>();
				for(byte[] currentFamily: result.getMap().keySet()) {				
					for(byte[] mapId : result.getMap().get(currentFamily).keySet()) {
						String value = Bytes.toString(mapId);
						if (value != null && (!value.equals("element_group_description") && !value.equals("element_group_id"))) {
							CodedAttributeGroup attribute = new CodedAttributeGroup();					
							attribute.element_group_id = Bytes.toString(result.getRow());
							attribute.element_group = Bytes.toString(mapId);
							groups.add(attribute);
						}
					}
				}
				codingGroup.setAttributes(groups);
				codedGroups.add(codingGroup);
			}
		} catch(Exception e) {
			
		} finally {}				
		return codedGroups;		
	}
	
	
	public void logCodingEvent(CodingEvent event) {
		
		HTableInterface counter = pool.getTable(countersTable);
		Put put = null;
		try {
			long nextCounter = counter.incrementColumnValue(Bytes.toBytes("9"), family_name_column, Bytes.toBytes(accountTable), 1);			
			put = new Put(Bytes.toBytes(String.valueOf(nextCounter)));		
			Class<CodingEvent> codedItemClass = CodingEvent.class;				
			HTableInterface table = pool.getTable(explicitEventTable);			
			for (Field field : codedItemClass.getFields()) {
				if (field.getType().equals(Integer.TYPE)) {					
					put.add(family_name_column, Bytes.toBytes(field.getName()), Bytes.toBytes(field.getInt(event)));
				}
				if (field.getType().equals(String.class)) {					
					String value = (String)field.get(event);					
					if (value != null) {
						put.add(family_name_column, Bytes.toBytes(field.getName()), Bytes.toBytes(value));
					}
				}
			}
			table.put(put);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			
		}	
	}
	
	
	public void logTrainingEvent(CodingEvent event) {
		Class<CodingEvent> codedItemClass = CodingEvent.class;			
		try {
			HTableInterface table = pool.getTable(trainingEventTable);
			Put put = new Put(Bytes.toBytes(event.user_id));			
			for (Field field : codedItemClass.getFields()) {
				if (field.getType().equals(Integer.TYPE)) {					
					put.add(family_name_column, Bytes.toBytes(field.getName()), Bytes.toBytes(field.getInt(event)));
				}
				if (field.getType().equals(String.class)) {					
					String value = (String)field.get(event);					
					if (value != null) {
						put.add(family_name_column, Bytes.toBytes(field.getName()), Bytes.toBytes(value));
					}
				}
			}
			table.put(put);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			
		}	
	}
	
	
	public List<Account> getAccounts() {
		List<Account> accounts = new ArrayList<Account>();		
		ResultScanner scanner  = null;		
		try {
			HTableInterface table = pool.getTable(accountTable);
			Scan scan = new Scan();			
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {			
				Account account = new Account();								
				mapAccount(account, result);
				accounts.add(account);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			scanner.close();
		}		
		
		return accounts;
		
	}

	public List<TrainingItem> getTrainingSetsForMainGroup(String id) {
		List<TrainingItem> trainingSet = new ArrayList<TrainingItem>();			
		
		ResultScanner scanner  = null;
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				family_name_column,
				TrainingItem.element_group_id_column,
				CompareOp.EQUAL,
				Bytes.toBytes(id)
		);
		try {
			HTableInterface table = pool.getTable(trainingImageTable);
			Scan scan = new Scan();
			scan.setFilter(filter);
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {
				TrainingItem trainingItem = new TrainingItem();	
				mapTrainingSet(trainingItem, result);
				CodedItem attribute = new CodedItem();
				mapResultToCodedItem(attribute, result);
				trainingItem.setCodedItem(attribute);
				trainingSet.add(trainingItem);			
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}

		Collections.sort(trainingSet, new Comparator<TrainingItem>() {
			public int compare(TrainingItem arg0, TrainingItem arg1) {
				
				return (new Integer(arg0.getTrainingId()).compareTo(new Integer(arg1.getTrainingId())));
			
			}
			
		});
		
		return trainingSet;
	}
	
	public List<TrainingItem> getTrainingSets() {
		List<TrainingItem> trainingSet = new ArrayList<TrainingItem>();			
		ResultScanner scanner  = null;
		try {
			HTableInterface table = pool.getTable(trainingImageTable);
			Scan scan = new Scan();
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {
				TrainingItem trainingItem = new TrainingItem();	
				mapTrainingSet(trainingItem, result);
				CodedItem attribute = new CodedItem();
				mapResultToCodedItem(attribute, result);
				trainingItem.setCodedItem(attribute);
				trainingSet.add(trainingItem);			
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}

		Collections.sort(trainingSet, new Comparator<TrainingItem>() {
			public int compare(TrainingItem arg0, TrainingItem arg1) {				
				return (new Integer(arg0.getTrainingId()).compareTo(new Integer(arg1.getTrainingId())));			
			}
			
		});
		
		return trainingSet;
	}

	
	public TrainingItem getTrainingItem(String trainingId) {
		TrainingItem trainingItem = new TrainingItem();		
		try {
			HTableInterface table = pool.getTable(trainingImageTable);
			Get get = new Get(Bytes.toBytes(trainingId));			
			Result result = table.get(get);
			mapTrainingSet(trainingItem, result);
			
			CodedItem attribute = new CodedItem();
			mapResultToCodedItem(attribute, result);
			trainingItem.setCodedItem(attribute);				
		} catch(Exception e) {
			e.printStackTrace();
		} finally {

		}
		return trainingItem;
	}

	private void mapTrainingSet(TrainingItem trainingItem, Result result) {
		trainingItem.setTrainingId(Bytes.toString(result.getRow()));
		
		if (result.containsColumn(family_name_column, TrainingItem.picture_id_column)) {
			byte[] val = result.getValue(family_name_column, TrainingItem.picture_id_column);
			trainingItem.setPictureId(Bytes.toString(val));
		}
		
		if (result.containsColumn(family_name_column, TrainingItem.bucket_name_column)) {
			byte[] val = result.getValue(family_name_column, TrainingItem.bucket_name_column);
			trainingItem.setBucketName(Bytes.toString(val));
		}
		
		if (result.containsColumn(family_name_column, TrainingItem.folder_name_column)) {
			byte[] val = result.getValue(family_name_column, TrainingItem.folder_name_column);
			trainingItem.setFolderName(Bytes.toString(val));
		}
		
		if (result.containsColumn(family_name_column, TrainingItem.element_folder_name_column)) {
			byte[] val = result.getValue(family_name_column, TrainingItem.element_folder_name_column);
			trainingItem.setElementFolderName(Bytes.toString(val));
		}
		
		if (result.containsColumn(family_name_column, TrainingItem.element_group_id_column)) {
			byte[] val = result.getValue(family_name_column, TrainingItem.element_group_id_column);
			trainingItem.setElementGroupId(Bytes.toString(val));
		}
		
	}
	
	public void saveTrainingItem(TrainingItem item) {		
		Class<CodedItem> codedItemClass = CodedItem.class;			
		try {
			HTableInterface table = pool.getTable(trainingImageTable);
			Put put = new Put(Bytes.toBytes(item.trainingId));		
			for (Field field : codedItemClass.getFields()) {
				if (!field.getName().equals("picture_id")) {
																
					if (field.getType().equals(Integer.TYPE)) {					
						put.add(family_name_column, Bytes.toBytes(field.getName()), Bytes.toBytes(field.getInt(item.getCodedItem())));
					}
					if (field.getType().equals(String.class)) {					
						String value = (String)field.get(item.getCodedItem());					
						if (value != null) {
							put.add(family_name_column, Bytes.toBytes(field.getName()), Bytes.toBytes(value));
						}
					}
				}
			}
			
			for (String key : item.getCodedItem().getHighlightMap().keySet()) {
				Highlight highlight = item.getCodedItem().getHighlightMap().get(key);
				put.add(family_name_column, Bytes.toBytes(key + "_x0"), Bytes.toBytes(highlight.getX0()));
				put.add(family_name_column, Bytes.toBytes(key + "_y0"), Bytes.toBytes(highlight.getY0()));
				put.add(family_name_column, Bytes.toBytes(key + "_x1"), Bytes.toBytes(highlight.getX1()));
				put.add(family_name_column, Bytes.toBytes(key + "_y1"), Bytes.toBytes(highlight.getY1()));
				put.add(family_name_column, Bytes.toBytes(key + "_height"), Bytes.toBytes(highlight.getHeight()));
				put.add(family_name_column, Bytes.toBytes(key + "_width"), Bytes.toBytes(highlight.getWidth()));
			}
						
			table.put(put);			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {}
	}

	public CodingGroup getGroup(String groupId) {
		CodingGroup codingGroup  = null;	
		try {
			HTableInterface table = pool.getTable(elementGroupTable);
			Get get = new Get(Bytes.toBytes(groupId));							
			Result result = table.get(get);		
			codingGroup = new CodingGroup();
			codingGroup.setId(Bytes.toString(result.getRow()));
			List<CodedAttributeGroup> groups = new ArrayList<CodedAttributeGroup>();
			for(byte[] currentFamily: result.getMap().keySet()) {				
				for(byte[] mapId : result.getMap().get(currentFamily).keySet()) {
					String value = Bytes.toString(mapId);
					if (value != null && (!value.equals("element_group_description") && !value.equals("element_group_id"))) {
						CodedAttributeGroup attribute = new CodedAttributeGroup();					
						attribute.element_group_id = Bytes.toString(result.getRow());
						attribute.element_group = Bytes.toString(mapId);
						groups.add(attribute);
					}
				}
			}
			codingGroup.setAttributes(groups);				

		} catch(Exception e) {

		} finally {}				
		return codingGroup;		
	}
	
	
	public void testTime() {
		
		HTableInterface table = pool.getTable("time_test");
		Put put = new Put(Bytes.toBytes(1));
		put.add(family_name_column, Bytes.toBytes("time_column"), Bytes.toBytes(System.currentTimeMillis()));
		put.add(family_name_column, Bytes.toBytes("time_column_formt"), Bytes.toBytes(new DateFormatter().getFormat().format(System.currentTimeMillis())));
		try {
			table.put(put);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	

	public List<MainGroup> getMainGroups() {
		List<MainGroup> groups = new ArrayList<MainGroup>();			
		ResultScanner scanner  = null;
		try {
			HTableInterface table = pool.getTable(elementMainTable);
			Scan scan = new Scan();
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {
				MainGroup main = new MainGroup();
				main.setId(Bytes.toString(result.getRow()));
				
				if (result.containsColumn(family_name_column,MainGroup.element_name_column)) {
					byte[] val = result.getValue(family_name_column, MainGroup.element_name_column);
					main.setName(Bytes.toString(val));
				}
				
				if (result.containsColumn(family_name_column,MainGroup.element_label_column)) {
					byte[] val = result.getValue(family_name_column, MainGroup.element_label_column);
					main.setLabel(Bytes.toString(val));
				}
				
				if (result.containsColumn(family_name_column,MainGroup.element_description_column)) {
					byte[] val = result.getValue(family_name_column, MainGroup.element_description_column);
					main.setDescription(Bytes.toString(val));
				}
				
				groups.add(main);			
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}

		return groups;
	}
	
	
	public List<CodingEvent> getCodingEventsForAccount(String accountId) {
		List<CodingEvent> events = new ArrayList<CodingEvent>();
		
		ResultScanner scanner  = null;
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				family_name_column,
				CodingEvent.user_id_column,
				CompareOp.EQUAL,
				Bytes.toBytes(accountId)
		);
		try {
			HTableInterface table = pool.getTable(explicitEventTable);
			Scan scan = new Scan();
			scan.setFilter(filter);
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {
				
				CodingEvent event = new CodingEvent();
				event.setId(Bytes.toString(result.getRow()));
				event.setTimestamp(result.getColumnLatest(family_name_column, CodingEvent.user_id_column).getTimestamp());
				
				if (result.containsColumn(family_name_column, CodingEvent.user_id_column)) {
					byte[] val = result.getValue(family_name_column, CodingEvent.user_id_column);
					event.setUser_id(Bytes.toString(val));
				}
				
				if (result.containsColumn(family_name_column, CodingEvent.picture_id_column)) {
					byte[] val = result.getValue(family_name_column, CodingEvent.picture_id_column);
					event.setPicture_id(Bytes.toString(val));
				}
				
				if (result.containsColumn(family_name_column, CodingEvent.type_column)) {
					byte[] val = result.getValue(family_name_column, CodingEvent.type_column);
					event.setType(Bytes.toString(val));
				}
				events.add(event);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		
		Collections.sort(events, new Comparator<CodingEvent>() {
			public int compare(CodingEvent arg0, CodingEvent arg1) {				
				return (new Long(arg0.getTimestamp()).compareTo(new Long(arg1.getTimestamp())));			
			}			
		});
		
		return events;		
	}
	
	
	public CodingEventRollup getCodingEventRollup(String accountId) {		
		return new CodingEventRollup(getCodingEventsForAccount(accountId));		
	}
	
	
	public HashMap<String, Factor> getFactors() {
		HashMap<String, Factor> factors = new HashMap<String, Factor>();		
		ResultScanner scanner  = null;		
		try {
			HTableInterface table = pool.getTable(factorTable);
			Scan scan = new Scan();			
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {
				String key = Bytes.toString(result.getValue(family_name_column, Factor.factor_column));
				String value = Bytes.toString(result.getValue(family_name_column, Factor.element_column));
				Factor factor = factors.get(key);
				if (factor == null) {
					factor = new Factor();
					factor.setName(key);
					factors.put(key, factor);
				}
				factor.getElements().add(value);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}		
		return factors;		
	}

	public List<String> getElementsFromPictures(String folder, String...pictureIds ) {		
		List<String> elements = new ArrayList<String>();
		List<CodedItem> items = getFolderCodedItemsForPictures(folder, pictureIds);
		for (CodedItem item : items) {
			elements.addAll(item.getActiveAttributes());
		}
		return elements;
	}
	
	
	public RowMapper getBigFiveRow(RowMapper mapper) {

		ResultScanner scanner  = null;
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				family_name_column,
				RowMapper.model_column,
				CompareOp.EQUAL,
				Bytes.toBytes(mapper.getName())
		);

		try {
			HTableInterface table = pool.getTable(elementBig5Table);
			Scan scan = new Scan();
			scan.setFilter(filter);
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {				
				String factor = Bytes.toString(result.getValue(family_name_column, RowMapper.factor_column));
				if (mapper.getDoubleValues().containsKey(factor)) {
					double value =  Bytes.toDouble(result.getValue(family_name_column, RowMapper.value_column));
					mapper.getDoubleValues().put(factor, value);
				}							
			}
			return mapper;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}		
		return mapper;

	}
	
	
	public void loadBig5Data(ArrayList<Factor> factors) {
		
		for (Factor factor : factors) {		
			try {
				HTableInterface table = pool.getTable(elementBig5Table);
				Put put = new Put(Bytes.toBytes(factor.getId()));				
				put.add(family_name_column, Factor.big_5_column, Bytes.toBytes(factor.getName()));				
				put.add(family_name_column, Factor.factor_column, Bytes.toBytes(factor.getElement()));				
				put.add(family_name_column, Factor.value_column, Bytes.toBytes(factor.getCoefficient()));				
				table.put(put);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}				
	}
	
	
	public Team getTeamFromId(long id) {
		Team team = null;			
		try {
			HTableInterface table = pool.getTable(teamTable);
			Get get = new Get(Bytes.toBytes(id));					
			Result result = table.get(get);							
			team = new Team();				
			mapTeam(team, result);				
			return team;
			
		} catch(Exception e) {
			e.printStackTrace();
		} 		
		return team;
	}
	
	
	public Team createTeam(Team team) {
		
		HTableInterface counter = pool.getTable(countersTable);
		try {
			long nextCounter = counter.incrementColumnValue(Bytes.toBytes("10"), family_name_column, Bytes.toBytes(teamTable), 1);
			team.setId(nextCounter);
			saveTeam(team);
		} catch (IOException e) {			
			e.printStackTrace();
		}
				
		return team;
	}
	
	public void saveTeam(Team team) {
		try {
			HTableInterface table = pool.getTable(teamTable);
			Put put = new Put(Bytes.toBytes(team.getId()));
			
			if (team.getName() != null) {
				put.add(family_name_column, Team.name_column, Bytes.toBytes(team.getName()));
			}
			
			if (team.getOwnerId() != null) {
				put.add(family_name_column, Team.owner_id_column, Bytes.toBytes(team.getOwnerId()));
			}
			
			if (team.getInviteBody() != null) {
				put.add(family_name_column, Team.invite_body_column, Bytes.toBytes(team.getInviteBody()));
			}
			
			if (team.getInviteSubject() != null) {
				put.add(family_name_column, Team.invite_subject_column, Bytes.toBytes(team.getInviteSubject()));
			}
			
			if (team.getInviteReminder() != null) {
				put.add(family_name_column, Team.invite_reminder_column, Bytes.toBytes(team.getInviteReminder()));
			}
			
			put.add(family_name_column, Team.timeline_column, Bytes.toBytes(team.getTimeline()));
			
			table.put(put);
			
			for (TeamAccount teamAccount : team.getTeamMembers()) {
				saveTeamAccount(teamAccount);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			
		}	
	}
	
	public void mapTeam(Team team, Result result) {
		
		team.setId(Bytes.toLong(result.getRow()));
		
		if (result.containsColumn(family_name_column, Team.owner_id_column)) {
			byte[] val = result.getValue(family_name_column, Team.owner_id_column);
			team.setOwnerId(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Team.name_column)) {
			byte[] val = result.getValue(family_name_column, Team.name_column);
			team.setName(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Team.timeline_column)) {
			byte[] val = result.getValue(family_name_column, Team.timeline_column);
			team.setTimeline(Bytes.toLong(val));					
		}
		
		if (result.containsColumn(family_name_column, Team.invite_subject_column)) {
			byte[] val = result.getValue(family_name_column, Team.invite_subject_column);
			team.setInviteSubject(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Team.invite_body_column)) {
			byte[] val = result.getValue(family_name_column, Team.invite_body_column);
			team.setInviteBody(Bytes.toString(val));					
		}
		
		if (result.containsColumn(family_name_column, Team.invite_reminder_column)) {
			byte[] val = result.getValue(family_name_column, Team.invite_reminder_column);
			team.setInviteReminder(Bytes.toString(val));					
		}
		
	}
	
	public List<Team> getTeamsForAccount(Account account) {
		List<Team> teams = new ArrayList<Team>();
		ResultScanner scanner  = null;
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				family_name_column,
				Team.owner_id_column,
				CompareOp.EQUAL,
				Bytes.toBytes(account.getUserId()));

		try {
			HTableInterface table = pool.getTable(teamTable);
			Scan scan = new Scan();
			scan.setFilter(filter);
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {				
				Team team = new Team();
				mapTeam(team, result);
				teams.add(team);
			}			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}		
		return teams;	
	}
	
	public void loadAccountsForTeam(Team team) {
		
		ResultScanner scanner  = null;
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				family_name_column,
				TeamAccount.team_id_column,
				CompareOp.EQUAL,
				Bytes.toBytes(team.getId()));

		try {
			HTableInterface table = pool.getTable(teamAccountMapTable);
			Scan scan = new Scan();
			scan.setFilter(filter);
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {				
				if (result.containsColumn(family_name_column, TeamAccount.account_id_column)) {					
					TeamAccount teamAccount = new TeamAccount();
					byte[] val = result.getValue(family_name_column, TeamAccount.account_id_column);
					Account account = getAccountFromId(Bytes.toString(val));
					teamAccount.setAccount(account);
					teamAccount.setId(Bytes.toLong(result.getRow()));
					if (result.containsColumn(family_name_column, TeamAccount.active_column)) {
						byte[] activityVal = result.getValue(family_name_column, TeamAccount.active_column);
						teamAccount.setActive(Bytes.toBoolean(activityVal));
					}
					team.getTeamMembers().add(teamAccount);
				}
				
			}			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}		
	}
	
	
	public void addAccountToTeam(TeamAccount account, Team team) {		
		HTableInterface counter = pool.getTable(countersTable);
		try {
			long nextCounter = counter.incrementColumnValue(Bytes.toBytes("11"), family_name_column, Bytes.toBytes(teamAccountMapTable), 1);
			HTableInterface table = pool.getTable(teamAccountMapTable);
			Put put = new Put(Bytes.toBytes(nextCounter));					
			put.add(family_name_column, TeamAccount.team_id_column, Bytes.toBytes(team.getId()));
			put.add(family_name_column, TeamAccount.account_id_column, Bytes.toBytes(account.getAccount().getUserId()));
			put.add(family_name_column, TeamAccount.active_column, Bytes.toBytes(false));
			table.put(put);
			account.setId(nextCounter);
		} catch (IOException e) {			
			e.printStackTrace();
		} 
		team.getTeamMembers().add(account);
	}
	
	public void saveTeamAccount(TeamAccount teamAccount) {
		
		try {
			HTableInterface table = pool.getTable(teamAccountMapTable);
			Put put = new Put(Bytes.toBytes(teamAccount.getId()));					
			put.add(family_name_column, TeamAccount.active_column, Bytes.toBytes(teamAccount.isActive()));		
			table.put(put);			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			
		}	
		
	}
	
	
	public Invite lookupInvite(String secret) {
		
			ResultScanner scanner  = null;
			SingleColumnValueFilter filter = new SingleColumnValueFilter(
					family_name_column,
					Invite.secret_column,
					CompareOp.EQUAL,
					Bytes.toBytes(secret));

			try {
				HTableInterface table = pool.getTable(inviteTable);
				Scan scan = new Scan();
				scan.setFilter(filter);
				scanner = table.getScanner(scan);		
				for (Result result : scanner) {				
					Invite invite = new Invite();
					
					if (result.containsColumn(family_name_column, Invite.account_id_column)) {
						byte[] val = result.getValue(family_name_column, Invite.account_id_column);
						invite.setAccountId(Bytes.toString(val));					
					}
					
					if (result.containsColumn(family_name_column, Invite.owner_id_column)) {
						byte[] val = result.getValue(family_name_column, Invite.owner_id_column);
						invite.setOwnerId(Bytes.toString(val));					
					}
					
					if (result.containsColumn(family_name_column, Invite.secret_column)) {
						byte[] val = result.getValue(family_name_column, Invite.secret_column);
						invite.setSecret(Bytes.toString(val));					
					}
					
					return invite;
				}			
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				scanner.close();
			}		
			return null;	
		
	}
	
	public Invite createInvite(String ownerId, String accountId) {
		Invite invite = new Invite();
		HTableInterface counter = pool.getTable(countersTable);
		try {			
			long nextCounter = counter.incrementColumnValue(Bytes.toBytes("11"), family_name_column, Bytes.toBytes(inviteTable), 1);
			invite.setId(nextCounter);			
			HTableInterface table = pool.getTable(inviteTable);
			Put put = new Put(Bytes.toBytes(invite.getId()));					
			put.add(family_name_column, Invite.owner_id_column, Bytes.toBytes(ownerId));
			put.add(family_name_column, Invite.account_id_column, Bytes.toBytes(accountId));
			String encoded = encoder.encodePassword(accountId, ownerId);
			invite.setSecret(encoded);
			put.add(family_name_column, Invite.secret_column, Bytes.toBytes(encoded));
			table.put(put);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			return invite;
		}			
	}
	
	
	
	

}
