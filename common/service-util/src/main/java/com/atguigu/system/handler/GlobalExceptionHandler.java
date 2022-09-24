package com.atguigu.system.handler;

import com.atguigu.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zzj
 * @date 2022/9/20
 */

/**
 * 配置一个全局的异常处理器
 */
@ControllerAdvice //声明当前类是一个异常处理器
@ResponseBody
public class GlobalExceptionHandler {

    //全局的处理异常的方法
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    //创建一个处理数学异常的方法
    @ExceptionHandler(ArithmeticException.class)
    public Result doArithmeticException(ArithmeticException e){
        e.printStackTrace();
        return Result.fail().code(444).message("出现了数学异常");
    }

    //处理自定义异常的方法
    @ExceptionHandler(GuiguException.class)
    public Result doGuiguException(GuiguException e){
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMessage());
    }
}
