import com.google.inject.servlet.GuiceFilter;
import com.moiz.Bootstrap;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class MoizAppServer {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        File pathSupremo = new File(System.getProperty("java.io.tmpdir")).getAbsoluteFile();
        File pathSupremoDemais = new File(System.getProperty("java.io.tmpdir") + "/webapps").getAbsoluteFile();
        FileUtils.forceMkdir(pathSupremoDemais);
        tomcat.setBaseDir(
                pathSupremo.toString()
        );

        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        tomcat.getService().addConnector(connector);

        customizeConnector(connector);

        tomcat.setConnector(connector);
        tomcat.getHost().setAutoDeploy(false);

        Context context = tomcat.addContext("", "");
        context.addApplicationListener(Bootstrap.class.getCanonicalName());

        FilterDef guiceFilter = new FilterDef();
        guiceFilter.setFilter(new GuiceFilter());
        guiceFilter.setFilterName("guiceFilter");

        context.addFilterDef(guiceFilter);

        tomcat.addServlet(context, "Hello", new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                resp.getWriter().println("yeast");
            }
        });

        context.addServletMappingDecoded("/hello", "Hello");

        FilterMap guiceFilterMapping = new FilterMap();
        guiceFilterMapping.setFilterName("guiceFilter");
        guiceFilterMapping.addURLPattern("/*");

        context.addFilterMap(guiceFilterMapping);

        tomcat.start();
        tomcat.getServer().await();
    }

    private static void customizeConnector(Connector connector) {
        connector.setPort(8080);
    }
}
