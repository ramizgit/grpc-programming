package com.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class HelloWorldServer {

    private Server server;

    public HelloWorldServer(int port)
    {
        this.server = ServerBuilder.
                forPort(port).
                addService(new HelloWorldService()).
                addService(new HelloWorldServerStreamingService()).
                addService(new HelloWorldClientStreamingService()).
                addService(new HelloWorldFullStreamingService()).
                build();
    }

    public void start() throws IOException, InterruptedException {
        System.out.println("server starting");
        this.server.start();
        System.out.println("server started");
        this.server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        HelloWorldServer server = new HelloWorldServer(50000);
        server.start();
    }
}
