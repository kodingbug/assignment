package com.charter.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Reward {

    String transactionId;
    int transactionAmount;
    int rewardsReceived;

}
