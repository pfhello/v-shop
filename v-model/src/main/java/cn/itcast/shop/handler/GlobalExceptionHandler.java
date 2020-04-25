package cn.itcast.shop.handler;

import cn.itcast.shop.exception.GlobalException;
import cn.itcast.shop.result.CodeMsg;
import cn.itcast.shop.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handler(Exception e){
        if (e instanceof GlobalException){
            GlobalException ge=(GlobalException)e;
            return Result.fail(ge.getCodeMsg());
        }
        return Result.fail(CodeMsg.SERVER_ERROR);
    }
}
