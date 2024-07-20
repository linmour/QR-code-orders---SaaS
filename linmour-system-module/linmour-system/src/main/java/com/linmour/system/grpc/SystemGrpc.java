package com.linmour.system.grpc;

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
public final class SystemGrpc {

  private SystemGrpc() {}

  public static final String SERVICE_NAME = "service.System";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.linmour.system.grpc.LongList,
      com.linmour.system.grpc.Result1> getGetShopByIdsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getShopByIds",
      requestType = com.linmour.system.grpc.LongList.class,
      responseType = com.linmour.system.grpc.Result1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.linmour.system.grpc.LongList,
      com.linmour.system.grpc.Result1> getGetShopByIdsMethod() {
    io.grpc.MethodDescriptor<com.linmour.system.grpc.LongList, com.linmour.system.grpc.Result1> getGetShopByIdsMethod;
    if ((getGetShopByIdsMethod = SystemGrpc.getGetShopByIdsMethod) == null) {
      synchronized (SystemGrpc.class) {
        if ((getGetShopByIdsMethod = SystemGrpc.getGetShopByIdsMethod) == null) {
          SystemGrpc.getGetShopByIdsMethod = getGetShopByIdsMethod =
              io.grpc.MethodDescriptor.<com.linmour.system.grpc.LongList, com.linmour.system.grpc.Result1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getShopByIds"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.linmour.system.grpc.LongList.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.linmour.system.grpc.Result1.getDefaultInstance()))
              .setSchemaDescriptor(new SystemMethodDescriptorSupplier("getShopByIds"))
              .build();
        }
      }
    }
    return getGetShopByIdsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.linmour.system.grpc.LongList,
      com.linmour.system.grpc.Result1> getGetMerchantByIdsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getMerchantByIds",
      requestType = com.linmour.system.grpc.LongList.class,
      responseType = com.linmour.system.grpc.Result1.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.linmour.system.grpc.LongList,
      com.linmour.system.grpc.Result1> getGetMerchantByIdsMethod() {
    io.grpc.MethodDescriptor<com.linmour.system.grpc.LongList, com.linmour.system.grpc.Result1> getGetMerchantByIdsMethod;
    if ((getGetMerchantByIdsMethod = SystemGrpc.getGetMerchantByIdsMethod) == null) {
      synchronized (SystemGrpc.class) {
        if ((getGetMerchantByIdsMethod = SystemGrpc.getGetMerchantByIdsMethod) == null) {
          SystemGrpc.getGetMerchantByIdsMethod = getGetMerchantByIdsMethod =
              io.grpc.MethodDescriptor.<com.linmour.system.grpc.LongList, com.linmour.system.grpc.Result1>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getMerchantByIds"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.linmour.system.grpc.LongList.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.linmour.system.grpc.Result1.getDefaultInstance()))
              .setSchemaDescriptor(new SystemMethodDescriptorSupplier("getMerchantByIds"))
              .build();
        }
      }
    }
    return getGetMerchantByIdsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SystemStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemStub>() {
        @java.lang.Override
        public SystemStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemStub(channel, callOptions);
        }
      };
    return SystemStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SystemBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemBlockingStub>() {
        @java.lang.Override
        public SystemBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemBlockingStub(channel, callOptions);
        }
      };
    return SystemBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SystemFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SystemFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SystemFutureStub>() {
        @java.lang.Override
        public SystemFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SystemFutureStub(channel, callOptions);
        }
      };
    return SystemFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class SystemImplBase implements io.grpc.BindableService {

    /**
     */
    public void getShopByIds(com.linmour.system.grpc.LongList request,
        io.grpc.stub.StreamObserver<com.linmour.system.grpc.Result1> responseObserver) {
      asyncUnimplementedUnaryCall(getGetShopByIdsMethod(), responseObserver);
    }

    /**
     */
    public void getMerchantByIds(com.linmour.system.grpc.LongList request,
        io.grpc.stub.StreamObserver<com.linmour.system.grpc.Result1> responseObserver) {
      asyncUnimplementedUnaryCall(getGetMerchantByIdsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetShopByIdsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.linmour.system.grpc.LongList,
                com.linmour.system.grpc.Result1>(
                  this, METHODID_GET_SHOP_BY_IDS)))
          .addMethod(
            getGetMerchantByIdsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.linmour.system.grpc.LongList,
                com.linmour.system.grpc.Result1>(
                  this, METHODID_GET_MERCHANT_BY_IDS)))
          .build();
    }
  }

  /**
   */
  public static final class SystemStub extends io.grpc.stub.AbstractAsyncStub<SystemStub> {
    private SystemStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemStub(channel, callOptions);
    }

    /**
     */
    public void getShopByIds(com.linmour.system.grpc.LongList request,
        io.grpc.stub.StreamObserver<com.linmour.system.grpc.Result1> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetShopByIdsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getMerchantByIds(com.linmour.system.grpc.LongList request,
        io.grpc.stub.StreamObserver<com.linmour.system.grpc.Result1> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetMerchantByIdsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SystemBlockingStub extends io.grpc.stub.AbstractBlockingStub<SystemBlockingStub> {
    private SystemBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.linmour.system.grpc.Result1 getShopByIds(com.linmour.system.grpc.LongList request) {
      return blockingUnaryCall(
          getChannel(), getGetShopByIdsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.linmour.system.grpc.Result1 getMerchantByIds(com.linmour.system.grpc.LongList request) {
      return blockingUnaryCall(
          getChannel(), getGetMerchantByIdsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SystemFutureStub extends io.grpc.stub.AbstractFutureStub<SystemFutureStub> {
    private SystemFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SystemFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SystemFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.linmour.system.grpc.Result1> getShopByIds(
        com.linmour.system.grpc.LongList request) {
      return futureUnaryCall(
          getChannel().newCall(getGetShopByIdsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.linmour.system.grpc.Result1> getMerchantByIds(
        com.linmour.system.grpc.LongList request) {
      return futureUnaryCall(
          getChannel().newCall(getGetMerchantByIdsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SHOP_BY_IDS = 0;
  private static final int METHODID_GET_MERCHANT_BY_IDS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SystemImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SystemImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_SHOP_BY_IDS:
          serviceImpl.getShopByIds((com.linmour.system.grpc.LongList) request,
              (io.grpc.stub.StreamObserver<com.linmour.system.grpc.Result1>) responseObserver);
          break;
        case METHODID_GET_MERCHANT_BY_IDS:
          serviceImpl.getMerchantByIds((com.linmour.system.grpc.LongList) request,
              (io.grpc.stub.StreamObserver<com.linmour.system.grpc.Result1>) responseObserver);
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

  private static abstract class SystemBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SystemBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.linmour.system.grpc.grpc.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("System");
    }
  }

  private static final class SystemFileDescriptorSupplier
      extends SystemBaseDescriptorSupplier {
    SystemFileDescriptorSupplier() {}
  }

  private static final class SystemMethodDescriptorSupplier
      extends SystemBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SystemMethodDescriptorSupplier(String methodName) {
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
      synchronized (SystemGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SystemFileDescriptorSupplier())
              .addMethod(getGetShopByIdsMethod())
              .addMethod(getGetMerchantByIdsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
