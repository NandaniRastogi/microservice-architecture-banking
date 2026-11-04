package com.nandani.gateway.error;


import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

public class ExceptionHandler implements org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    public ExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        Map<String, Object> errorDetails = new HashMap<>();

        errorDetails.put("status", httpStatus.value());
        errorDetails.put("message", "Internal Server Error");

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorDetails);

            exchange.getResponse().setStatusCode(httpStatus);

            exchange.getResponse().getHeaders()
                    .setContentType(MediaType.APPLICATION_JSON);

            DataBuffer buffer = exchange.getResponse()
                    .bufferFactory()
                    .wrap(bytes);

            return exchange.getResponse().writeWith(Mono.just(buffer));

        } catch (Exception exception) {
            return Mono.error(exception);
        }
    }
}
