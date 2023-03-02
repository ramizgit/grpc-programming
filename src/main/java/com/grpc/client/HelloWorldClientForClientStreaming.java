package com.grpc.client;

import com.grpc.myhelloworld.HelloWorldClientStreamingGreeterGrpc;
import com.grpc.myhelloworld.HelloWorldRequest;
import com.grpc.myhelloworld.HelloWorldResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class HelloWorldClientForClientStreaming {

    ManagedChannel channel;
    HelloWorldClientStreamingGreeterGrpc.HelloWorldClientStreamingGreeterStub stub;

    public HelloWorldClientForClientStreaming(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.stub = HelloWorldClientStreamingGreeterGrpc.newStub(channel);
    }

    public static void main(String[] args) throws InterruptedException {
        HelloWorldClientForClientStreaming client = new HelloWorldClientForClientStreaming("localhost", 50000);
        client.nonBlockingCall();

        //wait for the client call to complete
        Thread.sleep(5000);
    }

    public void nonBlockingCall() throws InterruptedException {

        StreamObserver<HelloWorldResponse> streamObserver = new StreamObserver<HelloWorldResponse>() {

            public void onNext(HelloWorldResponse helloWorldResponse) {
                System.out.println("Response from server : "+helloWorldResponse.getMessage());

            }

            public void onError(Throwable throwable) {
                System.out.println("Client side error : ");
                throwable.printStackTrace();
            }

            public void onCompleted() {
                System.out.println("Server response complted");
            }
        };

        StreamObserver<HelloWorldRequest> requestObserver = this.stub.sayHello(streamObserver);
        requestObserver.onNext(HelloWorldRequest.newBuilder().setName("Prerna").build());
        requestObserver.onNext(HelloWorldRequest.newBuilder().setName("Nirmit").build());
        requestObserver.onNext(HelloWorldRequest.newBuilder().setName("Sagnik").build());
        requestObserver.onNext(HelloWorldRequest.newBuilder().setName("Ramiz").build());
        requestObserver.onCompleted();
    }
}
