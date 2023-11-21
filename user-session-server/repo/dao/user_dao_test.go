package dao

import (
	"gorm.io/gorm"
	"log"
	"reflect"
	"testing"
	"user-session-server/entity/config"
)

func Test_userDaoImpl_GetUserByUid(t *testing.T) {
	config.ServerConfigPath = "../../conf"
	config.Init()
	tx := config.SqlClient.Begin()
	type fields struct {
		sqlClient *gorm.DB
	}
	type args struct {
		tx  *gorm.DB
		uid int64
	}
	tests := []struct {
		name    string
		fields  fields
		args    args
		want    *User
		wantErr bool
	}{
		{
			name: "GetUserByUid",
			fields: fields{
				sqlClient: config.SqlClient,
			},
			args: args{
				tx, 1000000000,
			},
			want:    &User{Uid: 1000000000},
			wantErr: false,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			u := &userDaoImpl{
				sqlClient: tt.fields.sqlClient,
			}
			got, err := u.GetUserByUid(tt.args.tx, tt.args.uid)
			if (err != nil) != tt.wantErr {
				t.Errorf("GetUserByUid() error = %v, wantErr %v", err, tt.wantErr)
				return
			}
			t.Logf("user:%+v", got)
			if !reflect.DeepEqual(got.Uid, tt.want.Uid) {
				t.Errorf("GetUserByUid() got = %v, want %v", got, tt.want)
			}
		})
	}
	tx.Rollback()
}
func Test_simple(t *testing.T) {
	// create
	config.ServerConfigPath = "../../conf"
	config.Init()
	userDao := &userDaoImpl{
		sqlClient: config.SqlClient,
	}
	userDao.sqlClient.Transaction(func(tx *gorm.DB) error {
		user, err := userDao.GetUserByUid(tx, 1000000000)
		log.Printf("user:%+v,err:%s", user, err)
		userDao.Save(tx, &User{
			Uid:      100,
			Password: "123",
			Status:   0,
		})
		return nil
	})
}
