package cc.mikaka.ddd.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${springdoc.api-docs.dev-url}")
    private String devUrl;

    @Value("${springdoc.api-docs.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("826305303@qq.com");
        contact.setName("Mika");
        contact.setUrl("https://blog.mikaka.cc");


        Info info = new Info()
                .title("DDD最佳实践")
                .version("1.0")
                .contact(contact)
                .description("swagger api文档");

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
