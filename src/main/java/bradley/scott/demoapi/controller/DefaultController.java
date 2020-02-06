package bradley.scott.demoapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

	/**
	 * Hello World Test
	 * 
	 * @return String
	 */
	@GetMapping("/")
	public String greeting() {
		return "Hello World!";
	}
	
}
