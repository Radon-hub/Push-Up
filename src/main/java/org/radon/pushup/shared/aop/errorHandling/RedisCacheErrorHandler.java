package org.radon.pushup.shared.aop.errorHandling;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheErrorHandler implements CacheErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheErrorHandler.class);
    private static boolean isRedisDown = false;

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        handleErrors("GET",exception,key);
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        handleErrors("PUT",exception,key);
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        handleErrors("EVICT",exception,key);
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        handleErrors("CLEAR",exception,null);
    }

    public void handleErrors(String operation,RuntimeException exception,Object key){
        if(exception instanceof RedisConnectionFailureException){
            if(!isRedisDown){
                isRedisDown = true;
                logger.error("🚨 Redis seems DOWN. Operation: {} Key: {}. Cause: {}", operation, key, exception.getMessage());
            }
        }else{
            logger.warn("Redis {} error for key {}: {}",operation,key,exception.getMessage());
        }
    }

}
