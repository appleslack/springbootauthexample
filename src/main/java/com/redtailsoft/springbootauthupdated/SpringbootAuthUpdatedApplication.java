/*
*
* https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
*/

package com.redtailsoft.springbootauthupdated;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringbootAuthUpdatedApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAuthUpdatedApplication.class, args);
	}

	/*
	The second problem, the missing BCryptPasswordEncoder instance, we solve by implementing a method 
	that generates an instance of BCryptPasswordEncoder. This method must be annotated with @Bean and we
	 will add it in the SpringbootAuthUpdatedApplication class
	*/
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder () {
		return new BCryptPasswordEncoder();
	}

	
}
