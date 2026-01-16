package io.github.gaomingyuan666.limiter.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 限流类型
 *
 * @author gaomingyuan
 */
@AllArgsConstructor
@Getter
public enum LimiterTypeEnum {
    FixedWindow("固定窗口"),
    ;

    private final String desc;

    private static Map<String, LimiterTypeEnum> cache;

    static {
        cache = Arrays.stream(LimiterTypeEnum.values()).collect(Collectors.toMap(LimiterTypeEnum::name, Function.identity()));
    }

    public static LimiterTypeEnum of(String type) {
        return cache.get(type);
    }
}
