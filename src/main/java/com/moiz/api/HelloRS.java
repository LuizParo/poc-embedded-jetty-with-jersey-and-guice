package com.moiz.api;

import com.moiz.model.Hello;
import com.moiz.service.HelloService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;

@Path("/yeast")
public class HelloRS {

    private final HelloService helloService;

    @Inject
    public HelloRS(HelloService helloService) {
        this.helloService = helloService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response yeast() {
        return Response.ok(new Hello(helloService.sayHello(), Instant.now().toEpochMilli())).build();
    }
}
