package com.service.onestopgateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/token",
            "/eureka",
            "/auth/userId",
            "/app/home",
            "/app/invoice-details",
            "/subscribe/plan",
            "/subscribe/create-invoice"
        //     "/invoice/generate-invoice",
        //     "/invoice/create"  

    );  

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}

