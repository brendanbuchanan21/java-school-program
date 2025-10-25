package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DemoApplication {

	@RequestMapping("/hello")
	public String hellowWorld() {
	return "Hello World";
	}
	private int counter = 0; // class member to keep state

    @RequestMapping("/counter")
    public String counteString() {
        counter++; // increment each time endpoint is called
        return "Hello World! Counter: " + counter;
    }
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
