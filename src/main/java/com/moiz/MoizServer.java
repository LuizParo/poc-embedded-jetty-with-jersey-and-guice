package com.moiz;

import com.google.inject.servlet.GuiceFilter;
import com.moiz.config.Bootstrap;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class MoizServer {
    public static void main(String[] args) throws Exception {
        final Server server = new Server();

        final ServerConnector httpConnector = new ServerConnector(server);
        httpConnector.setPort(8080);
        httpConnector.setIdleTimeout(30000);

        server.setConnectors(new Connector[]{httpConnector});

        final ServletContextHandler context = new ServletContextHandler();
        context.addEventListener(new Bootstrap());
        context.addFilter(GuiceFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(context);

        try {
            server.start();
            server.join();
        } finally {
            server.stop();
        }
    }
}
