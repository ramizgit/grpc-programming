package com.grpc.client;

import com.grpc.myhelloworld.HelloWorldFullStreamingGreeterGrpc;
import com.grpc.myhelloworld.HelloWorldRequest;
import com.grpc.myhelloworld.HelloWorldResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class HelloWorldClientForFullStreaming {

    ManagedChannel channel;
    HelloWorldFullStreamingGreeterGrpc.HelloWorldFullStreamingGreeterStub stub;

    public HelloWorldClientForFullStreaming(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.stub = HelloWorldFullStreamingGreeterGrpc.newStub(channel);
    }

    public static void main(String[] args) throws InterruptedException {
        HelloWorldClientForFullStreaming client = new HelloWorldClientForFullStreaming("localhost", 50000);
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
        requestObserver.onNext(HelloWorldRequest.newBuilder().setName("Alice").build());
        Thread.sleep(5000);
        requestObserver.onNext(HelloWorldRequest.newBuilder().setName("Ian").build());
        Thread.sleep(5000);
        requestObserver.onNext(HelloWorldRequest.newBuilder().setName("David").build());
        Thread.sleep(5000);
        requestObserver.onNext(HelloWorldRequest.newBuilder().setName("Jhon").build());
        Thread.sleep(5000);
        requestObserver.onCompleted();
    }
}
