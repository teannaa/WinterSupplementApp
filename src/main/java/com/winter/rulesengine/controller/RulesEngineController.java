package com.winter.rulesengine.controller;

import com.winter.rulesengine.model.WinterSupplementInput;
import com.winter.rulesengine.model.WinterSupplementOutput;
import com.winter.rulesengine.service.RulesEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rules")
public class RulesEngineController {

    @Autowired
    private RulesEngineService rulesEngineService;

    @PostMapping("/process")
    public WinterSupplementOutput processInput(@RequestBody WinterSupplementInput input) {
        return rulesEngineService.processInput(input);
    }
}