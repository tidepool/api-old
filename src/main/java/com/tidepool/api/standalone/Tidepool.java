package com.tidepool.api.standalone;

import java.math.BigInteger;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class Tidepool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("running.....");
		addSomeMoreRows();
	}

	
	public static void addSomeRows() {
		
		System.out.println("Hbase Demo Application ");

		
		try {
			Configuration conf = HBaseConfiguration.create();

			conf.clear();
			
			conf.set("hbase.zookeeper.quorum", "localhost");  

			HBaseAdmin.checkHBaseAvailable(conf);
			
			HBaseAdmin admin = new HBaseAdmin(conf);    
			String test = "long_test";
			
			HTable table = new HTable(conf, test);
			
			byte[] family_name_column = Bytes.toBytes("cf");
			byte[] user_column = Bytes.toBytes("user_id");
						
			Random random = new Random();
			for(int i = 0; i < 10; i++) {
				
				Long long0 = random.nextLong();
				Long long1 = random.nextLong();
				System.out.println("Putting row: " + long0 + " :  " + long1);
				Put put = new Put(Bytes.toBytes(long0));
				put.add(family_name_column, user_column, Bytes.toBytes(long1));
				table.put(put);
			}
			
		} catch (Exception e) {
			System.out.println("ERROR>>>");
			e.printStackTrace();
			System.exit(1);
		}

	}
	
	
public static void addSomeMoreRows() {
		
		System.out.println("Hbase Demo Application ");

		
		try {
			Configuration conf = HBaseConfiguration.create();

			conf.clear();
			
			conf.set("hbase.zookeeper.quorum", "localhost");  

			HBaseAdmin.checkHBaseAvailable(conf);
			
			HBaseAdmin admin = new HBaseAdmin(conf);    
			String test = "bigint_test";
			
			HTable table = new HTable(conf, test);
			
			byte[] family_name_column = Bytes.toBytes("cf");
			byte[] user_column = Bytes.toBytes("user_id");
						
			Random random = new Random();
			for(int i = 0; i < 10; i++) {
				
				Long long0 = random.nextLong();
				BigInteger big0 = BigInteger.valueOf(long0);
				Long long1 = random.nextLong();
				BigInteger big1 = BigInteger.valueOf(long1);
				
				System.out.println("Putting row: " + big0+ " :  " + big1);
				Put put = new Put(big0.toByteArray());
				put.add(family_name_column, user_column, big1.toByteArray());
				table.put(put);
			}
			
		} catch (Exception e) {
			System.out.println("ERROR>>>");
			e.printStackTrace();
			System.exit(1);
		}

	}
	
	
}
