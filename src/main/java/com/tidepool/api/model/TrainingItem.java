package com.tidepool.api.model;

import java.io.Serializable;

public class TrainingItem implements Serializable {

	private static final long serialVersionUID = 1L;

	public CodedItem codedItem;

	public String trainingId;
	
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
	
}
