package service

import (
	"context"
	"user-session-server/repo/pb"
)

type UserSessionService struct {
	pb.UnimplementedUserSessionServiceServer
}

func (u *UserSessionService) Create(ctx context.Context, in *pb.CreateSessionReq) (*pb.CreateSessionResp, error) {
	return &pb.CreateSessionResp{SessionId: "123444"}, nil
}
