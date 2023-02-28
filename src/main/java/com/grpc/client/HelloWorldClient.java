package com.grpc.client;

import com.grpc.myhelloworld.HelloWorldGreeterGrpc;
import com.grpc.myhelloworld.HelloWorldRequest;
import com.grpc.myhelloworld.HelloWorldResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class HelloWorldClient {

    ManagedChannel channel;
    HelloWorldGreeterGrpc.HelloWorldGreeterBlockingStub blockingStub;
    HelloWorldGreeterGrpc.HelloWorldGreeterStub nonBlockingStub;

    public HelloWorldClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.blockingStub = HelloWorldGreeterGrpc.newBlockingStub(channel);
        this.nonBlockingStub = HelloWorldGreeterGrpc.newStub(channel);
    }

    public static void main(String[] args)
    {
        HelloWorldClient client = new HelloWorldClient("localhost", 50000);
        client.nonBlockingCall();
        client.blockingCall();
    }

    public void blockingCall()
    {
        HelloWorldRequest request = HelloWorldRequest.newBuilder().setName("Ramiz").build();
        HelloWorldResponse response = this.blockingStub.sayHello(request);
        System.out.println("Response from the server : "+response.getMessage());
    }

    public void nonBlockingCall()
    {
        HelloWorldRequest request = HelloWorldRequest.newBuilder().setName("Ramiz").build();

        StreamObserver<HelloWorldResponse> streamObserver = new StreamObserver<HelloWorldResponse>() {
            public void onNext(HelloWorldResponse helloWorldResponse) {
                System.out.println("Async onNext");
                System.out.println("Response from the server : "+helloWorldResponse.getMessage());
            }

            public void onError(Throwable throwable) {
                System.out.println("Async onError");
            }

            public void onCompleted() {
                System.out.println("Async onCompleted");
            }
        };

        this.nonBlockingStub.sayHello(request, streamObserver);
    }
}
