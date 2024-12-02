package com.winter.rulesengine.model;

import lombok.Data;

@Data
public class WinterSupplementInput {
    private String id;
    private int numberOfChildren;
    private String familyComposition; // "single" or "couple"
    private boolean familyUnitInPayForDecember;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNumberOfChildren() {
		return numberOfChildren;
	}
	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	public String getFamilyComposition() {
		return familyComposition;
	}
	public void setFamilyComposition(String familyComposition) {
		this.familyComposition = familyComposition;
	}
	public boolean isFamilyUnitInPayForDecember() {
		return familyUnitInPayForDecember;
	}
	public void setFamilyUnitInPayForDecember(boolean familyUnitInPayForDecember) {
		this.familyUnitInPayForDecember = familyUnitInPayForDecember;
	}
    
    
}
