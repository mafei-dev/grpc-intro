package com.mafei.server.streaming.server;

import com.mafei.model.BalanceDeductRequest;
import com.mafei.model.BalanceDeductResponse;
import com.mafei.model.StreamBankServerServiceGrpc;
import com.mafei.server.AccountDB;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

/**
 * @Author mafei
 * @Created 6/26/2021 4:42 PM
 */
public class StreamBankServerService extends StreamBankServerServiceGrpc.StreamBankServerServiceImplBase {

    @Override
    public void deductBalance(BalanceDeductRequest request, StreamObserver<BalanceDeductResponse> responseObserver) {


        //deduct 20 time per one request and send the response 20 times [just for fun dont think about the use case]
        for (int i = 0; i < 20; i++) {

            if (AccountDB.getBalance(request.getAccountNumber()) <= 0) {
                String msg = "the amount is not enough";
                responseObserver.onError(Status.FAILED_PRECONDITION.withDescription(msg).asRuntimeException());
                System.out.println("msg " + msg);
                return;
            }
            BalanceDeductResponse build = BalanceDeductResponse
                    .newBuilder()
                    .setAccountNumber(request.getAccountNumber())
                    .setCurrentAmount(AccountDB.deductAmount(request.getAccountNumber(), request.getDeductAmount())).build();
            responseObserver.onNext(build);
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        responseObserver.onCompleted();
    }
}
