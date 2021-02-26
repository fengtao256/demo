package com.example.Redis;

import redis.clients.jedis.Jedis;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName RedisTest.java
 * @Description TODO
 * @createTime 2021年02月24日 09:55:00
 */
public class RedisTest {
    private Jedis jedis;

    public static void main(String[] args) {
        RedisTest redisTest = new RedisTest() ;
        System.out.println(redisTest.getConnection().ping());
        System.out.println(redisTest.getSize());
        System.out.println(redisTest.getAllData());
        redisTest.testSet();
    }

    public Jedis getConnection(){
        synchronized (this){
            if(jedis == null ){
                jedis = new Jedis("121.37.129.122") ;
            }
        }
        return jedis ;
    }

    public Object getSize(){
        return jedis.dbSize() ;
    }
    public Object getAllData(){
        //移除javaFramwork所所有内容
        jedis.del("javaFramwork");
        //存放数据
        jedis.lpush("javaFramework","spring");
        jedis.lpush("javaFramework","springMVC");
        jedis.lpush("javaFramework","mybatis");
        //取出所有数据,jedis.lrange是按范围取出
        //第一个是key，第二个是起始位置，第三个是结束位置
        System.out.println("长度:"+jedis.llen("javaFramework"));
        //jedis.llen获取长度，-1表示取得所有
        System.out.println("javaFramework:"+jedis.lrange("javaFramework",0,-1));

        jedis.del("javaFramework");
        System.out.println("删除后长度:"+jedis.llen("javaFramework"));
        System.out.println(jedis.lrange("javaFramework",0,-1));
        return "succes" ;
    }

    public void testSet(){
        //添加
        jedis.sadd("user","chenhaoxiang");
        jedis.sadd("user","hu");
        jedis.sadd("user","chen");
        jedis.sadd("user","xiyu");
        jedis.sadd("user","chx");
        jedis.sadd("user","are");
        //移除user集合中的元素are
        jedis.srem("user","are");
        System.out.println("user中的value:"+jedis.smembers("user"));//获取所有加入user的value
        System.out.println("chx是否是user中的元素:"+jedis.sismember("user","chx"));//判断chx是否是user集合中的元素
        System.out.println("集合中的一个随机元素:"+jedis.srandmember("user"));//返回集合中的一个随机元素
        System.out.println("user中元素的个数:"+jedis.scard("user"));
    }
}
