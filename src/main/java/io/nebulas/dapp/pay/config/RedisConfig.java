//package io.nebulas.dapp.pay.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.CacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * User: nathan
// * Date: 2018-04-25
// */
//@Configuration
//public class RedisConfig {
//
//    @Value("${redis.defaultExpiration}")
//    private Long defaultExpiration;
//    @Value("${redis.host}")
//    private String host;
//    @Value("${redis.port}")
//    private int port;
//    @Value("${redis.password}")
//    private String password;
//
//    private RedisConnectionFactory generateDevConnectionFactory() {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setHostName(host);
//        factory.setPort(port);
////        factory.setPassword(password);
//        factory.setUsePool(true);
//        factory.setConvertPipelineAndTxResults(true);
//        JedisPoolConfig poolConfig = generatePoolConfig();
//        factory.setPoolConfig(poolConfig);
//        factory.afterPropertiesSet();
//        return factory;
//    }
//
//    private JedisPoolConfig generatePoolConfig() {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMinIdle(50);
//        poolConfig.setMaxTotal(500);
//        poolConfig.setMaxWaitMillis(5000);
//        poolConfig.setTestOnBorrow(true);
//        return poolConfig;
//    }
//
//    @Bean(name = "redisConnectionFactory")
//    RedisConnectionFactory factory() {
//        return generateDevConnectionFactory();
//    }
//
//    @Bean(name = "redisTemplate")
//    public RedisTemplate<String, Object> redisTemplate(
//            RedisConnectionFactory factory) {
//        final RedisTemplate<String, Object> template = new RedisTemplate<>();
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        JdkSerializationRedisSerializer jdkSerializationRedisSerializer =
//                new JdkSerializationRedisSerializer();
//
//        template.setEnableTransactionSupport(false);
//        template.setKeySerializer(stringRedisSerializer);
//        template.setHashKeySerializer(stringRedisSerializer);
//        template.setValueSerializer(jdkSerializationRedisSerializer);
//        template.setDefaultSerializer(jdkSerializationRedisSerializer);
//        template.setConnectionFactory(factory);
//        return template;
//    }
//
//    @Bean(name = "stringRedisTemplate")
//    public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory factory) {
//        final RedisTemplate<String, String> template = new RedisTemplate<>();
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        template.setEnableTransactionSupport(false);
//        template.setKeySerializer(stringRedisSerializer);
//        template.setHashKeySerializer(stringRedisSerializer);
//        template.setValueSerializer(stringRedisSerializer);
//        template.setDefaultSerializer(stringRedisSerializer);
//        template.setConnectionFactory(factory);
//        return template;
//    }
//
//    @Bean(name = "cacheManager")
//    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
//        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//        cacheManager.setDefaultExpiration(defaultExpiration);
//        cacheManager.setUsePrefix(true);
//        return cacheManager;
//    }
//
//}
