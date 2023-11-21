// Code generated by protoc-gen-go-triple. DO NOT EDIT.
// versions:
// - protoc-gen-go-triple v1.0.8
// - protoc             v4.23.4
// source: user_session_service.proto

package pb

import (
	context "context"
	protocol "dubbo.apache.org/dubbo-go/v3/protocol"
	dubbo3 "dubbo.apache.org/dubbo-go/v3/protocol/dubbo3"
	invocation "dubbo.apache.org/dubbo-go/v3/protocol/invocation"
	grpc_go "github.com/dubbogo/grpc-go"
	codes "github.com/dubbogo/grpc-go/codes"
	metadata "github.com/dubbogo/grpc-go/metadata"
	status "github.com/dubbogo/grpc-go/status"
	common "github.com/dubbogo/triple/pkg/common"
	constant "github.com/dubbogo/triple/pkg/common/constant"
	triple "github.com/dubbogo/triple/pkg/triple"
)

// This is a compile-time assertion to ensure that this generated file
// is compatible with the grpc package it is being compiled against.
const _ = grpc_go.SupportPackageIsVersion7

// UserSessionServiceClient is the client API for UserSessionService service.
//
// For semantics around ctx use and closing/ending streaming RPCs, please refer to https://pkg.go.dev/google.golang.org/grpc/?tab=doc#ClientConn.NewStream.
type UserSessionServiceClient interface {
	Create(ctx context.Context, in *CreateSessionReq, opts ...grpc_go.CallOption) (*CreateSessionResp, common.ErrorWithAttachment)
}

type userSessionServiceClient struct {
	cc *triple.TripleConn
}

type UserSessionServiceClientImpl struct {
	Create func(ctx context.Context, in *CreateSessionReq) (*CreateSessionResp, error)
}

func (c *UserSessionServiceClientImpl) GetDubboStub(cc *triple.TripleConn) UserSessionServiceClient {
	return NewUserSessionServiceClient(cc)
}

func (c *UserSessionServiceClientImpl) XXX_InterfaceName() string {
	return "user.user_session_server.UserSessionService"
}

func NewUserSessionServiceClient(cc *triple.TripleConn) UserSessionServiceClient {
	return &userSessionServiceClient{cc}
}

func (c *userSessionServiceClient) Create(ctx context.Context, in *CreateSessionReq, opts ...grpc_go.CallOption) (*CreateSessionResp, common.ErrorWithAttachment) {
	out := new(CreateSessionResp)
	interfaceKey := ctx.Value(constant.InterfaceKey).(string)
	return out, c.cc.Invoke(ctx, "/"+interfaceKey+"/Create", in, out)
}

// UserSessionServiceServer is the server API for UserSessionService service.
// All implementations must embed UnimplementedUserSessionServiceServer
// for forward compatibility
type UserSessionServiceServer interface {
	Create(context.Context, *CreateSessionReq) (*CreateSessionResp, error)
	mustEmbedUnimplementedUserSessionServiceServer()
}

// UnimplementedUserSessionServiceServer must be embedded to have forward compatible implementations.
type UnimplementedUserSessionServiceServer struct {
	proxyImpl protocol.Invoker
}

func (UnimplementedUserSessionServiceServer) Create(context.Context, *CreateSessionReq) (*CreateSessionResp, error) {
	return nil, status.Errorf(codes.Unimplemented, "method Create not implemented")
}
func (s *UnimplementedUserSessionServiceServer) XXX_SetProxyImpl(impl protocol.Invoker) {
	s.proxyImpl = impl
}

func (s *UnimplementedUserSessionServiceServer) XXX_GetProxyImpl() protocol.Invoker {
	return s.proxyImpl
}

func (s *UnimplementedUserSessionServiceServer) XXX_ServiceDesc() *grpc_go.ServiceDesc {
	return &UserSessionService_ServiceDesc
}
func (s *UnimplementedUserSessionServiceServer) XXX_InterfaceName() string {
	return "user.user_session_server.UserSessionService"
}

func (UnimplementedUserSessionServiceServer) mustEmbedUnimplementedUserSessionServiceServer() {}

// UnsafeUserSessionServiceServer may be embedded to opt out of forward compatibility for this service.
// Use of this interface is not recommended, as added methods to UserSessionServiceServer will
// result in compilation errors.
type UnsafeUserSessionServiceServer interface {
	mustEmbedUnimplementedUserSessionServiceServer()
}

func RegisterUserSessionServiceServer(s grpc_go.ServiceRegistrar, srv UserSessionServiceServer) {
	s.RegisterService(&UserSessionService_ServiceDesc, srv)
}

func _UserSessionService_Create_Handler(srv interface{}, ctx context.Context, dec func(interface{}) error, interceptor grpc_go.UnaryServerInterceptor) (interface{}, error) {
	in := new(CreateSessionReq)
	if err := dec(in); err != nil {
		return nil, err
	}
	base := srv.(dubbo3.Dubbo3GrpcService)
	args := []interface{}{}
	args = append(args, in)
	md, _ := metadata.FromIncomingContext(ctx)
	invAttachment := make(map[string]interface{}, len(md))
	for k, v := range md {
		invAttachment[k] = v
	}
	invo := invocation.NewRPCInvocation("Create", args, invAttachment)
	if interceptor == nil {
		result := base.XXX_GetProxyImpl().Invoke(ctx, invo)
		return result, result.Error()
	}
	info := &grpc_go.UnaryServerInfo{
		Server:     srv,
		FullMethod: ctx.Value("XXX_TRIPLE_GO_INTERFACE_NAME").(string),
	}
	handler := func(ctx context.Context, req interface{}) (interface{}, error) {
		result := base.XXX_GetProxyImpl().Invoke(ctx, invo)
		return result, result.Error()
	}
	return interceptor(ctx, in, info, handler)
}

// UserSessionService_ServiceDesc is the grpc_go.ServiceDesc for UserSessionService service.
// It's only intended for direct use with grpc_go.RegisterService,
// and not to be introspected or modified (even as a copy)
var UserSessionService_ServiceDesc = grpc_go.ServiceDesc{
	ServiceName: "user.user_session_server.UserSessionService",
	HandlerType: (*UserSessionServiceServer)(nil),
	Methods: []grpc_go.MethodDesc{
		{
			MethodName: "Create",
			Handler:    _UserSessionService_Create_Handler,
		},
	},
	Streams:  []grpc_go.StreamDesc{},
	Metadata: "user_session_service.proto",
}
