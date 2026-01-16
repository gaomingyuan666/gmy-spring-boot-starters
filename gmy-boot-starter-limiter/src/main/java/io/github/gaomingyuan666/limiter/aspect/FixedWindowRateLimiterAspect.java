package io.github.gaomingyuan666.limiter.aspect;


import cn.hutool.core.util.StrUtil;
import io.github.gaomingyuan666.base.utils.SpElUtils;
import io.github.gaomingyuan666.limiter.domain.annotation.FixedWindowRateLimiter;
import io.github.gaomingyuan666.limiter.domain.dto.FrequencyControlDTO;
import io.github.gaomingyuan666.limiter.domain.enums.LimiterTypeEnum;
import io.github.gaomingyuan666.limiter.domain.service.FrequencyControlUtil;
import io.github.gaomingyuan666.limiter.domain.service.LimiterTargetResolver;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 频控实现
 *
 * @author gaomingyuan
 */
@Slf4j
@Aspect
@Component
public class FixedWindowRateLimiterAspect {

    @Autowired
    private LimiterTargetResolver limiterTargetResolver;

    @Around("@annotation(io.github.gaomingyuan666.limiter.domain.annotation.FixedWindowRateLimiter)||@annotation(io.github.gaomingyuan666.limiter.domain.annotation.FixedWindowRateLimiterContainer)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        FixedWindowRateLimiter[] annotationsByType = method.getAnnotationsByType(FixedWindowRateLimiter.class);
        Map<String, FixedWindowRateLimiter> keyMap = new HashMap<>();
        for (int i = 0; i < annotationsByType.length; i++) {
            FixedWindowRateLimiter frequencyControl = annotationsByType[i];
            String prefix = StrUtil.isBlank(frequencyControl.prefixKey()) ? SpElUtils.getMethodKey(method) + ":index:" + i : frequencyControl.prefixKey();//默认方法限定名+注解排名（可能多个）
            String key = "";
            switch (frequencyControl.target()) {
                case EL:
                    key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), frequencyControl.spEl());
                    break;
                case IP:
                    key = limiterTargetResolver.getIp();
                    break;
                case UID:
                    key = limiterTargetResolver.getUid();
            }
            keyMap.put(prefix + ":" + key, frequencyControl);
        }
        // 将注解的参数转换为编程式调用需要的参数
        List<FrequencyControlDTO> frequencyControlDTOS = keyMap.entrySet().stream().map(entrySet -> buildFixedWindowRateLimiterDTO(entrySet.getKey(), entrySet.getValue())).collect(Collectors.toList());
        // 调用编程式注解
        return FrequencyControlUtil.executeWithFrequencyControlList(LimiterTypeEnum.FixedWindow.name(), frequencyControlDTOS, joinPoint::proceed);
    }

    /**
     * 将注解参数转换为编程式调用所需要的参数
     *
     * @param key                    频率控制Key
     * @param fixedWindowRateLimiter 注解
     * @return 编程式调用所需要的参数-FrequencyControlDTO
     */
    private FrequencyControlDTO buildFixedWindowRateLimiterDTO(String key, FixedWindowRateLimiter fixedWindowRateLimiter) {
        FrequencyControlDTO frequencyControlDTO = new FrequencyControlDTO();
        frequencyControlDTO.setCount(fixedWindowRateLimiter.count());
        frequencyControlDTO.setTime(fixedWindowRateLimiter.time());
        frequencyControlDTO.setUnit(fixedWindowRateLimiter.unit());
        frequencyControlDTO.setKey(key);
        return frequencyControlDTO;
    }
}
