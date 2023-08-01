package com.esprit.socialnetwork;



import com.esprit.socialnetwork.User.RoleEnum;
import com.esprit.socialnetwork.User.auth.RegisterRequest;
import com.esprit.socialnetwork.User.auth.authService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.esprit.socialnetwork.*")
@ComponentScan(basePackages = "com.esprit.socialnetwork.*")

public class SocialNetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkApplication.class, args);
	}

/*	@Bean
	public CommandLineRunner commandLineRunner(
			authService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("client")
					.lastname("client")
					.email("client@mail.com")
					.password("password")
					.role(RoleEnum.CLIENT)
					.build();
			System.out.println("Client token: " + service.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
					.firstname("SELLER")
					.lastname("SELLER")
					.email("seller@mail.com")
					.password("password")
					.role(RoleEnum.SELLER)
					.build();
			System.out.println("Seller token: " + service.register(manager).getAccessToken());

		};
	}*/
}
