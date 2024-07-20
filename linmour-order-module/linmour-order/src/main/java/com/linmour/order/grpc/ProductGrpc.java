package com.linmour.order.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.34.1)",
    comments = "Source: mess.proto")
public final class ProductGrpc {

  private ProductGrpc() {}

  public static final String SERVICE_NAME = "service.Product";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.linmour.order.grpc.LongList,
      com.linmour.order.grpc.Result1> getGetProductDetailsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getProductDetails",
      requestType = com.linmour.order.grpc.LongList.class,
      responseType = com.linmour.order.grpc.Result1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.linmour.order.grpc.LongList,
      com.linmour.order.grpc.Result1> getGetProductDetailsMethod() {
    io.grpc.MethodDescriptor<com.linmour.order.grpc.LongList, com.linmour.order.grpc.Result1> getGetProductDetailsMethod;
    if ((getGetProductDetailsMethod = ProductGrpc.getGetProductDetailsMethod) == null) {
      synchronized (ProductGrpc.class) {
        if ((getGetProductDetailsMethod = ProductGrpc.getGetProductDetailsMethod) == null) {
          ProductGrpc.getGetProductDetailsMethod = getGetProductDetailsMethod =
              io.grpc.MethodDescriptor.<com.linmour.order.grpc.LongList, com.linmour.order.grpc.Result1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getProductDetails"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.linmour.order.grpc.LongList.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.linmour.order.grpc.Result1.getDefaultInstance()))
              .setSchemaDescriptor(new ProductMethodDescriptorSupplier("getProductDetails"))
              .build();
        }
      }
    }
    return getGetProductDetailsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.linmour.order.grpc.LongList,
      com.linmour.order.grpc.Result2> getReduceInventoriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reduceInventories",
      requestType = com.linmour.order.grpc.LongList.class,
      responseType = com.linmour.order.grpc.Result2.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.linmour.order.grpc.LongList,
      com.linmour.order.grpc.Result2> getReduceInventoriesMethod() {
    io.grpc.MethodDescriptor<com.linmour.order.grpc.LongList, com.linmour.order.grpc.Result2> getReduceInventoriesMethod;
    if ((getReduceInventoriesMethod = ProductGrpc.getReduceInventoriesMethod) == null) {
      synchronized (ProductGrpc.class) {
        if ((getReduceInventoriesMethod = ProductGrpc.getReduceInventoriesMethod) == null) {
          ProductGrpc.getReduceInventoriesMethod = getReduceInventoriesMethod =
              io.grpc.MethodDescriptor.<com.linmour.order.grpc.LongList, com.linmour.order.grpc.Result2>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reduceInventories"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.linmour.order.grpc.LongList.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.linmour.order.grpc.Result2.getDefaultInstance()))
              .setSchemaDescriptor(new ProductMethodDescriptorSupplier("reduceInventories"))
              .build();
        }
      }
    }
    return getReduceInventoriesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductStub>() {
        @java.lang.Override
        public ProductStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductStub(channel, callOptions);
        }
      };
    return ProductStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductBlockingStub>() {
        @java.lang.Override
        public ProductBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductBlockingStub(channel, callOptions);
        }
      };
    return ProductBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProductFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductFutureStub>() {
        @java.lang.Override
        public ProductFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductFutureStub(channel, callOptions);
        }
      };
    return ProductFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ProductImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 简单gRPC
     * </pre>
     */
    public void getProductDetails(com.linmour.order.grpc.LongList request,
        io.grpc.stub.StreamObserver<com.linmour.order.grpc.Result1> responseObserver) {
      asyncUnimplementedUnaryCall(getGetProductDetailsMethod(), responseObserver);
    }

    /**
     */
    public void reduceInventories(com.linmour.order.grpc.LongList request,
        io.grpc.stub.StreamObserver<com.linmour.order.grpc.Result2> responseObserver) {
      asyncUnimplementedUnaryCall(getReduceInventoriesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetProductDetailsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.linmour.order.grpc.LongList,
                com.linmour.order.grpc.Result1>(
                  this, METHODID_GET_PRODUCT_DETAILS)))
          .addMethod(
            getReduceInventoriesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.linmour.order.grpc.LongList,
                com.linmour.order.grpc.Result2>(
                  this, METHODID_REDUCE_INVENTORIES)))
          .build();
    }
  }

  /**
   */
  public static final class ProductStub extends io.grpc.stub.AbstractAsyncStub<ProductStub> {
    private ProductStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductStub(channel, callOptions);
    }

    /**
     * <pre>
     * 简单gRPC
     * </pre>
     */
    public void getProductDetails(com.linmour.order.grpc.LongList request,
        io.grpc.stub.StreamObserver<com.linmour.order.grpc.Result1> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetProductDetailsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void reduceInventories(com.linmour.order.grpc.LongList request,
        io.grpc.stub.StreamObserver<com.linmour.order.grpc.Result2> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReduceInventoriesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ProductBlockingStub extends io.grpc.stub.AbstractBlockingStub<ProductBlockingStub> {
    private ProductBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 简单gRPC
     * </pre>
     */
    public com.linmour.order.grpc.Result1 getProductDetails(com.linmour.order.grpc.LongList request) {
      return blockingUnaryCall(
          getChannel(), getGetProductDetailsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.linmour.order.grpc.Result2 reduceInventories(com.linmour.order.grpc.LongList request) {
      return blockingUnaryCall(
          getChannel(), getReduceInventoriesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ProductFutureStub extends io.grpc.stub.AbstractFutureStub<ProductFutureStub> {
    private ProductFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 简单gRPC
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.linmour.order.grpc.Result1> getProductDetails(
        com.linmour.order.grpc.LongList request) {
      return futureUnaryCall(
          getChannel().newCall(getGetProductDetailsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.linmour.order.grpc.Result2> reduceInventories(
        com.linmour.order.grpc.LongList request) {
      return futureUnaryCall(
          getChannel().newCall(getReduceInventoriesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PRODUCT_DETAILS = 0;
  private static final int METHODID_REDUCE_INVENTORIES = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProductImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProductImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PRODUCT_DETAILS:
          serviceImpl.getProductDetails((com.linmour.order.grpc.LongList) request,
              (io.grpc.stub.StreamObserver<com.linmour.order.grpc.Result1>) responseObserver);
          break;
        case METHODID_REDUCE_INVENTORIES:
          serviceImpl.reduceInventories((com.linmour.order.grpc.LongList) request,
              (io.grpc.stub.StreamObserver<com.linmour.order.grpc.Result2>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ProductBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProductBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.linmour.order.grpc.grpc.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Product");
    }
  }

  private static final class ProductFileDescriptorSupplier
      extends ProductBaseDescriptorSupplier {
    ProductFileDescriptorSupplier() {}
  }

  private static final class ProductMethodDescriptorSupplier
      extends ProductBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ProductMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ProductGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProductFileDescriptorSupplier())
              .addMethod(getGetProductDetailsMethod())
              .addMethod(getReduceInventoriesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
