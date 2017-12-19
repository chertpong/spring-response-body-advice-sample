package com.kritacademy.spring.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author krit on 12/19/2017.
 */
@ControllerAdvice
@Slf4j
public class AutoFeeControllerAdvice implements ResponseBodyAdvice<List<Room>> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.hasMethodAnnotation(AutoFee.class) || methodParameter.getContainingClass().isAnnotationPresent(AutoFee.class);
    }

    @Override
    public List<Room> beforeBodyWrite(List<Room> rooms, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        BigDecimal fee;
        if (AnnotationUtils.findAnnotation(methodParameter.getAnnotatedElement(), AutoFee.class) != null) {
            fee = new BigDecimal(AnnotationUtils.findAnnotation(methodParameter.getAnnotatedElement(), AutoFee.class).fee());
        } else {
            fee = new BigDecimal(AnnotationUtils.findAnnotation(methodParameter.getContainingClass(), AutoFee.class).fee());
        }

        for (Room room : rooms) {
            log.info("room id {} price before fee {}", room.getId(), room.getPrice());
            room.setPrice(room.getPrice().multiply(fee));
            log.info("room id {} price after fee {}", room.getId(), room.getPrice());
        }
        return rooms;
    }
}
