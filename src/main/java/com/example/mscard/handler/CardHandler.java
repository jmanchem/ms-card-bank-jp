package com.example.mscard.handler;

import com.example.mscard.model.entities.Card;
import com.example.mscard.services.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class CardHandler {
    private final ICardService creditService;

    @Autowired
    public CardHandler(ICardService creditService) {
        this.creditService = creditService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(creditService.findAll(), Card.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        String productId = request.pathVariable("productId");
        return creditService.findById(productId).flatMap(p -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(p))
                        .switchIfEmpty(ServerResponse.notFound().build()
        );
    }

    public Mono<ServerResponse> save(ServerRequest request){
        Mono<Card> product = request.bodyToMono(Card.class);
        return product.flatMap(creditService::create)
                .flatMap(p -> ServerResponse.created(URI.create("/api/client/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p))
                .onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;
                    if(errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(errorResponse.getResponseBodyAsString());
                    }
                    return Mono.error(errorResponse);
                });
    }
    public Mono<ServerResponse> update(ServerRequest request){
        Mono<Card> product = request.bodyToMono(Card.class);
        String id = request.pathVariable("id");
        return product
                        .flatMap(p -> {
                            p.setId(id);
                            return creditService.update(p);
                        })
                        .flatMap(p-> ServerResponse.created(URI.create("/api/product/".concat(p.getId())))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(p)
        );
    }

    public Mono<ServerResponse> delete(ServerRequest request){
        String id = request.pathVariable("id");
        return creditService.delete(id).then(ServerResponse.noContent().build());
    }
}
