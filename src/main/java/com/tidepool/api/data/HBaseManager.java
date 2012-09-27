package com.tidepool.api.data;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RandomRowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tidepool.api.model.Account;
import com.tidepool.api.model.CodedAttribute;
import com.tidepool.api.model.CodedAttributeGroup;
import com.tidepool.api.model.CodedItem;
import com.tidepool.api.model.CodingEvent;
import com.tidepool.api.model.CodingGroup;
import com.tidepool.api.model.Highlight;
import com.tidepool.api.model.Photo;
import com.tidepool.api.model.TrainingItem;

public class HBaseManager {
	
	private static final int HTABLE_POOL_SIZE = 10;

	@Value("${tidepool.hbase.quorum}") 
	private String hbaseQuorum;
	
	@Value("${tidepool.hbase.clientPort}") 
	private String hbaseClientPort;
	
	@Value("${tidepool.hbase.master}") 
	private String hbaseMaster;
	
	private static HBaseManager instance = new HBaseManager();
	private Configuration conf = null;
	private HTablePool pool = null;
	
	private final String implicitElementTable = "implicit_element";
	private final String accountTable = "user_attributes";
	private final String elementDescriptionTable = "element_description";
	private final String elementGroupTable = "element_group";
	private final String explicitEventTable= "explicit_event";
	private final String explicitImageTable= "explicit_image";
	private final String countersTable= "counters";
		
	private final String trainingImageTable= "explicit_training_image";
	
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

	private Account getAccountFromEmailInternal(String email) {
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
		account.setUserId(Bytes.toLong(result.getRow()));
		
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
						
		if (result.containsColumn(family_name_column, Account.registration_level_column)) {
			byte[] val = result.getValue(family_name_column, Account.registration_level_column);
			account.setRegistrationLevel(Bytes.toInt(val));					
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
			
			put.add(family_name_column, Account.registration_level_column, Bytes.toBytes(account.getRegistrationLevel()));
			
			if (account.getElementGroupId() != null) {
				put.add(family_name_column, Account.element_group_id_column, Bytes.toBytes(account.getElementGroupId()));
			}
			
			table.put(put);			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			
		}	
	}
	
	public Account createAccount(Account account) throws Exception{
		
		if (getAccountFromEmailInternal(account.getEmail()) != null) {
			throw new Exception("Account " + account.getEmail() + " already exists....");
		}
		
		HTableInterface counter = pool.getTable(countersTable);
		try {
			long nextCounter = counter.incrementColumnValue(Bytes.toBytes("6"), family_name_column, Bytes.toBytes(accountTable), 1);
			account.setUserId(nextCounter);
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
		ResultScanner scanner  = null;
		Filter filter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(id));
		try {
			HTableInterface table = pool.getTable(implicitElementTable);
			Scan scan = new Scan();
			scan.setFilter(filter);
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {			
				codedItem = new CodedItem();								
				mapResultToCodedItem(codedItem, result);
				return codedItem;				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			scanner.close();
		}		
		return codedItem;	
	}

	public CodedItem getRandomCodedItem() {		
		CodedItem codedItem = null;
		ResultScanner scanner  = null;
		RandomRowFilter filter = new RandomRowFilter(0.1F);
		try {
			HTableInterface table = pool.getTable(explicitImageTable);
			Scan scan = new Scan();
			scan.setFilter(filter);
			scanner = table.getScanner(scan);		
			for (Result result : scanner) {			
				codedItem = new CodedItem();								
				mapResultToCodedItem(codedItem, result);
				return codedItem;				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {			
			scanner.close();
		}		
		return codedItem;	
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
			HTableInterface table = pool.getTable(elementDescriptionTable);
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
		ResultScanner scanner  = null;
		try {
			HTableInterface table = pool.getTable(elementGroupTable);
			Get get  = new Get(Bytes.toBytes(id));
			Result result = table.get(get);		
			for(byte[] currentFamily: result.getMap().keySet()) {				
				for(byte[] mapId : result.getMap().get(currentFamily).keySet()) {
					CodedAttributeGroup attribute = new CodedAttributeGroup();					
					attribute.element_group_id = id;
					attribute.element_group = Bytes.toString(mapId);
					groups.add(attribute);
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
						CodedAttributeGroup attribute = new CodedAttributeGroup();					
						attribute.element_group_id = Bytes.toString(result.getRow());
						attribute.element_group = Bytes.toString(mapId);
						groups.add(attribute);
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
		Class<CodingEvent> codedItemClass = CodingEvent.class;			
		try {
			HTableInterface table = pool.getTable(explicitEventTable);
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
					CodedAttributeGroup attribute = new CodedAttributeGroup();					
					attribute.element_group_id = Bytes.toString(result.getRow());
					attribute.element_group = Bytes.toString(mapId);
					groups.add(attribute);
				}
			}
			codingGroup.setAttributes(groups);				

		} catch(Exception e) {

		} finally {}				
		return codingGroup;		
	}
	
		
}