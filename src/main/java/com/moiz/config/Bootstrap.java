package com.moiz.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Bootstrap extends GuiceServletContextListener {

    private final static Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    @Override
    protected Injector getInjector() {

        return Guice.createInjector(new JerseyServletModule() {

            @Override
            protected void configureServlets() {
                install(new MoizModule());

                final Map<String, String> initParams = ImmutableMap.<String, String>builder()
                        .put(PackagesResourceConfig.PROPERTY_PACKAGES, "com.moiz.api")
                        .build();

                serve("/*").with(GuiceContainer.class, initParams);
            }

            @Provides
            @Singleton
            public ObjectMapper objectMapper() {

                final ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JaxbAnnotationModule());
                mapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
                mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

                return mapper;
            }

            @Provides
            @Singleton
            public JacksonJaxbJsonProvider jacksonJaxbJsonProvider(ObjectMapper mapper) {

                return new JacksonJaxbJsonProvider(mapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
            }
        });
    }
}
