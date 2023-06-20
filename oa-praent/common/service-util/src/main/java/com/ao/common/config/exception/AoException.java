package com.ao.common.config.exception;

import com.ao.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class AoException extends RuntimeException{
    private Integer code;
    private String msg;
    public AoException(Integer code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    public AoException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "AoException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
