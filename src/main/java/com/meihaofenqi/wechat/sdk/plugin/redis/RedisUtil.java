package com.meihaofenqi.wechat.sdk.plugin.redis;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/18
 **/
public class RedisUtil {

    private JedisPool jedisPool;

    public RedisUtil(JedisPool pool) {
        this.jedisPool = pool;
    }

    public static RedisUtil use(JedisPool pool) {
        return new RedisUtil(pool);
    }

    public Object eval(String script, List<String> keys, List<String> params) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.eval(script, keys, params);
        }
    }

    public Object eval(String script, int keyCount, String[] keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.eval(script, keyCount, keys);
        }
    }

    /**
     * 通过key获取储存在redis中的value 并释放连
     *
     * @param key
     * @return 成功返回value 失败返回null
     */
    public String get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    /**
     * 向redis存入key和value,并释放连接资 如果key已经存在 则覆
     *
     * @return 成功 返回OK 失败返回 0
     */
    public String set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key, value);
        }
    }

    /**
     * 设值并增加过期时间
     * @param key
     * @param value
     * @param expireSeconds
     * @return
     */
    public String set(String key, String value, int expireSeconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setex(key, expireSeconds, value);
        }
    }

    /**
     * 向redis存入序列化的key和value,并释放连接资 如果key已经存在 则覆
     *
     * @return 成功 返回OK 失败返回 0
     */
    public String setSerializer(byte[] keyBytes, byte[] valueBytes) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(keyBytes, valueBytes);
        }
    }

    /**
     * 通过序列化key获取储存在redis中的序列化value 并释放连
     *
     * @return 成功返回value 失败返回null
     */
    public byte[] getSerializer(byte[] key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    /**
     * 删除指定的key,也可以传入一个包含key的数
     *
     * @param keys 多个key 也可以使 string 数组
     * @return 返回删除成功的个
     */
    public Long del(String... keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.del(keys);
        }
    }

    /**
     * 通过key向指定的value值追加?
     *
     * @return 成功返回 添加后value的长 失败 返回 添加 value 的长 异常返回0L
     */
    public Long append(String key, String str) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.append(key, str);
        }
    }

    /**
     * 判断key是否存在
     *
     * @return true OR false
     */
    public Boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        }
    }

    /**
     * 设置key value,如果key已经存在则返0,nx==> not exist
     *
     * @param key
     * @param value
     * @return 成功返回1 如果存在  发生异常 返回 0
     */
    public Long setnx(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setnx(key, value);
        }
    }

    /**
     * 设置key value并制定这个键值的有效期
     *
     * @param key
     * @param value
     * @param seconds 单位:
     * @return 成功返回OK 失败和异常返回null
     */
    public String setex(String key, String value, int seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setex(key, seconds, value);
        }
    }

    /**
     * 通过key 和offset 从指定的位置始将原先value替换 下标0,offset表示从offset下标始替
     * 如果替换的字符串长度过小则会这样 example: value : bigsea@zto.cn str : abc 从下7始替 则结果为
     * RES : bigsea.abc.cn
     *
     * @param key
     * @param str
     * @param offset 下标位置
     * @return 返回替换 value 的长
     */
    public Long setrange(String key, String str, int offset) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setrange(key, offset, str);
        }
    }

    /**
     * 通过批量的key获取批量的value
     *
     * @param keys string数组 也可以是个key
     * @return 成功返回value的集, 失败返回null的集 ,异常返回
     */
    public List<String> mget(String... keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.mget(keys);
        }
    }

    /**
     * 批量的设置key:value,可以 example: obj.mset(new
     * String[]{"key2","value1","key2","value2"})
     *
     * @param values
     * @return 成功返回OK 失败 异常 返回 null
     */
    public String mset(String... values) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.mset(values);
        }
    }

    /**
     * 删除多个字符串key 并释放连
     *
     * @param keys
     * @return 成功返回value 失败返回null
     */
    public boolean mdel(List<String> keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            // 获取jedis对象的pipeline对象
            Pipeline pipe = jedis.pipelined();
            for (String key : keys) {
                // 将多个key放入pipe删除指令
                pipe.del(key);
            }
            // 执行命令，完全此时pipeline对象的远程调
            pipe.sync();
            return true;
        }
    }

    /**
     * 批量的设置key:value,可以,如果key已经存在则会失败,操作会回 example: obj.msetnx(new
     * String[]{"key2","value1","key2","value2"})
     *
     * @param keysvalues
     * @return 成功返回1 失败返回0
     */
    public Long msetnx(String... keysvalues) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.msetnx(keysvalues);
        }
    }

    /**
     * 设置key的?,并返回一个旧
     *
     * @param key
     * @param value
     * @return 旧? 如果key不存 则返回null
     */
    public String getset(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.getSet(key, value);
        }
    }

    /**
     * 通过下标 和key 获取指定下标位置 value
     *
     * @param key
     * @param startOffset 始位 0  负数表示从右边开始截
     * @param endOffset
     * @return 如果没有返回null
     */
    public String getrange(String key, int startOffset, int endOffset) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.getrange(key, startOffset, endOffset);
        }
    }

    /**
     * 通过key 对value进行加?+1操作,当value不是int类型时会返回错误,当key不存在是则value1
     *
     * @param key
     * @return 加�后的结
     */
    public Long incr(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.incr(key);
        }
    }

    /**
     * 通过key给指定的value加?,如果key不存,则这是value为该
     *
     * @param key
     * @param step 步长
     * @return
     */
    public Long incyby(String key, Long step) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.incrBy(key, step);
        }
    }

    /**
     * 对key的value做减减操作,如果key不存,则设置key-1
     *
     * @param key
     * @return
     */
    public Long decr(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.decr(key);
        }
    }

    /**
     * 减去指定的值
     *
     * @param key
     * @param step
     * @return
     */
    public Long decrBy(String key, Long step) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.decrBy(key, step);
        }
    }

    /**
     * 通过key获取value值的长度
     *
     * @param key
     * @return 失败返回null
     */
    public Long serlen(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.strlen(key);
        }
    }

    /**
     * 通过key给field设置指定的?,如果key不存,则先创建
     *
     * @param key
     * @param field 字段
     * @param value
     * @return 如果存在返回0 异常返回null
     */
    public Long hset(String key, String field, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hset(key, field, value);
        }
    }

    /**
     * 通过key给field设置指定的?,如果key不存在则先创,如果field已经存在,返回0
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hsetnx(String key, String field, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hsetnx(key, field, value);
        }
    }

    /**
     * 通过key同时设置 hash的多个field
     *
     * @param key
     * @param params
     * @return 返回OK 异常返回null
     */
    public String hmset(String key, Map<String, String> params) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hmset(key, params);
        }
    }

    /**
     * 通过key  field 获取指定 value
     *
     * @param key
     * @param field
     * @return 没有返回null
     */
    public String hget(String key, String field) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hget(key, field);
        }
    }

    /**
     * 通过key  fields 获取指定的value 如果没有对应的value则返回null
     *
     * @param key
     * @param fields 可以 个String 也可以是 String数组
     * @return
     */
    public List<String> hmget(String key, String... fields) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hmget(key, fields);
        }
    }

    /**
     * 通过key给指定的field的value加上给定的?
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hincrby(String key, String field, Long value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hincrBy(key, field, value);
        }
    }

    /**
     * 通过key和field判断是否有指定的value存在
     *
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(String key, String field) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hexists(key, field);
        }
    }

    /**
     * 通过key返回field的数
     *
     * @param key
     * @return
     */
    public Long hlen(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hlen(key);
        }

    }

    public Map<String, String> hgetAll(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hgetAll(key);
        }

    }

    /**
     * 通过key 删除指定 field
     *
     * @param key
     * @param fields 可以  field 也可以是 个数
     * @return
     */
    public Long hdel(String key, String... fields) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hdel(key, fields);
        }
    }

    /**
     * 返回有序集合指定范围的member值
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrange(String key, Long start, Long end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrange(key, start, end);
        }
    }

    /**
     * 返回有序集合指定范围的member值和score值
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Tuple> zrangeWithScores(String key, Long start, Long end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeWithScores(key, start, end);
        }
    }

    /**
     * 通过key返回有的field
     *
     * @param key
     * @return
     */
    public Set<String> hkeys(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hkeys(key);
        }
    }

    /**
     * 通过key返回有和key有关的value
     *
     * @param key
     * @return
     */
    public List<String> hvals(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hvals(key);
        }
    }

    /**
     * 通过key获取有的field和value
     *
     * @param key
     * @return
     */
    public Map<String, String> hgetall(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hgetAll(key);
        }
    }

    /**
     * <p>
     * 通过key向list头部添加字符
     * </p>
     *
     * @param key
     * @param values 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    public Long lpush(String key, String... values) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lpush(key, values);
        }
    }

    /**
     * <p>
     * 通过key向list尾部添加字符
     * </p>
     *
     * @param key
     * @param values 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    public Long rpush(String key, String... values) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.rpush(key, values);
        }
    }

    /**
     * <p>
     * 通过key在list指定的位置之前或者之 添加字符串元
     * </p>
     *
     * @param key
     * @param where LIST_POSITION枚举类型
     * @param pivot list里面的value
     * @param value 添加的value
     * @return
     */
    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.linsert(key, where, pivot, value);
        }
    }

    /**
     * <p>
     * 通过key设置list指定下标位置的value
     * </p>
     * <p>
     * 如果下标超过list里面value的个数则报错
     * </p>
     *
     * @param key
     * @param index 0
     * @param value
     * @return 成功返回OK
     */
    public String lset(String key, Long index, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lset(key, index, value);
        }
    }

    /**
     * <p>
     * 通过key从对应的list中删除指定的count  value相同的元
     * </p>
     *
     * @param key
     * @param count 删除list中count个value，当count0时删除全删
     * @param value 要删除的值
     * @return 返回被删除的个数
     */
    public Long lrem(String key, long count, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrem(key, count, value);
        }
    }

    /**
     * <p>
     * 通过key保留list中从strat下标始到end下标结束的value
     * </p>
     *
     * @param key
     * @param start
     * @param end
     * @return 成功返回OK
     */
    public String ltrim(String key, long start, long end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.ltrim(key, start, end);
        }
    }

    /**
     * <p>
     * 通过key从list的头部删除一个value,并返回该value
     * </p>
     *
     * @param key
     * @return
     */
    public String lpop(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lpop(key);
        }
    }

    /**
     * <p>
     * 通过key从list尾部删除个value,并返回该元素
     * </p>
     *
     * @param key
     * @return
     */
    public String rpop(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.rpop(key);
        }
    }

    /**
     * <p>
     * 通过key从一个list的尾部删除一个value并添加到另一个list的头,并返回该value
     * </p>
     * <p>
     * 如果第一个list为空或不存在则返回null
     * </p>
     *
     * @param srckey
     * @param dstkey
     * @return
     */
    public String rpoplpush(String srckey, String dstkey) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.rpoplpush(srckey, dstkey);
        }
    }

    /**
     * <p>
     * 通过key获取list中指定下标位置的value
     * </p>
     *
     * @param key
     * @param index
     * @return 如果没有返回null
     */
    public String lindex(String key, long index) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lindex(key, index);
        }
    }

    /**
     * <p>
     * 通过key返回list的长
     * </p>
     *
     * @param key
     * @return
     */
    public Long llen(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.llen(key);
        }
    }

    /**
     * <p>
     * 通过key获取list指定下标位置的value
     * </p>
     * <p>
     * 如果start  0 end  -1 则返回全部的list中的value
     * </p>
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, long start, long end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrange(key, start, end);
        }
    }

    /**
     * <p>
     * 通过key向指定的set中添加value
     * </p>
     *
     * @param key
     * @param members 可以是一个String 也可以是个String数组
     * @return 添加成功的个
     */
    public Long sadd(String key, String... members) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sadd(key, members);
        }
    }

    public void expire(String key, int times) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.expire(key, times);
        }
    }

    /**
     * <p>
     * 通过key删除set中对应的value
     * </p>
     *
     * @param key
     * @param members 可以是一个String 也可以是个String数组
     * @return 删除的个
     */
    public Long srem(String key, String... members) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.srem(key, members);
        }
    }

    /**
     * <p>
     * 通过key随机删除个set中的value并返回该
     * </p>
     *
     * @param key
     * @return
     */
    public String spop(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.spop(key);
        }
    }

    /**
     * <p>
     * 通过key获取set中的差集
     * </p>
     * <p>
     * 以第个set为标
     * </p>
     *
     * @param keys 可以使一个string 则返回set中所有的value 也可以是string数组
     * @return
     */
    public Set<String> sdiff(String... keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sdiff(keys);
        }
    }

    /**
     * <p>
     * 通过key获取set中的差集并存入到另一个key
     * </p>
     * <p>
     * 以第个set为标
     * </p>
     *
     * @param dstkey 差集存入的key
     * @param keys   可以使一个string 则返回set中所有的value 也可以是string数组
     * @return
     */
    public Long sdiffstore(String dstkey, String... keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sdiffstore(dstkey, keys);
        }
    }

    /**
     * <p>
     * 通过key获取指定set中的交集
     * </p>
     *
     * @param keys 可以使一个string 也可以是个string数组
     * @return
     */
    public Set<String> sinter(String... keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sinter(keys);
        }
    }

    /**
     * <p>
     * 通过key获取指定set中的交集 并将结果存入新的set
     * </p>
     *
     * @param dstkey
     * @param keys   可以使一个string 也可以是个string数组
     * @return
     */
    public Long sinterstore(String dstkey, String... keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sinterstore(dstkey, keys);
        }
    }

    /**
     * <p>
     * 通过key返回有set的并集
     * </p>
     *
     * @param keys 可以使一个string 也可以是个string数组
     * @return
     */
    public Set<String> sunion(String... keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sunion(keys);
        }
    }

    /**
     * <p>
     * 通过key返回有set的并,并存入到新的set
     * </p>
     *
     * @param dstkey
     * @param keys   可以使一个string 也可以是个string数组
     * @return
     */
    public Long sunionstore(String dstkey, String... keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sunionstore(dstkey, keys);
        }
    }

    /**
     * <p>
     * 通过key将set中的value移除并添加到第二个set
     * </p>
     *
     * @param srckey 要移除的
     * @param dstkey 添加
     * @param member set中的value
     * @return
     */
    public Long smove(String srckey, String dstkey, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.smove(srckey, dstkey, member);
        }
    }

    /**
     * <p>
     * 通过key获取set中value的个
     * </p>
     *
     * @param key
     * @return
     */
    public Long scard(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.scard(key);
        }
    }

    /**
     * <p>
     * 通过key判断value是否是set中的元素
     * </p>
     *
     * @param key
     * @param member
     * @return
     */
    public Boolean sismember(String key, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sismember(key, member);
        }
    }

    /**
     * <p>
     * 通过key获取set中随机的value,不删除元素
     * </p>
     *
     * @param key
     * @return
     */
    public String srandmember(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.srandmember(key);
        }
    }

    /**
     * <p>
     * 通过key获取set中所有的value
     * </p>
     *
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.smembers(key);
        }
    }

    /**
     * 通过key向zset中添加value,score,其中score就是用来排序 如果该value已经存在则根据score更新元素
     *
     * @param key
     * @param scoreMembers
     * @return
     */
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zadd(key, scoreMembers);
        }
    }

    /**
     * 通过key向zset中添加value,score,其中score就是用来排序 如果该value已经存在则根据score更新元素
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Long zadd(String key, double score, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zadd(key, score, member);
        }
    }

    /**
     * 通过key删除在zset中指定的value
     *
     * @param key
     * @param members 可以使一个string 也可以是个string数组
     * @return
     */
    public Long zrem(String key, String... members) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrem(key, members);
        }
    }

    /**
     * 通过key增加该zset中value的score的?
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Double zincrby(String key, double score, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zincrby(key, score, member);
        }
    }

    /**
     * hash指定field增加value
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public long hincrBy(String key, String field, long value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hincrBy(key, field, value);
        }
    }

    /**
     * 通过key返回zset中value的排 下标从小到大排序
     *
     * @param key
     * @param member
     * @return
     */
    public Long zrank(String key, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrank(key, member);
        }
    }

    /**
     * 通过key返回zset中value的排 下标从大到小排序
     *
     * @param key
     * @param member
     * @return
     */
    public Long zrevrank(String key, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrank(key, member);
        }
    }

    /**
     * 通过key将获取score从start到end中zset的value socre从大到小排序 当start0 end-1时返回全
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrevrange(String key, long start, long end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrange(key, start, end);
        }
    }

    /**
     * 通过key返回指定score内zset中的value
     *
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zrangebyscore(String key, String max, String min) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeByScore(key, max, min);
        }
    }


    /**
     * 返回指定区间内zset中value的数
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zcount(String key, String min, String max) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zcount(key, min, max);
        }
    }

    /**
     * 通过key返回zset中的value个数
     *
     * @param key
     * @return
     */
    public Long zcard(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.scard(key);
        }
    }

    /**
     * 通过key获取zset中value的score
     *
     * @param key
     * @param member
     * @return
     */
    public Double zscore(String key, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zscore(key, member);
        }
    }

    /**
     * 通过key删除给定区间内的元素
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByRank(String key, long start, long end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zremrangeByRank(key, start, end);
        }
    }

    /**
     * 通过key删除指定score内的元素
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByScore(String key, double start, double end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zremrangeByScore(key, start, end);
        }
    }

    /**
     * 返回满足pattern表达式的有key keys(*) 返回有的key
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys(pattern);
        }
    }

    /**
     * 通过key判断值得类型
     *
     * @param key
     * @return
     */
    public String type(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.type(key);
        }
    }
}
