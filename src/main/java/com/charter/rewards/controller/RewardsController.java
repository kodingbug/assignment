package com.charter.rewards.controller;

import com.charter.rewards.model.Reward;
import com.charter.rewards.model.RewardsResponse;
import com.charter.rewards.services.RewardsCalculatorService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RewardsController {

    final RewardsCalculatorService rewardsCalculatorService;

    public RewardsController(RewardsCalculatorService rewardsCalculatorService) {
        this.rewardsCalculatorService = rewardsCalculatorService;
    }

    @GetMapping(value = "/reward/{transactionAmount}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Reward> greeting(@PathVariable int transactionAmount) {
        int rewards = rewardsCalculatorService.calculateRewards(transactionAmount);
        Reward reward = new Reward("transactionID-123", transactionAmount, rewards) ;
        return ResponseEntity.ok(reward);
    }

    @GetMapping("/getCustomerRewards/{customerId}" )
    public ResponseEntity<RewardsResponse> getUser(@PathVariable String customerId) throws Exception {
        RewardsResponse rewardsResponse = rewardsCalculatorService.rewardsCalculator(customerId);
        return ResponseEntity.ok(rewardsResponse);
    }
}
