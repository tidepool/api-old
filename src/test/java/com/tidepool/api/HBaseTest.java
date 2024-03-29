package com.tidepool.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.model.CodedAttribute;
import com.tidepool.api.model.CodedAttributeGroup;
import com.tidepool.api.model.CodedItem;
import com.tidepool.api.model.CodedItemLog;
import com.tidepool.api.model.CodingEvent;
import com.tidepool.api.model.CodingEventRollup;
import com.tidepool.api.model.CodingGroup;
import com.tidepool.api.model.Factor;
import com.tidepool.api.model.Highlight;
import com.tidepool.api.model.MainGroup;
import com.tidepool.api.model.TrainingItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:/Users/joseph/projects/tidepoolAPI/src/test/java/com/tidepool/api/hbase-properties.xml"})
public class HBaseTest {

	@Value("${tidepool.attribute.page.limit}") 
	public int PAGE_LIMIT = 6;
	
	@Autowired
	private HBaseManager hBaseManager;
	
	//@Test
	public void testHBASEConnection() {
	
		System.out.println("Hbase Demo Application ");

		// CONFIGURATION
		// ENSURE RUNNING
		try {
			Configuration conf = HBaseConfiguration.create();

			conf.clear();
			//conf.set("hbase.zookeeper.quorum", "ec2-23-22-153-180.compute-1.amazonaws.com");
			//conf.set("hbase.zookeeper.property.clientPort","2181");
			//conf.set("hbase.master", "ec2-23-22-153-180.compute-1.amazonaws.com:60000");
			
			//HBaseConfiguration config = HBaseConfiguration.create();
			conf.set("hbase.zookeeper.quorum", "localhost");  // Here we are running zookeeper locally

			HBaseAdmin.checkHBaseAvailable(conf);
			
			HBaseAdmin admin = new HBaseAdmin(conf);    
			String test = "implicit_coding";

			//admin.disableTable(test); 
			
			//admin.enableTable(test);
			
			HTable table = new HTable(conf, test);
			
			//Put put = new Put(Bytes.toBytes("row4"));
			//put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"), Bytes.toBytes("val1"));			
			//table.put(put);
			
			byte[] family_name_column = Bytes.toBytes("cf");
			byte[] pic_name_column = Bytes.toBytes("picture_id");
						
			
			Scan scan = new Scan();
			ResultScanner scanner = table.getScanner(scan);
			for (Result result : scanner) {
				
				System.out.println("Result: " + result.list());
				if (result.containsColumn(family_name_column, pic_name_column)) {
					byte[] val = result.getValue(family_name_column, pic_name_column);
					System.out.println("Value: " + Bytes.toStringBinary(val));
				}
				
				
				//for (KeyValue kv : result.raw()) {
				//	System.out.println("Name: " + kv.getKey() + "Row " + Bytes.toString(kv.getRow()) + " Value: " + Bytes.toString(kv.getValue()));
				//}
				
			}
			
			scanner.close();


			System.out.println("HBase is running!");
			//  createTable(config);    
			//creating a new table
			//            HTable table = new HTable(config, "mytable");
			//            System.out.println("Table mytable obtained ");  
			//            addData(table);
		} catch (Exception e) {
			System.out.println("ERROR>>>");
			e.printStackTrace();
			System.exit(1);
		}

	}
	
	//@Test
	public void testHBASEAccountConnection() {
		
		System.out.println("Hbase Demo Application ");

		// CONFIGURATION
		// ENSURE RUNNING
		try {
			Configuration conf = HBaseConfiguration.create();

			conf.clear();
			//conf.set("hbase.zookeeper.quorum", "ec2-23-22-153-180.compute-1.amazonaws.com");
			//conf.set("hbase.zookeeper.property.clientPort","2181");
			//conf.set("hbase.master", "ec2-23-22-153-180.compute-1.amazonaws.com:60000");
			
			//HBaseConfiguration config = HBaseConfiguration.create();
			conf.set("hbase.zookeeper.quorum", "localhost");  // Here we are running zookeeper locally

			HBaseAdmin.checkHBaseAvailable(conf);
			
			HBaseAdmin admin = new HBaseAdmin(conf);    
			String test = "user_internal";

			//admin.disableTable(test); 
			
			//admin.enableTable(test);
			
			HTable table = new HTable(conf, test);
			
			//Put put = new Put(Bytes.toBytes("row4"));
			//put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"), Bytes.toBytes("val1"));			
			//table.put(put);
			
			byte[] family_name_column = Bytes.toBytes("cf");
			byte[] pic_name_column = Bytes.toBytes("user_name");
						
			
			Scan scan = new Scan();
			ResultScanner scanner = table.getScanner(scan);
			for (Result result : scanner) {
				
				System.out.println("Result: " + result.list());
				if (result.containsColumn(family_name_column, pic_name_column)) {
					byte[] val = result.getValue(family_name_column, pic_name_column);
					System.out.println("Value: " + Bytes.toStringBinary(val));
				}
				
				
				//for (KeyValue kv : result.raw()) {
				//	System.out.println("Name: " + kv.getKey() + "Row " + Bytes.toString(kv.getRow()) + " Value: " + Bytes.toString(kv.getValue()));
				//}
				
			}
			
			scanner.close();


			System.out.println("HBase is running!");
			//  createTable(config);    
			//creating a new table
			//            HTable table = new HTable(config, "mytable");
			//            System.out.println("Table mytable obtained ");  
			//            addData(table);
		} catch (Exception e) {
			System.out.println("ERROR>>>");
			e.printStackTrace();
			System.exit(1);
		}

	}
	
	
	@Test
	public void testMapCodedItem() {
		
		CodedItem item = hBaseManager.getCodedItemFromId("1"); 
		assertNotNull(item);
	}
	
	//@Test
	public void testPutCodedItem() {
		
		CodedItem item = hBaseManager.getCodedItemFromId("1"); 
		assertNotNull(item);		
		hBaseManager.setCodedItem(item);
		
	}
	
	//@Test
	public void testAttributes() {
		List<CodedAttribute> attributes = hBaseManager.getCodedElements();
		assertTrue(attributes.size() > 0);
		for (CodedAttribute attribute : attributes) {
			System.out.println(attribute.element + " : " + attribute.element_name);
			assertNotNull(attribute.element);
			assertNotNull(attribute.element_name);
			//assertNotNull(attribute.element_description);
		}
	}
	
	
	//@Test
	public void testAttributeGroups() {
		List<CodedAttributeGroup> groups = hBaseManager.getCodedAttributeGroup("1");
		assertTrue(groups.size() > 0);
		for (CodedAttributeGroup group : groups) {
			assertNotNull(group.element_group);
			
		}
	}
	
	//@Test
	public void testGetPageAttributes() {
		
		List<CodedAttribute> allCodedAttributes = new ArrayList<CodedAttribute>();
		List<CodedAttributeGroup> groups = hBaseManager.getCodedAttributeGroup("1");
		HashMap<String, CodedAttribute> attributeMap = hBaseManager.getCodedElementsMap();
		for (CodedAttributeGroup group : groups) {
			
			if ( attributeMap.containsKey(group.element_group)) {
				System.out.println("Group Name: " + group.element_group + " element: " + attributeMap.get(group.element_group).element);
				allCodedAttributes.add(attributeMap.get(group.element_group));
			}
		}
		
		for(CodedAttribute attribute : allCodedAttributes) {
			assertNotNull(attribute.element);
			assertNotNull(attribute.element_name);
			//assertNotNull(attribute.element_description);
		}
		
	}
	
	//@Test
	public void testAttributePagingRollup() {
		
		List<CodedAttribute> allCodedAttributes = new ArrayList<CodedAttribute>();
		List<CodedAttributeGroup> groups = hBaseManager.getCodedAttributeGroup("1");
		HashMap<String, CodedAttribute> attributeMap = hBaseManager.getCodedElementsMap();
		for ( CodedAttribute attribute : attributeMap.values()) {
			allCodedAttributes.add(attribute);
		}
		
		for(CodedAttribute attribute : allCodedAttributes) {
			assertNotNull(attribute.element);
			assertNotNull(attribute.element_name);			
		}
		
		List<List<CodedAttribute>> rollupList = new ArrayList<List<CodedAttribute>>();
		int subListSize = 0;
		int listSize = 0;
		List<CodedAttribute> subList = new ArrayList<CodedAttribute>();
		for (CodedAttribute attribute : allCodedAttributes) {
			if ((subList.size() != 0) && (subList.size() % PAGE_LIMIT == 0) || (listSize == allCodedAttributes.size() - 1)) {
				subListSize = 0;
				rollupList.add(subList);
				subList = new ArrayList<CodedAttribute>();
			}
			subList.add(attribute);
			subListSize++;
			listSize++;
		}
		
		for (List<CodedAttribute> rollup : rollupList) {
			
			for (CodedAttribute attribute : rollup) {
				assertNotNull(attribute.element);
			}
		}
		
	}
	
	
	@Test
	public void testPutCodingEvent() {
		
		for (int i = 0; i < 100; i ++) {
			CodingEvent event = new CodingEvent();
			event.user_id = "0";
			event.picture_id = "test-picture";
			event.x0 = "23";
			event.x1 = "21";
			hBaseManager.logCodingEvent(event);
		}
		
		
		
	}
	
	
	//@Test
	public void testGetRandomExplicitCoding() {
		
		CodedItem item0 = hBaseManager.getRandomCodedItem("0", null);		
		assertNotNull(item0);
		
		CodedItem item1 = hBaseManager.getRandomCodedItem("0", null);		
		assertNotNull(item1);
		
		System.out.println(" Item 0 " + item0.picture_id + " "  + item0.folder_name + " Item 1 " + item1.picture_id);
		
		assertFalse(item1.id.equals(item0.id));
		
	}
	
	
	@Test
	public void testCodedItemLog() {
		
		//assertTrue(hBaseManager.getCodedItemsForUserAndFolder(0L, "test").size() == 0);
		CodedItemLog log = new CodedItemLog();		
		log.setExplicitImageId("test");
		log.setFolderType("work_type");
		log.setUserId("0");
		hBaseManager.saveCodedItemLog(log);
		assertTrue(hBaseManager.getCodedItemsForUserAndFolder("0", "work_type").size() > 0);
		
	}
	
	@Test
	public void testCodedItemLog1() {
		
		assertTrue(hBaseManager.getCodedItemsForUserAndFolder("0", "work_type").size() > 0);
		
		
	}
	
	
	//@Test
	public void testTrainingItem() {
		
		String trainingId = "1-test";
		
		TrainingItem item = new TrainingItem();
		item.setTrainingId(trainingId);
		CodedItem codedItem = new CodedItem();
		Highlight highlight = new Highlight();
		highlight.setX0(10.0);
		highlight.setY0(10.0);
		highlight.setX1(10.0);
		highlight.setX1(10.0);
		highlight.setHeight(10.0);
		highlight.setWidth(10.0);
		codedItem.getHighlightMap().put("night_life", highlight);
		
		item.setCodedItem(codedItem);
		hBaseManager.saveTrainingItem(item);
		
		assertNotNull(hBaseManager.getTrainingItem(trainingId));
		assertNotNull(hBaseManager.getTrainingItem(trainingId).getTrainingId());
		assertNotNull(hBaseManager.getTrainingItem(trainingId).getCodedItem());
		assertNotNull(hBaseManager.getTrainingItem(trainingId).getCodedItem().getHighlightMap());
		assertNotNull(hBaseManager.getTrainingItem(trainingId).getCodedItem().getHighlightMap().get("night_life"));
		assertTrue(hBaseManager.getTrainingItem(trainingId).getCodedItem().getHighlightMap().get("night_life").getX0() == 10.0);
	}
	
	
	//@Test
	public void testTrainingItems() {
		List<TrainingItem> items = hBaseManager.getTrainingSets(); 
		assertTrue(items.size() > 0);
		
		for (TrainingItem item : items) {
			System.out.println("Item: " + item.getPictureId());
		}		
	}
	
	//@Test
	public void testTrainingItemsFromMain() {
		List<TrainingItem> items = hBaseManager.getTrainingSetsForMainGroup("1");
		assertTrue(items.size() > 0);
		
		for (TrainingItem item : items) {
			System.out.println("Id  : " + item.getTrainingId() +  " Item: " + item.getPictureId() + "  folder: " + item.getFolderName());
		}
		
		items = hBaseManager.getTrainingSetsForMainGroup("100");
		assertTrue(items.size() == 0);
				
	}
	
	
	@Test
	public void testMainGroups() {
		List<MainGroup> items = hBaseManager.getMainGroups();
		assertTrue(items.size() > 0);
		
		for (MainGroup item : items) {
			System.out.println("Id  : " + item.getId() +  " Item: " + item.getName() );
		}
		
				
	}
	
	
	@Test
	public void testCodedItemActive() {
		
		CodedItem item = new CodedItem();
		item.night_life = "1";
		
		assertTrue(item.isAttributeActive("night_life"));
		assertFalse(item.isAttributeActive("movement"));
		
		
	}
	
	
	@Test
	public void testCodedAttributeMap() {
		
		HashMap<String, CodedAttribute> attributeMap = hBaseManager.getCodedElementsMap();
		assertNotNull(attributeMap);
		
		for(String name : attributeMap.keySet()) {
			System.out.println("public String " + name + ";");
		}
		
		
	}

	
	@Test
	public void testGetCodedGroups() {
		List<CodingGroup> codedGroups = hBaseManager.getCodedGroups();
		
		assertTrue(codedGroups.size() > 0);
		
		
	}
	
		
	@Test
	public void testTime() {
		hBaseManager.testTime();
	}
	
	
	@Test
	public void testGetCodedAttributeForGroup() {
		List<CodedAttribute> attributeList = hBaseManager.getCodedElementsForGroup("2");				
		assertTrue(attributeList.size() > 0);
		
		for (CodedAttribute attribute : attributeList) {
			assertNotNull(attribute);
			assertNotNull(attribute.element_group_id);
			assertNotNull(attribute.element);
			assertNotNull(attribute.element_description);
		}
	
	}
	
	
	@Test
	public void testGetCodedItemsFromFolder() {
		
		List<CodedItem> items = hBaseManager.getFolderCodedItems("work_type");
		assertNotNull(items);
		assertTrue(items.size() > 0);
		
	}
	
	//@Test
	public void testGetCodingEventForUser() {
		List<CodingEvent> events = hBaseManager.getCodingEventsForAccount("191");
		assertNotNull(events);
		assertTrue(events.size() > 0);		
		
		for (CodingEvent event : events) {
			System.out.println("Event: id: " + event.getId() + " user id: " + event.getUser_id() + " type: " + event.getType() + " picture_id: " + event.getPicture_id()  + " timestamp: " + event.getTimestamp());
		}		
	}
	
	//@Test
	public void testCodingEventRollup() {
		CodingEventRollup rollup =  hBaseManager.getCodingEventRollup("193");
		assertNotNull(rollup);
		assertTrue(rollup.getSelectedEvents().size() > 0);
		assertTrue(rollup.getSelectedPictureIds().length > 0);
	}
	
	
	@Test
	public void testGettingCodedItemsByIdAndFolder() {
		List<CodedItem> items = hBaseManager.getFolderCodedItemsForPictures("match_type", "1", "10");
		assertNotNull(items);
		assertTrue(items.size() == 2);
	}
	
	@Test
	public void testGetElementsFromPicture() {
		List<CodedItem> items = hBaseManager.getFolderCodedItemsForPictures("match_type", "1");
		assertNotNull(items);
		assertTrue(items.size() == 1);		
		assertTrue(items.get(0).getActiveAttributes().size() > 0);
	}
	
	@Test
	public void getElementsFromPictures() {
		List<String> items = hBaseManager.getElementsFromPictures("match_type", "1", "10");
		assertTrue(items.size() > 0);
		for (String item : items) {
			System.out.println("Element: " + item);
		}
	}
	
	
	@Test
	public void testGetFactorMap() {		
		HashMap<String, Factor> factors = hBaseManager.getFactors();
		assertNotNull(factors);
		assertTrue(factors.size() > 0);
	}
	
	
	
}
