package com.computercomponent.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
@EnableAsync
@OpenAPIDefinition(info = @Info(title = "Computer Components  Admin API", version = "1.0", description = "Anhpq"))
@SecurityScheme(name = "computer-components-admin-security", scheme = "Bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class ComputerComponentsShopBeApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ComputerComponentsShopBeApplication.class);
		Environment env = app.run(args).getEnvironment();
		logApplicationStartup(env);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	private static void logApplicationStartup(Environment env) {
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		String serverPort = env.getProperty("server.port");
		String contextPath = env.getProperty("server.servlet.context-path");
		if (StringUtils.isBlank(contextPath)) {
			contextPath = "/";
		}
		String serverIp = "localhost";
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			serverIp = localhost.getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("Can not get server IP");
		}
		log.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\t{}://localhost:{}{}\n\t" +
						"server: \t{}://{}:{}{}\n\t" +
						"swagger: \t {}://" + serverIp + ":{}{}swagger-ui.html\n\t" +
						"Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				protocol,
				serverPort,
				contextPath,
				protocol,
				serverIp,
				serverPort,
				contextPath,
				protocol,
				serverPort,
				contextPath,
				env.getActiveProfiles());
	}

}
