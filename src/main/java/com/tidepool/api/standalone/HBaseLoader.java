package com.tidepool.api.standalone;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.tidepool.api.model.Factor;

public class HBaseLoader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CommandLineParser parser = new PosixParser();

		// create the Options
		Options options = new Options();
		options.addOption( "t", "table", true, "the table name" );
		options.addOption( "cf", "column-family", true, "column family name" );
		options.addOption( "c", "columns", true, "column mappings(first column is assumned to be ID): name:<Java>type, ex: name:String,score:Long" );
		options.addOption( "f", "file", true, "csv file" );
		try {
	        // parse the command line arguments
	        CommandLine line = parser.parse( options, args );
	        HelpFormatter formatter = new HelpFormatter();
	        	        
	        if (!line.hasOption("t") || !line.hasOption("c") || !line.hasOption("cf") || !line.hasOption("f")) {
	        	formatter.printHelp( "loader", options );
	        	return;
	        } 
	        
	        loadTable(line.getOptionValue("t"), line.getOptionValue("cf"),line.getOptionValue("c"), line.getOptionValue("f")); 
	        
	    }
	    catch( ParseException exp ) {
	        // oops, something went wrong
	        System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
	    }
		
	}
	
	protected static void loadTable(String tableName, String columnFamily, String columns, String filePath) {

		Configuration conf = HBaseConfiguration.create();
		conf.clear();		
		conf.set("hbase.zookeeper.quorum", "localhost");  
		try {
			
			HTable table = new HTable(conf, tableName);
			String[] columnPairs = columns.split(",");
			HashMap<String, String> columnMap = new HashMap<String, String>();
			for (String columnPair : columnPairs) {				
				String[] keyValues = columnPair.split(":");
				columnMap.put(keyValues[0], keyValues[1]);				
			}
			
			byte[] family_name_column = Bytes.toBytes(columnFamily);
			
			BufferedReader inFile;
			ArrayList<Factor> factors = new ArrayList<Factor>();
			try {
				inFile = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
				String pipeName;
				while ((pipeName = inFile.readLine()) != null) {
					String[] values = pipeName.split(",");				
					Put put = new Put(Bytes.toBytes(values[0]));	
					int i = 1;
					for (String columnMapKey : columnMap.keySet()) {
						
						if ( columnMap.get(columnMapKey).toLowerCase().equals("string")) {
							put.add(family_name_column, Bytes.toBytes(columnMapKey), Bytes.toBytes(values[i++]));	
						}
						
						if ( columnMap.get(columnMapKey).toLowerCase().equals("double")) {
							put.add(family_name_column, Bytes.toBytes(columnMapKey), Bytes.toBytes(new Double(values[i++])));	
						}
						
						if ( columnMap.get(columnMapKey).toLowerCase().equals("long")) {
							put.add(family_name_column, Bytes.toBytes(columnMapKey), Bytes.toBytes(new Long(values[i++])));	
						}
						
						if ( columnMap.get(columnMapKey).toLowerCase().equals("int")) {
							put.add(family_name_column, Bytes.toBytes(columnMapKey), Bytes.toBytes(new Integer(values[i++])));	
						}
						
						if ( columnMap.get(columnMapKey).toLowerCase().equals("boolean")) {
							put.add(family_name_column, Bytes.toBytes(columnMapKey), Bytes.toBytes(new Boolean(values[i++])));	
						}
												
					}
					table.put(put);
				}
			} catch (FileNotFoundException e2) {			
				e2.printStackTrace();
			} catch (IOException e) {			
				e.printStackTrace();
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	
}
