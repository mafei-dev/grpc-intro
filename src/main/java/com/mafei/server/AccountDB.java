package com.mafei.server;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author mafei
 * @Created 6/25/2021 3:54 AM
 */
public class AccountDB {


    private static final Map<Integer, Integer> DATABASE =
            IntStream
                    .rangeClosed(1, 10)
                    .boxed()
                    .collect(
                            Collectors.toMap(Function.identity(), integer -> {
                                return integer * 10;
                            })
                    );


    public static Integer addAmount(int accountNumber, int amount) {
        return DATABASE.computeIfPresent(accountNumber, (_accountNum, _amount) -> {
            return _amount + amount;
        });
    }

    public static Integer deductAmount(int accountNumber, int amount) {
        return DATABASE.computeIfPresent(accountNumber, (_accountNum, _amount) -> {
            return _amount - amount;
        });
    }

    public static Integer getBalance(int accountNumber) {
        return DATABASE.get(accountNumber);
    }
}
