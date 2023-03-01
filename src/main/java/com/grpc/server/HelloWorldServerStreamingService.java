package com.grpc.server;

import com.grpc.myhelloworld.HelloWorldRequest;
import com.grpc.myhelloworld.HelloWorldResponse;
import com.grpc.myhelloworld.HelloWorldServerStreamingGreeterGrpc;
import io.grpc.stub.StreamObserver;

public class HelloWorldServerStreamingService extends HelloWorldServerStreamingGreeterGrpc.HelloWorldServerStreamingGreeterImplBase{

    @Override
    public void sayHello(HelloWorldRequest request, StreamObserver<HelloWorldResponse> responseObserver) {
        HelloWorldResponse response = HelloWorldResponse.newBuilder().setMessage("Welcome : "+request.getName()+"!!!").build();

        for(int i=0;i<100;i++){
            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();
    }
}
