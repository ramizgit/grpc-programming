package com.grpc.server;

import com.grpc.myhelloworld.HelloWorldGreeterGrpc;
import com.grpc.myhelloworld.HelloWorldRequest;
import com.grpc.myhelloworld.HelloWorldResponse;

public class HelloWorldService extends HelloWorldGreeterGrpc.HelloWorldGreeterImplBase {

    @Override
    public void sayHello(HelloWorldRequest request, io.grpc.stub.StreamObserver<HelloWorldResponse> responseObserver) {
        HelloWorldResponse response = HelloWorldResponse.newBuilder().setMessage("Welcome : "+request.getName()+"!!!").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
