package ru.vladigeras.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author vladi_geras on 09/10/2018
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Autowired
	private ZuulProperties properties;

	@Primary
	@Bean
	public SwaggerResourcesProvider swaggerResourcesProvider() {
		return () -> {
			var resources = new ArrayList<SwaggerResource>();
			properties.getRoutes().values()
					.forEach(route -> resources.add(createResource(route.getServiceId(), route.getId(), "1.0")));
			return resources;
		};
	}

	private SwaggerResource createResource(String name, String location, String version) {
		var swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation("/api/" + location + "/v2/api-docs");
		swaggerResource.setSwaggerVersion(version);
		return swaggerResource;
	}
}
