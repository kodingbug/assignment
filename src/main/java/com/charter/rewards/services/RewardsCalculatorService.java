package com.charter.rewards.services;

import com.charter.rewards.model.Customer;
import com.charter.rewards.model.RewardsResponse;
import com.charter.rewards.model.Transaction;
import com.charter.rewards.repository.CustomerRepository;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardsCalculatorService {

    private final CustomerRepository customerRepository;

    private static Logger log = LoggerFactory.getLogger(RewardsCalculatorService.class);

    public RewardsCalculatorService(CustomerRepository userRepository) {
        this.customerRepository = userRepository;
    }

    public RewardsResponse rewardsCalculator(String customerId){
        RewardsResponse rewardsResponse = new RewardsResponse();
        rewardsResponse.setCustomerId(customerId);

        try {
            Customer customer =  customerRepository.findByCustomerId(customerId);
            if (customer != null && !customer.getTransactionList().isEmpty()) {
                List<Transaction> transactions = customer.getTransactionList();
                Map<String, Integer> monthlyRewards = calculateMonthlyRewards(transactions);
                rewardsResponse.setMonthlyRewards(monthlyRewards);
                rewardsResponse.setTotalRewards(monthlyRewards.values().stream()
                        .reduce(0, Integer::sum));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return rewardsResponse;
    }

    private Map<String, Integer> calculateMonthlyRewards(List<Transaction> transactionList) {

        LocalDate initialDate = transactionList.get(0).getTransactionDate();

        int firstMonthRewards = 0,secondMonthRewards = 0, thirdMonthRewards = 0;
        for (Transaction transaction: transactionList) {

            if (transaction.getTransactionDate().isAfter(initialDate)  &&
                    transaction.getTransactionDate().isAfter(initialDate.plusDays(30))) {
                firstMonthRewards += transaction.getRewards() > 0 ? transaction.getRewards() : calculateRewards(transaction.getPrice());
            } else if (transaction.getTransactionDate().isAfter(initialDate.plusDays(30))  &&
                    transaction.getTransactionDate().isAfter(initialDate.plusDays(60))) {
                secondMonthRewards += transaction.getRewards() > 0 ? transaction.getRewards() : calculateRewards(transaction.getPrice());
            } else if (transaction.getTransactionDate().isAfter(initialDate.plusDays(60))  &&
                    transaction.getTransactionDate().isAfter(initialDate.plusDays(90))) {
                thirdMonthRewards += transaction.getRewards() > 0 ? transaction.getRewards() : calculateRewards(transaction.getPrice());
            }
        }
        Map<String, Integer> monthlyRewards = new HashMap<>();
        monthlyRewards.put("First Month", firstMonthRewards);
        monthlyRewards.put("Second Month", secondMonthRewards);
        monthlyRewards.put("Third Month", thirdMonthRewards);
        return monthlyRewards;
    }

    public int calculateRewards(int transactionAmount) {
        int rewards = 0;
        //Check If number greater than 50
        if (transactionAmount > 50 && transactionAmount < 100) {
            rewards = transactionAmount - 50;
        } else if (transactionAmount > 100) {//Check If number greater than 100 add additional Rewards for every $$ amount spent
            rewards += (transactionAmount-100) * 2 + 50;
        }
        return rewards;
    }
}
