package com.winter.rulesengine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import com.winter.rulesengine.model.WinterSupplementInput;
import com.winter.rulesengine.model.WinterSupplementOutput;
import com.winter.rulesengine.service.RulesEngineService;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class RulesengineApplicationTests {

	@Test
	void contextLoads() {
	}
	
	private RulesEngineService rulesEngineService;

    @BeforeEach
    void setUp() {
        rulesEngineService = new RulesEngineService();
    }

    @Test
    void testProcessInput_NotEligible() {
        WinterSupplementInput input = new WinterSupplementInput();
        input.setId("test1");
        input.setFamilyUnitInPayForDecember(false);
        input.setFamilyComposition("single");
        input.setNumberOfChildren(2);

        WinterSupplementOutput output = rulesEngineService.processInput(input);

        assertFalse(output.getIsEligible());
        assertEquals(0, output.getBaseAmount());
        assertEquals(0, output.getChildrenAmount());
        assertEquals(0, output.getSupplementAmount());
    }

    @ParameterizedTest
    @CsvSource({
        "single, 0, 60, 0, 60",
        "single, 2, 60, 40, 100",
        "couple, 0, 120, 0, 120",
        "couple, 3, 120, 60, 180"
    })
    void testInput_Eligible(String familyComposition, int numberOfChildren, 
                                   float expectedBaseAmount, float expectedChildrenAmount, 
                                   float expectedSupplementAmount) {
        WinterSupplementInput input = new WinterSupplementInput();
        input.setId("mqtt_id1");
        input.setFamilyUnitInPayForDecember(true);
        input.setFamilyComposition(familyComposition);
        input.setNumberOfChildren(numberOfChildren);

        WinterSupplementOutput output = rulesEngineService.processInput(input);

        assertTrue(output.getIsEligible());
        assertEquals(expectedBaseAmount, output.getBaseAmount());
        assertEquals(expectedChildrenAmount, output.getChildrenAmount());
        assertEquals(expectedSupplementAmount, output.getSupplementAmount());
    }

    @Test
    void testInput_InvalidFamilyComposition() {
        WinterSupplementInput input = new WinterSupplementInput();
        input.setId("mqtt_id2");
        input.setFamilyUnitInPayForDecember(true);
        input.setFamilyComposition("invalid");
        input.setNumberOfChildren(1);

        WinterSupplementOutput output = rulesEngineService.processInput(input);

        assertTrue(output.getIsEligible());
        assertEquals(0, output.getBaseAmount());
        assertEquals(20, output.getChildrenAmount());
        assertEquals(20, output.getSupplementAmount());
    }

}
