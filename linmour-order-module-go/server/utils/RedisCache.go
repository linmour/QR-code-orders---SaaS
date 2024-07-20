package utils

import (
	"context"
	"encoding/json"
	"fmt"
	"server/global"
	"time"
)

/*------------------------------------ 字符 操作 ------------------------------------*/

// Set 设置 key的值
func Set(key, value string) bool {
	result, err := global.RedisClient.Set(context.Background(), key, value, 0).Result()
	if err != nil {

		return false
	}
	return result == "OK"
}

// SetEX 设置 key的值并指定过期时间
func SetEX(key, value string, ex time.Duration) bool {
	result, err := global.RedisClient.Set(context.Background(), key, value, ex).Result()
	if err != nil {

		return false
	}
	return result == "OK"
}

// Get 获取 key的值
func Get(key string) (bool, string) {
	result, err := global.RedisClient.Get(context.Background(), key).Result()
	if err != nil {

		return false, ""
	}
	return true, result
}

// GetSet 设置新值获取旧值
func GetSet(key, value string) (bool, string) {
	oldValue, err := global.RedisClient.GetSet(context.Background(), key, value).Result()
	if err != nil {

		return false, ""
	}
	return true, oldValue
}

// Incr key值每次加一 并返回新值
func Incr(key string) int64 {
	val, err := global.RedisClient.Incr(context.Background(), key).Result()
	if err != nil {

	}
	return val
}

// IncrBy key值每次加指定数值 并返回新值
func IncrBy(key string, incr int64) int64 {
	val, err := global.RedisClient.IncrBy(context.Background(), key, incr).Result()
	if err != nil {

	}
	return val
}

// IncrByFloat key值每次加指定浮点型数值 并返回新值
func IncrByFloat(key string, incrFloat float64) float64 {
	val, err := global.RedisClient.IncrByFloat(context.Background(), key, incrFloat).Result()
	if err != nil {

	}
	return val
}

// Decr key值每次递减 1 并返回新值
func Decr(key string) int64 {
	val, err := global.RedisClient.Decr(context.Background(), key).Result()
	if err != nil {

	}
	return val
}

// DecrBy key值每次递减指定数值 并返回新值
func DecrBy(key string, incr int64) int64 {
	val, err := global.RedisClient.DecrBy(context.Background(), key, incr).Result()
	if err != nil {

	}
	return val
}

// Del 删除 key
func Del(key string) bool {
	result, err := global.RedisClient.Del(context.Background(), key).Result()
	if err != nil {
		return false
	}
	return result == 1
}

// Expire 设置 key的过期时间
func Expire(key string, ex time.Duration) bool {
	result, err := global.RedisClient.Expire(context.Background(), key, ex).Result()
	if err != nil {
		return false
	}
	return result
}

/*------------------------------------ list 操作 ------------------------------------*/

// LPush 从列表左边插入数据，并返回列表长度
func LPush(key string, date ...interface{}) int64 {
	result, err := global.RedisClient.LPush(context.Background(), key, date).Result()
	if err != nil {

	}
	return result
}

// RPush 从列表右边插入数据，并返回列表长度
func RPush(key string, date ...interface{}) int64 {
	result, err := global.RedisClient.RPush(context.Background(), key, date).Result()
	if err != nil {

	}
	return result
}

// LPop 从列表左边删除第一个数据，并返回删除的数据
func LPop(key string) (bool, string) {
	val, err := global.RedisClient.LPop(context.Background(), key).Result()
	if err != nil {

		return false, ""
	}
	return true, val
}

// RPop 从列表右边删除第一个数据，并返回删除的数据
func RPop(key string) (bool, string) {
	val, err := global.RedisClient.RPop(context.Background(), key).Result()
	if err != nil {
		fmt.Println(err)
		return false, ""
	}
	return true, val
}

// LIndex 根据索引坐标，查询列表中的数据
func LIndex(key string, index int64) (bool, string) {
	val, err := global.RedisClient.LIndex(context.Background(), key, index).Result()
	if err != nil {

		return false, ""
	}
	return true, val
}

// LLen 返回列表长度
func LLen(key string) int64 {
	val, err := global.RedisClient.LLen(context.Background(), key).Result()
	if err != nil {

	}
	return val
}

// LRange 返回列表的一个范围内的数据，也可以返回全部数据
func LRange(key string, start, stop int64) []string {
	vales, err := global.RedisClient.LRange(context.Background(), key, start, stop).Result()
	if err != nil {

	}
	return vales
}

// LRem 从列表左边开始，删除元素data， 如果出现重复元素，仅删除 count次
func LRem(key string, count int64, data interface{}) bool {
	_, err := global.RedisClient.LRem(context.Background(), key, count, data).Result()
	if err != nil {
		fmt.Println(err)
	}
	return true
}

// LInsert 在列表中 pivot 元素的后面插入 data
func LInsert(key string, pivot int64, data interface{}) bool {
	err := global.RedisClient.LInsert(context.Background(), key, "after", pivot, data).Err()
	if err != nil {

		return false
	}
	return true
}

/*------------------------------------ set 操作 ------------------------------------*/

// SAdd 添加元素到集合中
func SAdd(key string, data ...interface{}) bool {
	err := global.RedisClient.SAdd(context.Background(), key, data).Err()
	if err != nil {

		return false
	}
	return true
}

// SCard 获取集合元素个数
func SCard(key string) int64 {
	size, err := global.RedisClient.SCard(context.Background(), "key").Result()
	if err != nil {

	}
	return size
}

// SIsMember 判断元素是否在集合中
func SIsMember(key string, data interface{}) bool {
	ok, err := global.RedisClient.SIsMember(context.Background(), key, data).Result()
	if err != nil {

	}
	return ok
}

// SMembers 获取集合所有元素
func SMembers(key string) []string {
	es, err := global.RedisClient.SMembers(context.Background(), key).Result()
	if err != nil {

	}
	return es
}

// SRem 删除 key集合中的 data元素
func SRem(key string, data ...interface{}) bool {
	_, err := global.RedisClient.SRem(context.Background(), key, data).Result()
	if err != nil {

		return false
	}
	return true
}

// SPopN 随机返回集合中的 count个元素，并且删除这些元素
func SPopN(key string, count int64) []string {
	vales, err := global.RedisClient.SPopN(context.Background(), key, count).Result()
	if err != nil {

	}
	return vales
}

/*------------------------------------ hash 操作 ------------------------------------*/

// HSet 根据 key和 field字段设置，field字段的值
func HSet(key, field, value string) bool {
	err := global.RedisClient.HSet(context.Background(), key, field, value).Err()
	if err != nil {
		return false
	}
	return true
}

func GetHashValue(key string, field string) interface{} {
	return global.RedisClient.HGet(context.Background(), key, field)
}

func SetHashValue(key string, field string, value interface{}) error {
	hashOperations := global.RedisClient.HMSet(context.Background(), key, map[string]interface{}{field: value})
	return hashOperations.Err()
}

func SetCacheList(key string, orderItems ...interface{}) {
	for _, item := range orderItems {
		// 序列化 OrderItem 为 JSON 字符串
		jsonData, _ := json.Marshal(item)
		// 使用 RPush 将 JSON 字符串推入 Redis 列表
		global.RedisClient.RPush(context.Background(), key, string(jsonData))

	}
}

// HGet 根据 key和 field字段，查询field字段的值
func HGet(key, field string) string {
	val, err := global.RedisClient.HGet(context.Background(), key, field).Result()
	if err != nil {

	}
	return val
}

// HMGet 根据key和多个字段名，批量查询多个 hash字段值
func HMGet(key string, fields ...string) []interface{} {
	vales, err := global.RedisClient.HMGet(context.Background(), key, fields...).Result()
	if err != nil {
		panic(err)
	}
	return vales
}

// HGetAll 根据 key查询所有字段和值
func HGetAll(key string) map[string]string {
	data, err := global.RedisClient.HGetAll(context.Background(), key).Result()
	if err != nil {

	}
	return data
}

// HKeys 根据 key返回所有字段名
func HKeys(key string) []string {
	fields, err := global.RedisClient.HKeys(context.Background(), key).Result()
	if err != nil {

	}
	return fields
}

// HLen 根据 key，查询hash的字段数量
func HLen(key string) int64 {
	size, err := global.RedisClient.HLen(context.Background(), key).Result()
	if err != nil {

	}
	return size
}

// HMSet 根据 key和多个字段名和字段值，批量设置 hash字段值

func SetHashValues[T any](key string, entries map[string]T) error {
	var err error
	for k, v := range entries {
		err = global.RedisClient.HSet(context.Background(), key, k, v).Err()
		if err != nil {
			return err
		}
	}
	return nil
}

// HSetNX 如果 field字段不存在，则设置 hash字段值
func HSetNX(key, field string, value interface{}) bool {
	result, err := global.RedisClient.HSetNX(context.Background(), key, field, value).Result()
	if err != nil {

		return false
	}
	return result
}

// HDel 根据 key和字段名，删除 hash字段，支持批量删除
func HDel(key string, fields ...string) bool {
	_, err := global.RedisClient.HDel(context.Background(), key, fields...).Result()
	if err != nil {

		return false
	}
	return true
}

// HExists 检测 hash字段名是否存在
func HExists(key, field string) bool {
	result, err := global.RedisClient.HExists(context.Background(), key, field).Result()
	if err != nil {

		return false
	}
	return result
}

// GetAllHash 获取 Redis 中哈希表的所有键值对
func GetAllHash(key string) map[string]interface{} {
	// 使用 HGETALL 命令获取哈希表的所有字段和值
	values, err := global.RedisClient.HGetAll(context.Background(), key).Result()
	if err != nil {
		return nil
	}

	// 将结果转换为 map[string]interface{} 类型
	hashMap := make(map[string]interface{})
	for k, v := range values {
		// 键是字符串，值需要根据实际情况转换
		// 这里假设值是字符串，如果值是其他类型，需要相应转换
		hashMap[k] = v
	}

	return hashMap
}

func GetListValue(key string, index int64) interface{} {

	return global.RedisClient.LIndex(context.Background(), key, index)
}

func SetListValue(key string, index int64, value interface{}) {
	// 序列化value为JSON字符串，以支持任意类型
	jsonValue, _ := json.Marshal(value)
	global.RedisClient.LSet(context.Background(), key, index, string(jsonValue))
}

func GetCacheList[T any](key string) ([]T, error) {
	start := int64(0)
	stop := int64(-1)
	values, err := global.RedisClient.LRange(context.Background(), key, start, stop).Result()
	if err != nil {
		return nil, err
	}

	var result []T // 这里result的类型是[]T，但T的具体类型需要在调用时指定

	// 你需要一个具体类型的变量来反序列化JSON数据
	// 例如，如果T是MyStruct类型，你需要声明var t MyStruct
	// 这里使用反射来动态创建类型T的实例，但你需要提供类型信息
	var t T
	for _, value := range values {
		if err := json.Unmarshal([]byte(value), t); err != nil {
			return nil, err
		}
		result = append(result, t) // 将反序列化的数据添加到结果切片中
	}

	return result, nil
}

func DeleteObject(key string) {
	global.RedisClient.Del(context.Background(), key)
}
