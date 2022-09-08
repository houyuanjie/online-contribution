package edu.xsyu.onlinesubmit.controller.advice;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({SizeLimitExceededException.class, javax.naming.SizeLimitExceededException.class})
    public ResponseEntity<Map<String, Object>> sizeLimitExceededExceptionHandler() {
        var map = new HashMap<String, Object>();

        map.put("code", 500);
        map.put("msg", "大小超出限制 (10MB)");

        return ResponseEntity.ok(map);
    }

}
