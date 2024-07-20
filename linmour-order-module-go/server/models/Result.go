package models

// Result 用于定义响应结构体
type Result struct {
	Code int         `json:"code"` // 响应编码
	Msg  string      `json:"msg"`  // 响应提示信息
	Data interface{} `json:"data"` // 响应内容
}

// NewResult 创建一个新的Result实例
func NewResult(code int, msg string, data interface{}) *Result {
	return &Result{
		Code: code,
		Msg:  msg,
		Data: data,
	}
}

// Error 创建一个错误响应
func Error(msg string) *Result {
	return NewResult(201, msg, nil)
}

// ErrorWithData 创建一个带有数据的错误响应
func ErrorWithData(data interface{}, msg string) *Result {
	return NewResult(201, msg, data)
}

// Success 创建一个成功的响应
func Success(data interface{}) *Result {
	return NewResult(200, "success", data)
}
