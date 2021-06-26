package com.mafei.server.streaming.bidirectional;

import com.mafei.model.*;
import com.mafei.server.AccountDB;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

/**
 * @Author mafei
 * @Created 6/27/2021 2:38 AM
 */
public class BidirectionalBankService extends BidirectionalBankServiceGrpc.BidirectionalBankServiceImplBase {
    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {

        StreamObserver<TransferRequest> streamObserver = new StreamObserver<>() {

            @Override
            public void onNext(TransferRequest transferRequest) {
                int fromAccount = transferRequest.getFromAccount();
                int toAccount = transferRequest.getToAccount();
                int amount = transferRequest.getAmount();
                int fromAmountBalance = AccountDB.getBalance(fromAccount);

                TransferStatus status;
                if (fromAmountBalance >= amount && fromAccount != toAccount) {
                    AccountDB.deductAmount(fromAccount, amount);
                    AccountDB.addAmount(toAccount, amount);
                    status = TransferStatus.TRANSFER_STATUS_SUCCESS;
                    Account _fromAccount = Account.newBuilder()
                            .setAccountNumber(fromAccount)
                            .setAmount(AccountDB.getBalance(fromAccount))
                            .build();

                    Account _toAccount = Account.newBuilder()
                            .setAccountNumber(toAccount)
                            .setAmount(AccountDB.getBalance(toAccount))
                            .build();


                    TransferResponse response = TransferResponse.newBuilder()
                            .setTransferStatus(status)
                            .addAccounts(_fromAccount)
                            .addAccounts(_toAccount)
                            .build();

                    responseObserver.onNext(response);
                } else {
                    responseObserver.onError(Status.OK.asException());
                }

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("throwable = " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                AccountDB.printDetails();
                responseObserver.onCompleted();
            }
        };
        return streamObserver;
    }
}
