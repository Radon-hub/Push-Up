package org.radon.pushup.features.event.application.service;

import org.radon.pushup.features.event.application.port.in.IsEventDuplicateUseCase;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class IdempotencyService implements IsEventDuplicateUseCase {

    private final RedisTemplate<String,String> redisTemplate;

    public IdempotencyService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Boolean isDuplicate(UUID appId,String eventId){
        String key = "event:"+appId+":"+eventId;
        Boolean exists = redisTemplate.hasKey(key);

        if(exists!=null && exists){
            return true;
        }

        redisTemplate.opsForValue().set(key,"1", Duration.ofHours(24));
        return false;
    }


}
