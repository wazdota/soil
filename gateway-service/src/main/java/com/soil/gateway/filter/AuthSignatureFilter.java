package com.soil.gateway.filter;

import com.soil.gateway.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthSignatureFilter implements GlobalFilter, Ordered {

    private Logger log = LoggerFactory.getLogger(AuthSignatureFilter.class);

    @Value("${jwt.header}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthSignatureFilter(JwtTokenUtil jwtTokenUtil){
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("first post filter");
        String authHeader = exchange.getRequest().getHeaders().getFirst(tokenHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring("Bearer ".length());
            log.info(authToken);
            int id = jwtTokenUtil.getUserIdFromToken(authToken);
            log.info(String.valueOf(id));
            if(id != 0) {
                ServerHttpRequest host = exchange.getRequest().mutate().header("id", String.valueOf(id)).build();
                ServerWebExchange build = exchange.mutate().request(host).build();
                return chain.filter(build);
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -200;
    }
}
