package com.example.mscard.config;

import com.example.mscard.handler.CardHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> rutas(CardHandler handler){
        return route(GET("/card"), handler::findAll)
                .andRoute(GET("/card/{id}"), handler::findById)
                .andRoute(POST("/card"), handler::save);
    }
}
