package com.charter.rewards.services;

import com.charter.rewards.model.Customer;
import com.charter.rewards.model.RewardsResponse;
import com.charter.rewards.model.Transaction;
import com.charter.rewards.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardsCalculatorService {

    private final CustomerRepository customerRepository;

    public RewardsCalculatorService(CustomerRepository userRepository) {
        this.customerRepository = userRepository;
    }

    public int calculateRewards(int transactionAmount) {
        int rewards = 0;
        //Check If number greater than 50
        if (transactionAmount > 50) {
            rewards = ((transactionAmount/50) - 1) * 50;
        }
        //Check If number greater than 100 add additional Rewards for every $$ amount spent
        if (transactionAmount > 100) {
            int divValue = transactionAmount/100;
            rewards += (transactionAmount%100) * 2;
            rewards += divValue > 1 ? (divValue - 1 ) * 100 * 2 : 0;
        }
        return rewards;
    }

    public RewardsResponse rewardsCalculator(String customerId){
        Customer customer =  customerRepository.findByCustomerId(customerId);
        List<Transaction> transactions = customer.getTransactionList();
        int total = transactions.stream().mapToInt(Transaction::getRewards).sum();
        return new RewardsResponse(customerId, calculateMonthlyRewards(transactions), total);
    }

    private Map<String, Integer> calculateMonthlyRewards(List<Transaction> transactionList) {

        int firstMonth = transactionList.get(0).getTransactionDate().getMonthValue();
        int firstMonthRewards = 0,secondMonthRewards = 0, thirdMonthRewards = 0;
        for (Transaction transaction: transactionList) {
            if (firstMonth == transaction.getTransactionDate().getMonthValue()) {
                firstMonthRewards += transaction.getRewards();
            } else if (firstMonth + 1 == transaction.getTransactionDate().getMonthValue()) {
                secondMonthRewards += transaction.getRewards();
            } else if ( firstMonth + 2 == transaction.getTransactionDate().getMonthValue()) {
                thirdMonthRewards += transaction.getRewards();
            }
        }
        Map<String, Integer> monthlyRewards = new HashMap<>();
        monthlyRewards.put(Month.of(firstMonth).toString(), firstMonthRewards);
        monthlyRewards.put(Month.of(firstMonth+1).toString(), secondMonthRewards);
        monthlyRewards.put(Month.of(firstMonth+2).toString(), thirdMonthRewards);
        return monthlyRewards;
    }
}
