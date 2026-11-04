package com.nandani.gateway.filter;

import java.util.UUID;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class CorrelationFilter implements GlobalFilter, Ordered {

    private static final String CORRELATION_ID = "X-Correlation-Id";

    @Override
    public int getOrder() {
        return -2;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String currentCorrelationId = exchange.getRequest()
                .getHeaders()
                .getFirst(CORRELATION_ID);

        String correlationId = currentCorrelationId != null
                ? currentCorrelationId
                : UUID.randomUUID().toString();

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(builder -> builder.headers(headers ->
                        headers.set(CORRELATION_ID, correlationId)))
                .build();

        return chain.filter(mutatedExchange);
    }
}
