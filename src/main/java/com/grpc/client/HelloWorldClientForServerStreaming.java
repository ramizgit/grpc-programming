package com.grpc.client;

import com.grpc.myhelloworld.HelloWorldRequest;
import com.grpc.myhelloworld.HelloWorldResponse;
import com.grpc.myhelloworld.HelloWorldServerStreamingGreeterGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class HelloWorldClientForServerStreaming {

    ManagedChannel channel;
    HelloWorldServerStreamingGreeterGrpc.HelloWorldServerStreamingGreeterBlockingStub blockingStub;

    public HelloWorldClientForServerStreaming(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.blockingStub = HelloWorldServerStreamingGreeterGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args)
    {
        HelloWorldClientForServerStreaming client = new HelloWorldClientForServerStreaming("localhost", 50000);
        client.blockingCall();
    }

    public void blockingCall()
    {
        HelloWorldRequest request = HelloWorldRequest.newBuilder().setName("Ramiz").build();
        Iterator<HelloWorldResponse> iterator = this.blockingStub.sayHello(request);
        while (iterator.hasNext()){
            HelloWorldResponse response = iterator.next();
            System.out.println("Response from the server : "+response.getMessage());
        }
    }
}
