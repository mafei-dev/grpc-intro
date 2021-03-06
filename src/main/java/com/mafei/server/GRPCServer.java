package com.mafei.server;

import com.mafei.server.streaming.bidirectional.BidirectionalBankService;
import com.mafei.server.streaming.clientside.ClientSideStreamBankService;
import com.mafei.server.streaming.server.StreamBankServerService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @Author mafei
 * @Created 6/25/2021 3:28 AM
 */
public class GRPCServer {
    public static int port = 6565;

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(port).addService(new BidirectionalBankService()).build();
        System.out.println("server starting on port : " + port);
        server.start();
        server.awaitTermination();


        System.out.println("server is waiting..");
    }
}
