package com.charter.rewards.controller;

import com.charter.rewards.model.Reward;
import com.charter.rewards.model.RewardsResponse;
import com.charter.rewards.model.Transaction;
import com.charter.rewards.services.RewardsCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RewardsController {

    final
    RewardsCalculatorService rewardsCalculatorService;

    public RewardsController(RewardsCalculatorService rewardsCalculatorService) {
        this.rewardsCalculatorService = rewardsCalculatorService;
    }

    @GetMapping(value = "/reward", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Reward> greeting(@RequestParam int transactionAmount) {
        int rewards = rewardsCalculatorService.calculateRewards(transactionAmount);
        Reward reward = new Reward("transactionID-123", transactionAmount, rewards) ;
        return ResponseEntity.ok(reward);
    }

    @GetMapping("/getCustomerRewards" )
    public ResponseEntity getUser(@RequestParam("id") String customerId) throws Exception {
        RewardsResponse rewardsResponse = rewardsCalculatorService.rewardsCalculator(customerId);
        return ResponseEntity.ok(rewardsResponse);
    }
}
