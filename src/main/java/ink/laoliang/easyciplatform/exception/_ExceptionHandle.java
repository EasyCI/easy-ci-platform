package ink.laoliang.easyciplatform.exception;

import ink.laoliang.easyciplatform.domain.response.ExceptionResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class _ExceptionHandle {

//    @ExceptionHandler(value = RuntimeException.class)
//    @ResponseBody
//    public ExceptionResponse handleRuntimeException(RuntimeException e) {
//        return new ExceptionResponse(e.getClass().getSimpleName(), e.getMessage());
//    }
}
