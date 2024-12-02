package com.winter.rulesengine.model;

import lombok.Data;

@Data
public class WinterSupplementOutput {
    private String id;
    private Boolean isEligible;
    private float baseAmount;
    private float childrenAmount;
    private float supplementAmount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getIsEligible() {
		return isEligible;
	}
	public void setIsEligible(Boolean isEligible) {
		this.isEligible = isEligible;
	}
	public float getBaseAmount() {
		return baseAmount;
	}
	public void setBaseAmount(float baseAmount) {
		this.baseAmount = baseAmount;
	}
	public float getChildrenAmount() {
		return childrenAmount;
	}
	public void setChildrenAmount(float childrenAmount) {
		this.childrenAmount = childrenAmount;
	}
	public float getSupplementAmount() {
		return supplementAmount;
	}
	public void setSupplementAmount(float supplementAmount) {
		this.supplementAmount = supplementAmount;
	}
    
    
}
