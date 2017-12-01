package com.idb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
@PropertySource("classpath:config/redis.properties")
public class RedisConfig extends CachingConfigurerSupport{
    @Autowired
    private Environment env;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(env.getProperty("redis.host").trim());
        jedisConnectionFactory.setPort(Integer.parseInt(env.getProperty("redis.port").trim()));
        jedisConnectionFactory.setPassword(env.getProperty("redis.password").trim());
        jedisConnectionFactory.setDatabase(Integer.parseInt(env.getProperty("redis.database").trim()));
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        return jedisConnectionFactory;
    }

    public <T> RedisTemplate redisTemplate(Class<T> valueType) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}