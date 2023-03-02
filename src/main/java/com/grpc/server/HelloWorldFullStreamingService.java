package com.grpc.server;

import com.grpc.myhelloworld.HelloWorldFullStreamingGreeterGrpc;
import com.grpc.myhelloworld.HelloWorldRequest;
import com.grpc.myhelloworld.HelloWorldResponse;
import io.grpc.stub.StreamObserver;

public class HelloWorldFullStreamingService extends HelloWorldFullStreamingGreeterGrpc.HelloWorldFullStreamingGreeterImplBase {

    @Override
    public StreamObserver<HelloWorldRequest> sayHello(final StreamObserver<HelloWorldResponse> responseObserver) {

        return new StreamObserver<HelloWorldRequest>() {

            public void onNext(HelloWorldRequest helloWorldRequest) {
                System.out.println("service side onnext");
                HelloWorldResponse response = HelloWorldResponse.newBuilder().setMessage("Welcome "+helloWorldRequest.getName()+" !!!").build();
                responseObserver.onNext(response);
            }

            public void onError(Throwable throwable) {
                System.out.println("Server side error : ");
                throwable.printStackTrace();
            }

            public void onCompleted() {
                System.out.println("Server onCompleted()");
                responseObserver.onCompleted();
            }
        };
    }
}
