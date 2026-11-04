package com.nandani.gateway.filter;

import java.util.UUID;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.micrometer.observation.GlobalObservationConvention;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class BankingRequestLoginFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        long startTime = System.currentTimeMillis();

        String requestId = exchange.getRequest()
                .getHeaders()
                .getFirst("X-Request-ID");

        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }

        String finalRequestId = requestId;

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(builder -> builder.headers(headers ->
                        headers.set("X-Request-ID", finalRequestId)))
                .build();

        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();
        String user = exchange.getRequest().getHeaders()
                .getFirst("X-User-Id");

        log.info("[REQ] id={} path={} method={} userId={}",
                requestId, path, method, user);

        return chain.filter(mutatedExchange)
                .doOnSuccess(v -> {
                    long timeTaken = System.currentTimeMillis() - startTime;

                    log.info("[RES] id={} status={} time={}ms",
                            finalRequestId,
                            mutatedExchange.getResponse().getStatusCode(),
                            timeTaken);
                })
                .doOnError(ex -> {
                    long timeTaken = System.currentTimeMillis() - startTime;

                    log.error("[ERR] id={} {} {} time={}ms error={}",
                            finalRequestId, method, path,
                            timeTaken, ex.getMessage());
                });
    }
}
