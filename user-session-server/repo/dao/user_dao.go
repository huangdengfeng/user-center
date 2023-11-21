package dao

import (
	"fmt"
	"gorm.io/gorm"
	"time"
	"user-session-server/entity/config"
	"user-session-server/entity/errors"
)

type User struct {
	Uid        int64 `gorm:"primaryKey"`
	Password   string
	Status     int8
	CreateTime time.Time `gorm:"autoCreateTime:true"`
	UpdateTime time.Time `gorm:"autoUpdateTime:true"`
}

func (User) TableName() string {
	return "t_user"
}

type UserDao interface {
	GetUserByUid(*gorm.DB, int64) (*User, error)
	Save(*gorm.DB, *User) (int64, error)
}

type userDaoImpl struct {
	sqlClient *gorm.DB
}

func NewUserDao() UserDao {
	return &userDaoImpl{sqlClient: config.SqlClient}
}

func (u *userDaoImpl) GetUserByUid(tx *gorm.DB, uid int64) (*User, error) {
	var user User
	result := tx.Limit(1).Find(&user, "uid = ?", uid)
	if result.Error != nil {
		return nil, result.Error
	}
	if result.RowsAffected == 0 {
		return nil, nil
	}
	return &user, nil
}

func (u *userDaoImpl) Save(tx *gorm.DB, user *User) (int64, error) {
	result := tx.Create(user)
	if result.Error != nil {
		return 0, result.Error
	}
	if result.RowsAffected != 1 {
		return 0, errors.New(errors.UnexpectRowsAffected, fmt.Sprintf("rows affected %d,expected %d", result.RowsAffected, 1))
	}
	return user.Uid, nil
}
