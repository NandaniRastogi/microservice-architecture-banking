package com.nandani.gateway.ratelimit;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;
@Component
public class UserKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {

        String userId = exchange.getRequest()
                .getHeaders()
                .getFirst("X-User-Id");

        if (userId != null && !userId.isBlank()) {
            return Mono.just(userId);
        }

        return Mono.justOrEmpty(exchange.getRequest().getRemoteAddress())
                .map(address -> address.getAddress() != null
                        ? address.getAddress().getHostAddress()
                        : address.getHostString())
                .defaultIfEmpty("unknown");
    }
}
