package com.tidepool.api.controller;

import java.util.HashMap;

public class VariantManager {

	private String[] variant0 = {
		"timing",
		"image0",
		"drag0",
		"image1",
		"drag1",
		"image2",
		"drag2",
		"image3",
		"drag3",
		"image4",
		"drag4",
		"image5",
		"image6",
		"assessmentFeedback"
	};
	
	private String[] variant1 = {
			 "timing",
			 "drag0",
			 "image1",
			 "drag1",
			 "image3",
			 "drag3",
			 "image4",
			 "drag4",
			 "image5",
			 "image6",
			 "assessmentFeedback"
		};
	
	private String[] variant10 = {			 
			 "image1",
			 "endUserRegister"
		};
	
	
	private HashMap<String, String[]> map = new HashMap<String, String[]>();
	private String testNumber = "0";
	private int currentIndex = 0;
	
	public VariantManager(String testNumber) {
		this.testNumber = testNumber;
		
		map.put("0", variant0);
		map.put("1", variant1);
		map.put("10", variant10);
	}
	
	public String getNext() {
		return map.get(testNumber)[currentIndex++];
	}
	
}
