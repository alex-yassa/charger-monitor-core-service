package pl.twerdenergoplus.charger.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
@EnableConfigurationProperties
@EnableCaching
public class MonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorApplication.class, args);
	}

}
