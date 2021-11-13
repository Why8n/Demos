package com.yn.redisdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RedisService {
    private KeysOps<String, Object> keysOps;
    private StringOps<String, Object> stringOps;
    private HashOps<String, String, Object> hashOps;
    private ListOps<String, Object> listOps;
    private SetOps<String, Object> setOps;
    private ZSetOps<String, Object> zsetOps;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.keysOps = new KeysOps<>(redisTemplate);
        this.stringOps = new StringOps<>(redisTemplate);
        this.hashOps = new HashOps<>(redisTemplate);
        this.listOps = new ListOps<>(redisTemplate);
        this.setOps = new SetOps<>(redisTemplate);
        this.zsetOps = new ZSetOps<>(redisTemplate);
    }


    /* ##### Key 操作 ##### */
    public KeysOps<String, Object> keys() {
        return this.keysOps;
    }


    /* ##### String 操作 ##### */
    public StringOps<String, Object> string() {
        return this.stringOps;
    }

    /* ##### List 操作 ##### */
    public ListOps<String, Object> list() {
        return this.listOps;
    }

    /* ##### Hash 操作 ##### */
    public HashOps<String, String, Object> hash() {
        return this.hashOps;
    }

    /* ##### Set 操作 ##### */
    public SetOps<String, Object> set() {
        return this.setOps;
    }

    /* ##### Zset 操作 ##### */
    public ZSetOps<String, Object> zset() {
        return this.zsetOps;
    }

    public static class KeysOps<K, V> {
        private final RedisTemplate<K, V> redis;

        private KeysOps(RedisTemplate<K, V> redis) {
            this.redis = redis;
        }

        /**
         * 删除一个 key
         *
         * @param key
         * @return
         */
        public Boolean delete(K key) {
            return this.redis.delete(key);
        }

        /**
         * 批量删除 key
         *
         * @param keys
         * @return
         */
        public Long delete(K... keys) {
            return this.redis.delete(Arrays.asList(keys));
        }

        /**
         * 批量删除 key
         *
         * @param keys
         * @return
         */
        public Long delete(Collection<K> keys) {
            return this.redis.delete(keys);
        }

        /**
         * 设置 key 过期时间
         *
         * @param key     键值
         * @param timeout 过期时间
         * @return
         */
        public Boolean expire(K key, long timeout, TimeUnit timeunit) {
            return this.redis.expire(key, timeout, timeunit);
        }

        /**
         * 设置 key 过期时间
         *
         * @param key
         * @param date 指定过期时间
         * @return
         */
        public Boolean expireAt(K key, Date date) {
            return this.redis.expireAt(key, date);
        }

        /**
         * 获取 key 过期时间
         *
         * @param key 键值
         * @return key 对应的过期时间（单位：毫秒）
         */
        public Long getExpire(K key, TimeUnit timeunit) {
            return this.redis.getExpire(key, timeunit);
        }

        /**
         * 判断 key 是否存在
         *
         * @param key
         * @return key 存在返回 TRUE
         */
        public Boolean hasKey(K key) {
            return this.redis.hasKey(key);
        }

        /**
         * 模糊匹配 key
         *
         * @param pattern 匹配模式（可使用通配符）
         * @return 返回匹配的所有键值
         */
        public Set<K> keys(K pattern) {
            return this.redis.keys(pattern);
        }

        /**
         * 返回数据库所有键值
         *
         * @return
         */
        public Set<K> keys() {
            return this.keys((K) "*");
        }

        /**
         * 序列化 key
         *
         * @param key
         * @return 返回 key 序列化的字节数组
         */
        public byte[] dump(K key) {
            return this.redis.dump(key);
        }

        /**
         * 移除 key 过期时间，相当于持久化 key
         *
         * @param key
         * @return
         */
        public Boolean persist(K key) {
            return this.redis.persist(key);
        }

        /**
         * 从当前数据库中随机返回一个 key
         *
         * @return
         */
        public K random() {
            return this.redis.randomKey();
        }

        /**
         * 重命名 key
         *
         * @param oldKey
         * @param newKey
         */
        public void rename(K oldKey, K newKey) {
            this.redis.rename(oldKey, newKey);
        }

        /**
         * 仅当 newKey 不存在时，才将 oldKey 重命名为 newKey
         *
         * @param oldKey
         * @param newKey
         * @return
         */
        public Boolean renameIfAbsent(K oldKey, K newKey) {
            return this.redis.renameIfAbsent(oldKey, newKey);
        }

        /**
         * 返回 key 存储值对应的类型
         *
         * @param key
         * @return
         */
        public DataType type(K key) {
            return this.redis.type(key);
        }
    }

    public static class StringOps<K, V> {
        private final ValueOperations<K, V> valueOps;

        private StringOps(RedisTemplate<K, V> redis) {
            this.valueOps = redis.opsForValue();
        }

        /**
         * 设置 key 对应的 value
         *
         * @param key
         * @param value
         */
        public void set(K key, V value) {
            this.valueOps.set(key, value);
        }

        /**
         * 设置键值，附带过期时间
         *
         * @param key
         * @param value
         * @param timeout
         * @param unit
         */
        public void set(K key, V value, long timeout, TimeUnit unit) {
            this.valueOps.set(key, value, timeout, unit);
        }

        /**
         * 只有当 key 不存在时，才进行设置
         *
         * @param key
         * @param value
         * @return
         */
        public Boolean setIfAbsent(K key, V value) {
            return this.valueOps.setIfAbsent(key, value);
        }

        /**
         * 当 key 不存在时，进行设置，同时指定其过期时间
         * @param key
         * @param value
         * @param timeout
         * @param unit
         * @return
         */
        public Boolean setIfAbsent(K key, V value, long timeout, TimeUnit unit) {
            return this.valueOps.setIfAbsent(key, value, timeout, unit);
        }

        /**
         * 获取 key 对应的 value
         *
         * @param key
         * @return
         */
        public V get(K key) {
            return this.valueOps.get(key);
        }

        /**
         * 批量添加
         *
         * @param map
         */
        public void multiSet(Map<K, V> map) {
            this.valueOps.multiSet(map);
        }

        /**
         * 批量添加键值对（只有当 key 不存在时，才会进行添加）
         *
         * @param map
         * @return
         */
        public Boolean multiSetIfAbsent(Map<K, V> map) {
            return this.valueOps.multiSetIfAbsent(map);
        }

        /**
         * 批量获取 key 对应的 value
         *
         * @param keys
         * @return key 对应的 value（按访问顺序排列）
         */
        public List<V> multiGet(K... keys) {
            return this.valueOps.multiGet(Arrays.asList(keys));
        }

        /**
         * 批量获取 key 对应的 value
         *
         * @param keys
         * @return
         */
        public List<V> multiGet(Collection<K> keys) {
            return this.valueOps.multiGet(keys);
        }

        /**
         * 将指定 key 的值设为 value，并返回 key 的旧值
         *
         * @param key
         * @param value
         * @return key 的旧值
         */
        public V getAndSet(K key, V value) {
            return this.valueOps.getAndSet(key, value);
        }

        /**
         * 将 key 对应的 value 添加一个步进 delta（value 仍以字符串存储）
         *
         * @param key
         * @param delta
         */
        public void increment(K key, long delta) {
            this.valueOps.increment(key, delta);
        }
    }

    public static class HashOps<K, HK, HV> {
        private final HashOperations<K, HK, HV> hashOps;

        private HashOps(RedisTemplate<K, ?> redis) {
            this.hashOps = redis.opsForHash();
        }

        /**
         * 获取 key 对应的哈希表
         *
         * @param key
         * @return
         */
        public Map<HK, HV> getMap(K key) {
            return this.hashOps.entries(key);
        }

        /**
         * 从 key 对应的哈希表中查找 hashKey 的值
         *
         * @param key
         * @param hashKey
         * @return
         */
        public HV get(K key, HK hashKey) {
            return this.hashOps.get(key, hashKey);
        }

        /**
         * 从 key 对应哈希表中批量获取给定字段的值
         *
         * @param key
         * @param hashKeys
         * @return
         */
        public List<HV> multiGet(K key, HK... hashKeys) {
            return this.hashOps.multiGet(key, Arrays.asList(hashKeys));
        }

        /**
         * 从 key 对应哈希表中批量获取给定字段的值
         *
         * @param key
         * @param hashKeys
         * @return
         */
        public List<HV> multiGet(K key, Collection<HK> hashKeys) {
            return this.hashOps.multiGet(key, hashKeys);
        }

        /**
         * 插入 (hashKey,value) 到 key 对应的哈希表中
         *
         * @param key
         * @param hashKey
         * @param value
         */
        public void put(K key, HK hashKey, HV value) {
            this.hashOps.put(key, hashKey, value);
        }

        /**
         * 只有当 key 对应的哈希表不存在 hashKey 时，才进行插入
         *
         * @param key
         * @param hashKey
         * @param value
         * @return
         */
        public Boolean putIfAbsent(K key, HK hashKey, HV value) {
            return this.hashOps.putIfAbsent(key, hashKey, value);
        }

        /**
         * 批量插入到 key 对应的哈希表中
         *
         * @param key
         * @param map
         */
        public void putAll(K key, Map<? extends HK, ? extends HV> map) {
            this.hashOps.putAll(key, map);
        }

        /**
         * 删除一个或多个哈希字段
         *
         * @param key
         * @param hashKeys
         * @return
         */
        public Long delete(K key, HK... hashKeys) {
            return this.hashOps.delete(key, hashKeys);
        }

        /**
         * 哈希表是否存在指定字段
         *
         * @param key
         * @param hashKey
         * @return
         */
        public Boolean exists(K key, HK hashKey) {
            return this.hashOps.hasKey(key, hashKey);
        }

        /**
         * 获取哈希表中的所有字段
         *
         * @param key
         * @return
         */
        public Set<HK> keys(K key) {
            return this.hashOps.keys(key);
        }

        /**
         * 获取哈希表中的所有值
         *
         * @param key
         * @return
         */
        public List<HV> values(K key) {
            return this.hashOps.values(key);
        }


        /**
         * 查看 key 对应哈希表大小
         *
         * @param key
         * @return 哈希表大小
         */
        public Long size(K key) {
            return this.hashOps.size(key);
        }

    }

    public static class ListOps<K, V> {
        private final ListOperations<K, V> listOps;

        private ListOps(RedisTemplate<K, V> redis) {
            this.listOps = redis.opsForList();
        }

        /**
         * 获取列表索引对应元素
         *
         * @param key
         * @param index
         * @return
         */
        public V get(K key, long index) {
            return this.listOps.index(key, index);
        }

        /**
         * 获取列表指定范围内的元素
         *
         * @param key
         * @param start
         * @param end
         * @return
         */
        public List<V> range(K key, long start, long end) {
            return this.listOps.range(key, start, end);
        }

        /**
         * 获取列表所有元素
         *
         * @param key
         * @return
         */
        public List<V> getList(K key) {
            return this.range(key, 0, -1);
        }

        /**
         * 插入数据到列表头部
         *
         * @param key
         * @param value
         * @return
         */
        public Long leftPush(K key, V value) {
            return this.listOps.leftPush(key, value);
        }

        /**
         * value 插入到值 pivot 前面
         *
         * @param key
         * @param pivot
         * @param value
         * @return
         */
        public Long leftPush(K key, V pivot, V value) {
            return this.listOps.leftPush(key, pivot, value);
        }

        /**
         * 批量插入数据到列表头部
         *
         * @param key
         * @param values
         * @return
         */
        public Long leftPushAll(K key, V... values) {
            return this.listOps.leftPushAll(key, values);
        }

        /**
         * 批量插入数据到列表头部
         *
         * @param key
         * @param values
         * @return
         */
        public Long leftPushAll(K key, Collection<V> values) {
            return this.listOps.leftPushAll(key, values);
        }

        /**
         * 插入数据到列表尾部
         *
         * @param key
         * @param value
         * @return
         */
        public Long push(K key, V value) {
            return this.listOps.rightPush(key, value);
        }

        /**
         * value 插入到值 pivot 后面
         *
         * @param key
         * @param pivot
         * @param value
         * @return
         */
        public Long rightPush(K key, V pivot, V value) {
            return this.listOps.rightPush(key, pivot, value);
        }

        /**
         * 设置元素到指定索引位置
         *
         * @param key
         * @param index
         * @param value
         */
        public void set(K key, long index, V value) {
            this.listOps.set(key, index, value);
        }

        /**
         * 移除列表头部元素
         *
         * @param key
         * @return 返回移除的头部元素
         */
        public V leftPop(K key) {
            return this.listOps.leftPop(key);
        }

        /**
         * 移除列表尾部元素
         *
         * @param key
         * @return 返回移除的尾部元素
         */
        public V pop(K key) {
            return this.listOps.rightPop(key);
        }

        /**
         * 删除值为 value 的 count 个元素
         *
         * @param key
         * @param count count = 0: 删除列表所有值为 value 的元素
         *              count > 0: 从头到尾，删除 count 个值为 value 的元素
         *              count < 0: 从尾到头，删除 count 个值为 value 的元素
         * @param value
         * @return 实际删除的元素个数
         */
        public Long remove(K key, long count, V value) {
            return this.listOps.remove(key, count, value);
        }

        /**
         * 删除列表值为 value 的所有元素
         *
         * @param key
         * @param value
         * @return
         */
        public Long removeAll(K key, V value) {
            return this.remove(key, 0, value);
        }

        /**
         * 裁剪列表，只保留 [start, end] 区间的元素
         *
         * @param key
         * @param start
         * @param end
         */
        public void trim(K key, long start, long end) {
            this.listOps.trim(key, start, end);
        }

        /**
         * 获取列表长度
         *
         * @param key
         * @return
         */
        public Long size(K key) {
            return this.listOps.size(key);
        }
    }

    public static class SetOps<K, V> {

        private final SetOperations<K, V> setOps;

        private SetOps(RedisTemplate<K, V> redis) {
            this.setOps = redis.opsForSet();
        }

        /**
         * 集合添加元素
         *
         * @param key
         * @param value
         * @return
         */
        public Long add(K key, V value) {
            return this.setOps.add(key, value);
        }

        /**
         * 弹出元素
         *
         * @param key
         * @return 返回弹出的元素
         */
        public V pop(K key) {
            return this.setOps.pop(key);
        }

        /**
         * 批量移除元素
         *
         * @param key
         * @param values
         * @return
         */
        public Long remove(K key, V... values) {
            return this.setOps.remove(key, values);
        }

        /**
         * 获取集合所有元素
         *
         * @param key
         * @return
         */
        public Set<V> getSet(K key) {
            return this.setOps.members(key);
        }

        /**
         * 获取集合大小
         *
         * @param key
         * @return
         */
        public Long size(K key) {
            return this.setOps.size(key);
        }

        /**
         * 判断集合是否包含指定元素
         *
         * @param key
         * @param value
         * @return
         */
        public Boolean contains(K key, Object value) {
            return this.setOps.isMember(key, value);
        }

        /**
         * 获取 key 集合和其他 key 指定的集合之间的交集
         *
         * @param key
         * @param otherKeys
         * @return
         */
        public Set<V> intersect(K key, Collection<K> otherKeys) {
            return this.setOps.intersect(key, otherKeys);
        }

        /**
         * 获取多个集合的交集
         *
         * @param key
         * @param otherKeys
         * @return
         */
        public Set<V> intersect(K key, K... otherKeys) {
            return this.intersect(key, Stream.of(otherKeys).collect(Collectors.toSet()));
        }

        /**
         * 获取 key 集合和其他 key 指定的集合之间的并集
         *
         * @param key
         * @param otherKeys
         * @return
         */
        public Set<V> union(K key, Collection<K> otherKeys) {
            return this.setOps.union(key, otherKeys);
        }

        /**
         * 获取多个集合之间的并集
         *
         * @param key
         * @param otherKeys
         * @return
         */
        public Set<V> union(K key, K... otherKeys) {
            return this.union(key, Stream.of(otherKeys).collect(Collectors.toSet()));
        }

        /**
         * 获取 key 集合和其他 key 指定的集合间的差集
         *
         * @param key
         * @param otherKeys
         * @return
         */
        public Set<V> difference(K key, Collection<K> otherKeys) {
            return this.setOps.difference(key, otherKeys);
        }

        /**
         * 获取多个集合间的差集
         *
         * @param key
         * @param otherKeys
         * @return
         */
        public Set<V> difference(K key, K... otherKeys) {
            return this.difference(key, Stream.of(otherKeys).collect(Collectors.toSet()));
        }
    }

    public static class ZSetOps<K, V> {
        private final ZSetOperations<K, V> zsetOps;

        private ZSetOps(RedisTemplate<K, V> redis) {
            this.zsetOps = redis.opsForZSet();
        }

        /**
         * 添加元素（有序集合内部按元素的 score 从小到达进行排序）
         *
         * @param key
         * @param value
         * @param score
         * @return
         */
        public Boolean add(K key, V value, double score) {
            return this.zsetOps.add(key, value, score);
        }

        /**
         * 批量删除元素
         *
         * @param key
         * @param values
         * @return
         */
        public Long remove(K key, V... values) {
            return this.zsetOps.remove(key, values);
        }

        /**
         * 增加元素 value 的 score 值
         *
         * @param key
         * @param value
         * @param delta
         * @return 返回元素增加后的 score 值
         */
        public Double incrementScore(K key, V value, double delta) {
            return this.zsetOps.incrementScore(key, value, delta);
        }

        /**
         * 返回元素 value 在有序集合中的排名（按 score 从小到大排序）
         *
         * @param key
         * @param value
         * @return 0 表示排名第一，依次类推
         */
        public Long rank(K key, V value) {
            return this.zsetOps.rank(key, value);
        }

        /**
         * 返回元素 value 在有序集合中的排名（按 score 从大到小排序）
         *
         * @param key
         * @param value
         * @return
         */
        public Long reverseRank(K key, V value) {
            return this.zsetOps.reverseRank(key, value);
        }

        /**
         * 获取有序集合指定范围 [start, end] 之间的元素（默认按 score 由小到大排序）
         *
         * @param key
         * @param start
         * @param end
         * @return
         */
        public Set<V> range(K key, long start, long end) {
            return this.zsetOps.range(key, start, end);
        }

        /**
         * 获取有序集合所有元素（默认按 score 由小到大排序）
         *
         * @param key
         * @return
         */
        public Set<V> getZSet(K key) {
            return this.range(key, 0, -1);
        }

        /**
         * 获取有序集合指定区间 [start, end] 内的所有元素，同时携带对应的 score 值。
         *
         * @param key
         * @param start
         * @param end
         * @return
         */
        public Set<ZSetOperations.TypedTuple<V>> rangeWithScores(K key, long start, long end) {
            return this.zsetOps.rangeWithScores(key, start, end);
        }

        /**
         * 获取 score 介于 [min, max] 之间的所有元素（按 score 由小到大排序）
         *
         * @param key
         * @param min
         * @param max
         * @return
         */
        public Set<V> rangeByScore(K key, double min, double max) {
            return this.zsetOps.rangeByScore(key, min, max);
        }

        /**
         * 获取 score 介于 [min, max] 之间的所有元素，同时携带其 score 值
         *
         * @param key
         * @param min
         * @param max
         * @return
         */
        public Set<ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max) {
            return this.zsetOps.rangeByScoreWithScores(key, min, max);
        }

        /**
         * 返回有序集合指定区间 [start, end] 内的所有元素（按元素 score 值从大到小排列）
         *
         * @param key
         * @param start
         * @param end
         * @return
         */
        public Set<V> reverseRange(K key, long start, long end) {
            return this.zsetOps.reverseRange(key, start, end);
        }

        /**
         * 获取有序集合指定区间 [start, end] 内的所有元素，包含其 score 值，且按 score 值由大到小排列
         *
         * @param key
         * @param start
         * @param end
         * @return
         */
        public Set<ZSetOperations.TypedTuple<V>> reverseRangeWithScore(K key, long start, long end) {
            return this.zsetOps.reverseRangeWithScores(key, start, end);
        }

        /**
         * 获取 score 介于 [min, max] 之间的所有元素（按 score 由大到小排序）
         *
         * @param key
         * @param min
         * @param max
         * @return
         */
        public Set<V> reverseRangeByScore(K key, double min, double max) {
            return this.zsetOps.reverseRangeByScore(key, min, max);
        }

        /**
         * 获取 score 介于 [min, max] 之间的所有元素，同时携带其 score 值，元素按 score 值由大到小排序
         *
         * @param key
         * @param min
         * @param max
         * @return
         */
        public Set<ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(K key, double min, double max) {
            return this.zsetOps.reverseRangeByScoreWithScores(key, min, max);
        }


        /**
         * 获取 score 值介于 [min, max] 之间的元素数量
         *
         * @param key
         * @param min
         * @param max
         * @return
         */
        public Long count(K key, double min, double max) {
            return this.zsetOps.count(key, min, max);
        }

        /**
         * 获取有序集合大小
         *
         * @param key
         * @return
         */
        public Long size(K key) {
            return this.zsetOps.size(key);
        }

        /**
         * 获取指定元素 value 的 score 值
         *
         * @param key
         * @param value
         * @return
         */
        public Double score(K key, V value) {
            return this.zsetOps.score(key, value);
        }

        /**
         * 移除指定区间 [start, end] 的元素
         *
         * @param key
         * @param start
         * @param end
         * @return
         */
        public Long removeRange(K key, long start, long end) {
            return this.zsetOps.removeRange(key, start, end);
        }

        /**
         * 移除 score 指定区间 [min, max] 内的所有元素
         *
         * @param key
         * @param min
         * @param max
         * @return
         */
        public Long removeRangeByScore(K key, double min, double max) {
            return this.zsetOps.removeRangeByScore(key, min, max);
        }
    }
}
