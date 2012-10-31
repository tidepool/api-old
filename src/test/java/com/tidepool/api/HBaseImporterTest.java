package com.tidepool.api;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tidepool.api.data.HBaseManager;
import com.tidepool.api.model.Factor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:/Users/joseph/projects/tidepoolAPI/src/test/java/com/tidepool/api/hbase-properties.xml"})

public class HBaseImporterTest {

	@Autowired
	private HBaseManager hBaseManager;
	
	private static final String CSV = "/Users/joseph/Downloads/original_big_5.csv";
	
	@Test
	public void testImport() {
		
		BufferedReader inFile;
		ArrayList<Factor> factors = new ArrayList<Factor>();
		try {
			inFile = new BufferedReader(new InputStreamReader(new FileInputStream(CSV)));
			String pipeName;
			while ((pipeName = inFile.readLine()) != null) {
				String[] values = pipeName.split(",");				
				
				Factor factor = new Factor();
				factor.setId(new Integer(values[0]));
				factor.setName(values[1]);
				factor.setElement(values[2]);
				factor.setCoefficient(new Double(values[3]));
				factors.add(factor);
				
			}
		} catch (FileNotFoundException e2) {			
			e2.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		for(Factor factor : factors) {
			System.out.println("  Id: " + factor.getId());
		}
		
		hBaseManager.loadBig5Data(factors);
		
	}
	
	
}
