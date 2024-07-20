package utils

import (
	"errors"
	"os"
)

// PathExists 判断文件目录是否存在
func PathExists(path string) (bool, error) {
	fi, err := os.Stat(path)
	if err == nil {
		if fi.IsDir() {
			return true, nil
		}
		return false, errors.New("存在同名文件")
	}

	// 用于判断一个错误是否表示文件或目录不存在
	if os.IsNotExist(err) {
		return false, nil
	}
	return false, err
}
