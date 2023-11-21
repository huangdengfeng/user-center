package errors

import "fmt"

const (
	successCode = 0
	successMsg  = "success"
)

var UnexpectRowsAffected int32 = 10000

type Error struct {
	Code int32
	Msg  string
}

func New(code int32, msg string) *Error {
	return &Error{code, msg}
}

func Newf(code int32, msg string, args ...any) *Error {
	formatted := fmt.Sprintf(msg, args)
	return New(code, formatted)
}

func (err *Error) Error() string {
	if err.Code == successCode {
		return successMsg
	}
	return fmt.Sprintf("code %d, msg: %s", err.Code, err.Msg)
}
