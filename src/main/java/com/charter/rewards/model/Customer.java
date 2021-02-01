package com.charter.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Document
public class Customer {
    String CustomerId;
    List<Transaction> transactionList;
}
