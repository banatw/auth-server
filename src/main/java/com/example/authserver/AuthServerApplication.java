package com.example.authserver;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.entity.AppClient;
import com.entity.User;
import com.model.Result;
import com.model.UserModel;
import com.repository.AppClientRepo;
import com.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;


@SpringBootApplication
@EnableJpaRepositories("com.repository")
@EntityScan("com.entity")
public class AuthServerApplication {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AppClientRepo appClientRepo;

	@Value("${secret.key}")
	private String secretKey;

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return(x)->{
			User user = new User();
			user.setUsername("user");
			user.setPassword("user123");
			userRepo.save(user);

			user = new User();
			user.setUsername("admin");
			user.setPassword("admin123");
			userRepo.save(user);

			AppClient client = new AppClient();
			client.setClientId(1);
			client.setName("Aplikasi 1");
			client.setRedirectUrl("localhost/vali-ci/home/has_login/");
			appClientRepo.save(client);
		};
	}

	@Controller
	public class MyController {

		@GetMapping("/show_login")
		public String showLogin(@RequestParam(name = "id",defaultValue = "1") Integer idApp, Model model) {
			AppClient appClient = appClientRepo.findOne(idApp);
			model.addAttribute("client",appClient);
			return "login";
		}
	}


	@RestController
	public class MyRestController {

		@PostMapping("/login")
		public Result login(UserModel model) {

			User user = userRepo.findOne(model.getUsername());
			Result result = new Result();
			String token = null;
			if(user.getPassword().equals(model.getPassword())) {
				result.setSuccess(true);
				try {
					Algorithm algorithm = Algorithm.HMAC256(secretKey);
					token = JWT.create()
							.withIssuer(user.getUsername())
							.sign(algorithm);
				} catch (UnsupportedEncodingException exception){
					//UTF-8 encoding not supported
				} catch (JWTCreationException exception){
					//Invalid Signing configuration / Couldn't convert Claims.
				}
				result.setValue(token);
			}
			else {
				result.setSuccess(false);
				result.setValue(token);
			}
			return result;
		}
	}




}
