package com.charter.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RewardsResponse {
    String customerId;
    Map<String, Integer> monthlyRewards;
    int totalRewards;
}
