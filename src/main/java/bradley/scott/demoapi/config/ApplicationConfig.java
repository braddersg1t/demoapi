package bradley.scott.demoapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Application wide configuration here
 * 
 * @author scottbradley
 */
@Configuration
public class ApplicationConfig {

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
}
