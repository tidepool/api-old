package com.tidepool.api.model;

import java.io.Serializable;

import org.apache.hadoop.hbase.util.Bytes;

public class TrainingItem implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final byte[] picture_id_column = Bytes.toBytes("picture_id");
	public static final byte[] bucket_name_column = Bytes.toBytes("bucket_name");
	public static final byte[] folder_name_column = Bytes.toBytes("folder_name");
	
	public CodedItem codedItem;

	public String trainingId;
	public String pictureId;
	public String bucketName;
	public String folderName;
	
	public String name;
	
	public CodedItem getCodedItem() {
		return codedItem;
	}

	public void setCodedItem(CodedItem codedItem) {
		this.codedItem = codedItem;
	}

	public String getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(String trainingId) {
		this.trainingId = trainingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
}
