package com.grpc.server;

import com.grpc.myhelloworld.HelloWorldClientStreamingGreeterGrpc;
import com.grpc.myhelloworld.HelloWorldRequest;
import com.grpc.myhelloworld.HelloWorldResponse;
import io.grpc.stub.StreamObserver;

public class HelloWorldClientStreamingService extends HelloWorldClientStreamingGreeterGrpc.HelloWorldClientStreamingGreeterImplBase {

    @Override
    public StreamObserver<HelloWorldRequest> sayHello(final StreamObserver<HelloWorldResponse> responseObserver) {

        return new StreamObserver<HelloWorldRequest>() {

            StringBuilder sb = new StringBuilder("Welcome all : ");

            public void onNext(HelloWorldRequest helloWorldRequest) {
                System.out.println("Server onNext()");
                sb.append(helloWorldRequest.getName());
                sb.append(",");
            }

            public void onError(Throwable throwable) {
                System.out.println("Server side error : ");
                throwable.printStackTrace();
            }

            public void onCompleted() {
                System.out.println("Server onCompleted()");
                sb.append("!!!");
                HelloWorldResponse response = HelloWorldResponse.newBuilder().setMessage(sb.toString()).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }
}
