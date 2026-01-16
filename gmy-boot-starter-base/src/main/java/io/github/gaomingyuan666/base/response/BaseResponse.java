package io.github.gaomingyuan666.base.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 通用出参
 *
 * @author gaomingyuan
 */
@Setter
@Getter
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private String code;

    /**
     * 是否成功
     */
    private Boolean succeed = true;

    /**
     * 消息描述
     */
    private String message;

    /**
     * 数据,可以是任何类型的VO
     */
    private T data;
}
