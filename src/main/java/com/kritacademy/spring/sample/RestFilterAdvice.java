package com.kritacademy.spring.sample;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author krit on 7/10/2017.
 */
@Slf4j
@ControllerAdvice
public class RestFilterAdvice implements ResponseBodyAdvice<List<Object>> {
    private static final String FILTER_KEY = "filter";

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Check whether the target method has @RestFilter or not
     * @param methodParameter
     * @param aClass
     * @return true if support
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasMethodAnnotation(RestFilter.class);
    }

    @Override
    public List beforeBodyWrite(List<Object> list, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.info("list = {}", list);
        HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        // check whether the request has filter key or not
        if (httpServletRequest.getParameterMap().containsKey(FILTER_KEY)) {
            log.info("RestFilter: triggered");
            Set<String> filterKeys = new HashSet<>(Arrays.asList(httpServletRequest.getParameter(FILTER_KEY).split(",")));
            log.info("RestFilter: filter keys = {}", filterKeys);

            if (filterKeys.isEmpty()) {
                log.info("RestFilter: filter nothing since filter key is empty");
                return list;
            }

            return list.stream()
                    .map(e -> objectMapper.valueToTree(e))
                    .map(e -> {
                        Iterator<String> fieldNameIterator = ((JsonNode)e).fieldNames();
                        Set<String> fieldNameSet = new HashSet<>();
                        while (fieldNameIterator.hasNext()) {
                            fieldNameSet.add(fieldNameIterator.next());
                        }
                        // remove all filter keys and the left will be the attribute name that we should remove
                        fieldNameSet.removeAll(filterKeys);
                        // remove the rest, except field(s) in filter key(s)
                        for (String shouldRemoveFieldName : fieldNameSet) {
                            ((ObjectNode) e).remove(shouldRemoveFieldName);
                        }
                        return e;
                    }).collect(Collectors.toList());
        }
        return list;
    }

}
