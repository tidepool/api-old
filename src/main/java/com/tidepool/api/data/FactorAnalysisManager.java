package com.tidepool.api.data;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tidepool.api.model.Account;
import com.tidepool.api.model.BigFive;
import com.tidepool.api.model.CodingEventRollup;
import com.tidepool.api.model.Factor;
import com.tidepool.api.model.RowMapper;

public class FactorAnalysisManager {

	private HBaseManager hBaseManager;
	
	@Autowired
	public void setHBaseManager(HBaseManager hBaseManager) {
		this.hBaseManager = hBaseManager;
	}
	
	public BigFive getTheBigFive(String folder, Account account) {
		BigFive bigFive = new BigFive();
		
		//Get the events
		CodingEventRollup rollup =  hBaseManager.getCodingEventRollup(account.getUserId());
		
		//Get the elements		
		List<String> elements = hBaseManager.getElementsFromPictures(folder, rollup.getSelectedPictureIds());
		
		//Get the factors
		HashMap<String, Factor> factors = hBaseManager.getFactors();
		
		//Get the factor scores from the elements
		HashMap<String, Factor> scoredFactors = getScoredFactors(factors, elements);
		
		//Apply factor score to the big 5 equations
		bigFive.setOpenness(calculateOpenness(scoredFactors));
		bigFive.setConscientiousness(calculateConscientiousness(scoredFactors));
		bigFive.setExtraversion(calculateExtraversion(scoredFactors));
		bigFive.setAgreeableness(calculateAgreeableness(scoredFactors));
		bigFive.setNeuroticism(this.calculateNeuroticism(scoredFactors));
		return bigFive;
	}
	
	public int getFactorScore(HashMap<String, Factor> factorMap, String factor) {
		if (factorMap.containsKey(factor)) {
			return factorMap.get(factor).getScore();
		}
		return 0;
	}
	
	public double calculateOpenness(HashMap<String, Factor> factorMap) {
		
		RowMapper mapper = new RowMapper();
		mapper.setName("openness");
		
		mapper.getDoubleValues().put("complex_cognition", 0D);
		mapper.getDoubleValues().put("artistic_composition", 0D);
		mapper.getDoubleValues().put("nature", 0D);
		mapper.getDoubleValues().put("complex_nature", 0D);
		mapper.getDoubleValues().put("literary", 0D);
		mapper.getDoubleValues().put("spiritual_religious", 0D);
		mapper.getDoubleValues().put("wild_side", 0D);
		mapper = hBaseManager.getBigFiveRow(mapper);
		
		return (mapper.getDoubleValues().get("complex_cognition") * getFactorScore(factorMap, "complex_cognition")) +
			   (mapper.getDoubleValues().get("artistic_composition") * getFactorScore(factorMap, "artistic_composition")) +
			   (mapper.getDoubleValues().get("nature") * getFactorScore(factorMap, "nature")) +
			   (mapper.getDoubleValues().get("complex_nature") * getFactorScore(factorMap, "complex_nature")) +
			   (mapper.getDoubleValues().get("literary") * getFactorScore(factorMap, "literary")) +
			   (mapper.getDoubleValues().get("spiritual_religious") * getFactorScore(factorMap, "spiritual_religious")) + 
			   (mapper.getDoubleValues().get("wild_side") * getFactorScore(factorMap, "wild_side"));
	}
	
	
	public double calculateConscientiousness(HashMap<String, Factor> factorMap) {

		RowMapper mapper = new RowMapper();
		mapper.setName("conscientiousness");

		mapper.getDoubleValues().put("complex_cognition", 0D);		
		mapper.getDoubleValues().put("negative_human_emotions", 0D);
		mapper.getDoubleValues().put("complex_nature", 0D);
		mapper.getDoubleValues().put("artistic_composition", 0D);		
		mapper = hBaseManager.getBigFiveRow(mapper);

		return (mapper.getDoubleValues().get("complex_cognition") * getFactorScore(factorMap, "complex_cognition")) +
		(mapper.getDoubleValues().get("negative_human_emotions") * getFactorScore(factorMap, "negative_human_emotions")) +			   
		(mapper.getDoubleValues().get("complex_nature") * getFactorScore(factorMap, "complex_nature")) +
		(mapper.getDoubleValues().get("artistic_composition") * getFactorScore(factorMap, "artistic_composition"));			   
	}

	
	public double calculateExtraversion(HashMap<String, Factor> factorMap) {
		
		RowMapper mapper = new RowMapper();
		mapper.setName("extraversion");
		
		mapper.getDoubleValues().put("humans", 0D);
		mapper.getDoubleValues().put("positive_human_emotions", 0D);
		mapper.getDoubleValues().put("sports", 0D);
		mapper.getDoubleValues().put("sexuality", 0D);		
		mapper = hBaseManager.getBigFiveRow(mapper);
		
		return (mapper.getDoubleValues().get("humans") * getFactorScore(factorMap, "humans")) +
			   (mapper.getDoubleValues().get("positive_human_emotions") * getFactorScore(factorMap, "positive_human_emotions")) +
			   (mapper.getDoubleValues().get("sports") * getFactorScore(factorMap, "sports")) +
			   (mapper.getDoubleValues().get("sexuality") * getFactorScore(factorMap, "sexuality"));
	}
	

	public double calculateAgreeableness(HashMap<String, Factor> factorMap) {
		
		RowMapper mapper = new RowMapper();
		mapper.setName("agreeableness");
		
		mapper.getDoubleValues().put("negative_human_emotions", 0D);
		mapper.getDoubleValues().put("positive_human_emotions", 0D);
		mapper.getDoubleValues().put("nature", 0D);
		mapper.getDoubleValues().put("happiness_factor", 0D);
		mapper.getDoubleValues().put("sexuality", 0D);
		
		mapper = hBaseManager.getBigFiveRow(mapper);
		
		return (mapper.getDoubleValues().get("negative_human_emotions") * getFactorScore(factorMap, "negative_human_emotions")) +
			   (mapper.getDoubleValues().get("positive_human_emotions") * getFactorScore(factorMap, "positive_human_emotions")) +
			   (mapper.getDoubleValues().get("nature") * getFactorScore(factorMap, "nature")) +
			   (mapper.getDoubleValues().get("happiness_factor") * getFactorScore(factorMap, "happiness_factor")) +
			   (mapper.getDoubleValues().get("sexuality") * getFactorScore(factorMap, "sexuality"));
	}	
	
	
	public double calculateNeuroticism(HashMap<String, Factor> factorMap) {
		
		RowMapper mapper = new RowMapper();
		mapper.setName("neuroticism");
		
		mapper.getDoubleValues().put("artistic_composition", 0D);
		mapper.getDoubleValues().put("negative_human_emotions", 0D);
		mapper.getDoubleValues().put("positive_human_emotions", 0D);		
		mapper.getDoubleValues().put("nature", 0D);
		mapper.getDoubleValues().put("animals", 0D);
		
		mapper = hBaseManager.getBigFiveRow(mapper);
		
		return (mapper.getDoubleValues().get("negative_human_emotions") * getFactorScore(factorMap, "negative_human_emotions")) +
			   (mapper.getDoubleValues().get("positive_human_emotions") * getFactorScore(factorMap, "positive_human_emotions")) +
			   (mapper.getDoubleValues().get("artistic_composition") * getFactorScore(factorMap, "artistic_composition")) +
			   (mapper.getDoubleValues().get("nature") * getFactorScore(factorMap, "nature")) +
			   (mapper.getDoubleValues().get("animals") * getFactorScore(factorMap, "animals"));
	}	
	
	
	public HashMap<String, Factor> getScoredFactors (HashMap<String, Factor> factors, List<String> elements) {
		HashMap<String, Factor> map = new HashMap<String, Factor>();
		for (String element : elements) {
			for (String factorKey : factors.keySet()) {
				Factor factor = factors.get(factorKey);
				for (String factorElement : factor.getElements()) {
					if (factorElement.equals(element)) {
						Factor scoredFactor = map.get(factorKey);
						if (scoredFactor == null) {
							scoredFactor = new Factor();
							scoredFactor.setName(factorKey);
							map.put(factorKey, scoredFactor);
						}
						scoredFactor.setScore(scoredFactor.getScore() + 1);
					}
				}
			}
		}
		
		return map;
	}
	
	
	
}
