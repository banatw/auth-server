package com.example.authserver;

import com.entity.User;
import com.model.Result;
import com.model.UserModel;
import com.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableJpaRepositories("com.repository")
@EntityScan("com.entity")
public class AuthServerApplication {
	@Autowired
	private UserRepo userRepo;

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return(x)->{
			User user = new User();
			user.setUsername("bana");
			user.setPassword("123");
			userRepo.save(user);
		};
	}


	@RestController
	public class MyRestController {

		@PostMapping("/login")
		public Result login(UserModel model) {
			User user = userRepo.findOne(model.getUsername());
			Result result = new Result();
			if(user.getPassword().equals(model.getPassword())) {
				result.setSuccess(true);
				result.setValue("OK");
			}
			else {
				result.setSuccess(false);
				result.setValue("NOT OK");
			}
			return result;
		}
	}




}
