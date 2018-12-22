package com.moiz.config;

import com.google.inject.AbstractModule;
import com.moiz.service.HelloService;

public class MoizModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HelloService.class);
    }
}
