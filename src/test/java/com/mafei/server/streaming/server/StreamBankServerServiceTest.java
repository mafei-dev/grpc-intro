package com.mafei.server.streaming.server;

import com.mafei.model.BalanceDeductRequest;
import com.mafei.model.BalanceDeductResponse;
import com.mafei.model.BankServiceGrpc;
import com.mafei.model.StreamBankServerServiceGrpc;
import com.mafei.server.GRPCServer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StreamBankServerServiceTest {
    private static final String host = "localhost";
    private StreamBankServerServiceGrpc.StreamBankServerServiceBlockingStub streamBankServerServiceBlockingStub;

    @BeforeAll
    void setUp() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, GRPCServer.port)
                .usePlaintext()
                .build();
        //the non-blocking stub means it is Asynchronous and non-blocking
        this.streamBankServerServiceBlockingStub = StreamBankServerServiceGrpc.newBlockingStub(channel);
    }

    @Test
    void deductBalance() {

        BalanceDeductRequest request = BalanceDeductRequest
                .newBuilder()
                .setAccountNumber(1)
                .setDeductAmount(5)
                .build();


        try {
            streamBankServerServiceBlockingStub.deductBalance(request).forEachRemaining(balanceDeductResponse -> {
                System.out.println("balanceDeductResponse = " + balanceDeductResponse);
            });
        } catch (StatusRuntimeException e) {
            System.err.println("e = " + e.getMessage());
        }
    }
}