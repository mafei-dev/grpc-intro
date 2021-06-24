package com.mafei.server;

import com.mafei.model.BalanceCheckRequest;
import com.mafei.model.BalanceResponse;
import com.mafei.model.BankServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @Author mafei
 * @Created 6/25/2021 3:23 AM
 */
public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<BalanceResponse> responseObserver) {
        int amount = request.getAccountNumber() * 2;
        BalanceResponse response = BalanceResponse.newBuilder()
                .setAmount(amount)
                .setCurrency("LKR")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
