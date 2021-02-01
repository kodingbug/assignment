package com.charter.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    int price;
    int rewards;
    LocalDate transactionDate;
}
