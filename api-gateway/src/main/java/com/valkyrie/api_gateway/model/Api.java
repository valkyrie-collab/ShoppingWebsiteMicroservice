package com.valkyrie.api_gateway.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class Api {

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route("product-post").POST("/product/**", http())
                .before(uri("http://localhost:8081/")).build()
                .and(
                        route("product-get").GET("/product/**", http())
                                .before(uri("http://localhost:8081/")).build()
                ).and(
                        route("product-delete").DELETE("/product/**", http())
                                .before(uri("http://localhost:8081/")).build()
                ).and(
                        route("order-post").POST("/order/**", http())
                                .before(uri("http://localhost:8082/")).build()
                ).and(
                        route("order-get").GET("/order/**", http())
                                .before(uri("http://localhost:8082/")).build()
                ).and(
                        route("order-delete").DELETE("/order/**", http())
                                .before(uri("http://localhost:8082/")).build()
                ).and(
                        route("cart-post").POST("/cart/**", http())
                                .before(uri("http://localhost:8083/")).build()
                ).and(
                        route("cart-get").GET("/cart/**", http())
                                .before(uri("http://localhost:8083/")).build()
                ).and(
                        route("cart-delete").DELETE("/cart/**", http())
                                .before(uri("http://localhost:8083/")).build()
                ).and(
                        route("authentication-post").POST("/user/**", http())
                                .before(uri("http://localhost:8084/")).build()
                );
    }
}
