package com.mafei.server.streaming.clientside;

import com.mafei.model.ClientSideStreamBankServiceGrpc;
import com.mafei.model.DepositRequest;
import com.mafei.model.DepositResponse;
import com.mafei.server.AccountDB;
import io.grpc.stub.StreamObserver;

/**
 * @Author mafei
 * @Created 6/26/2021 7:58 PM
 */
public class ClientSideStreamBankService extends ClientSideStreamBankServiceGrpc.ClientSideStreamBankServiceImplBase {
    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<DepositResponse> responseObserver) {
        return new StreamObserver<DepositRequest>() {
            private StreamObserver<DepositResponse> streamObserver = responseObserver;
            private int accountBalance;

            @Override
            public void onNext(DepositRequest depositRequest) {
                Integer balance = AccountDB.addAmount(depositRequest.getAccountNumber(), depositRequest.getAmount());
                accountBalance = balance;

            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("throwable.getMessage() = " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                DepositResponse response = DepositResponse.newBuilder()
                        .setCurrentAmount(accountBalance)
                        .build();
                streamObserver.onNext(response);
                streamObserver.onCompleted();
            }
        };
    }
}
