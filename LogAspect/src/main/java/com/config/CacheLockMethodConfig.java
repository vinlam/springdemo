package com.config;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.util.StringUtils;

import com.define.annotation.CacheLock;
import com.service.CacheKeyGenerator;
import com.service.impl.CacheKeyGeneratorImp;

import redis.clients.jedis.JedisCommands;

//分布式注解
@Aspect
@Configuration
public class CacheLockMethodConfig {
	private final static Logger logger = LoggerFactory.getLogger(CacheLockMethodConfig.class);

	// 主函数引入key生成策略
//	@Bean
//	public CacheKeyGenerator cacheKeyGenerator() {
//		return new CacheKeyGeneratorImp();
//	}
//
//	//@Autowired
//	public CacheLockMethodConfig(StringRedisTemplate stringRedisTemplate, CacheKeyGenerator cacheKeyGenerator) {
//		this.cacheKeyGenerator = cacheKeyGenerator;
//		this.stringRedisTemplate = stringRedisTemplate;
//	}
//
//	private final StringRedisTemplate stringRedisTemplate;
//	private final CacheKeyGenerator cacheKeyGenerator;
	// NX,XX
    //NX----key不存在则保存，XX----key存在则保存
    private static final String STNX= "NX";

    //EX,PX
    //EX-秒，PX-毫秒
    private static final String SET_EXPIRE_TIME = "EX";
	@Autowired
	private  StringRedisTemplate stringRedisTemplate;
	@Autowired
	private  CacheKeyGenerator cacheKeyGenerator;
	@Around("execution(public * * (..)) && @annotation(com.define.annotation.CacheLock)")
    public Object interceptor(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        final CacheLock cacheLock = method.getAnnotation(CacheLock.class);
        if(StringUtils.isEmpty(cacheLock.prefix())){
            throw new RuntimeException("前缀不能为空");
        }
        //获取自定义key
        final String lockkey = cacheKeyGenerator.getLockKey(joinPoint);
        //final Boolean success = stringRedisTemplate.execute(
        //        (RedisCallback<Boolean>) connection -> connection.set(lockkey.getBytes(), new byte[0], Expiration.from(cacheLock.expire(), cacheLock.timeUnit())
        //                , RedisStringCommands.SetOption.SET_IF_ABSENT));
        
        //boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockkey,lockkey,Expiration.from(cacheLock.expire(), cacheLock.timeUnit()), TimeUnit.SECONDS);
        final Boolean success = stringRedisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                //String uuid = UUID.randomUUID().toString();
                String res = commands.set(lockkey, lockkey, STNX, SET_EXPIRE_TIME, cacheLock.expire());
                logger.info("doInRedis_res:"+res);
                if(StringUtils.isEmpty(res)) {
                	return false;
                }
                return true;
			}
//			@Override
//			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//				// TODO Auto-generated method stub
//				connection.set(lockkey.getBytes(), new byte[0], Expiration.from(cacheLock.expire(), cacheLock.timeUnit()), RedisStringCommands.SetOption.SET_IF_ABSENT);
//				return true;
//				//2.0以上
//				//return connection.set(lockkey.getBytes(), new byte[0], Expiration.from(cacheLock.expire(), cacheLock.timeUnit()), RedisStringCommands.SetOption.SET_IF_ABSENT);
//				//return true;
//			}
//			@Override
//			public String doInRedis(RedisConnection connection) throws DataAccessException {
//			    Object nativeConnection = connection.getNativeConnection();
//			    String status = null;
//			    RedisSerializer<String> stringRedisSerializer = (RedisSerializer<String>) stringRedisTemplate.getKeySerializer();
//
//			    byte[] keyByte = stringRedisSerializer.serialize(key);
//			    //springboot 2.0以上的spring-data-redis 包默认使用 lettuce连接包
//
//			    //lettuce连接包，集群模式，ex为秒，px为毫秒
//			    if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
//			      logger.debug("lettuce Cluster:---setKey:"+setKey+"---value"+value+"---maxTimes:"+expireSeconds);
//			      status = ((RedisAdvancedClusterAsyncCommands) nativeConnection)
//			          .getStatefulConnection().sync()
//			          .set(keyByte,keyByte,SetArgs.Builder.nx().ex(30));
//			      logger.debug("lettuce Cluster:---status:"+status);
//			    }
//			    //lettuce连接包，单机模式，ex为秒，px为毫秒
//			    if (nativeConnection instanceof RedisAsyncCommands) {
//			      logger.debug("lettuce single:---setKey:"+setKey+"---value"+value+"---maxTimes:"+expireSeconds);
//			      status = ((RedisAsyncCommands ) nativeConnection)
//			      .getStatefulConnection().sync()
//			      .set(keyByte,keyByte, SetArgs.Builder.nx().ex(30));
//			      logger.debug("lettuce single:---status:"+status);
//			    }
//			    return status;
//			  }
		});
        if (!success) {
            // TODO 按理来说 我们应该抛出一个自定义的 CacheLockException 异常;这里偷下懒
            throw new RuntimeException("请勿重复请求");
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("系统异常");
        }
    }
}