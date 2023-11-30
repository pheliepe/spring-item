package com.example.springboot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;

@RestController
public class HelloController {

	@Autowired
	private ApplicationContext context;

	/*
	 * Einfacher Test, ob die Anwendung läuft.
	 */

	@GetMapping("/")
	public String index(HttpSession session) {
		String message = "Greetings from Item Manager in Spring Boot!";
		// Access the appStore singleton
		AppStore appStore = context.getBean(AppStore.class);
		return message + " " + appStore.getItemCount();
	}



}
