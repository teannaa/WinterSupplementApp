package com.winter.rulesengine.service;

import com.winter.rulesengine.model.WinterSupplementInput;
import com.winter.rulesengine.model.WinterSupplementOutput;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class RulesEngineService {
	
	
	/** Eligibility is based on familyUnitInPayForDecember, 
	 * base amount depends on family composition, 
	 * additional amount is for children, and total amount is the sum of both. */


	@Async
	public Future<WinterSupplementOutput> processInputAsync(WinterSupplementInput input) {
	    return new AsyncResult<>(processInput(input));
	}	
	
	public WinterSupplementOutput processInput(WinterSupplementInput input) {
        WinterSupplementOutput output = new WinterSupplementOutput();
        output.setId(input.getId());
        output.setIsEligible(input.isFamilyUnitInPayForDecember());

        if (!input.isFamilyUnitInPayForDecember()) {
            output.setBaseAmount(0);
            output.setChildrenAmount(0);
            output.setSupplementAmount(0);
            return output;
        }

        float baseAmount = 0;
        if (input.getFamilyComposition().equals("single")) {
            baseAmount = 60;
        } else if (input.getFamilyComposition().equals("couple")) {
            baseAmount = 120;
        }

        float childrenAmount = input.getNumberOfChildren() * 20f;
        output.setBaseAmount(baseAmount);
        output.setChildrenAmount(childrenAmount);
        output.setSupplementAmount(baseAmount + childrenAmount);

        return output;
    }
}
