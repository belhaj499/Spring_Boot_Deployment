package com.example.coldchain;

import com.example.coldchain.auth.AuthService;
import com.example.coldchain.domain.Role;
import com.example.coldchain.domain.User;
import com.example.coldchain.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ColdChainApplication {
  public static void main(String[] args) {
    SpringApplication.run(ColdChainApplication.class, args);
  }

  @Bean
  CommandLineRunner seedUsers(UserRepository users, AuthService auth) {
    return args -> {
      users.findByUsername("admin").orElseGet(() ->
          users.save(User.builder().username("admin").passwordHash(auth.hash("admin123")).role(Role.ADMIN).build())
      );

      users.findByUsername("user1").orElseGet(() ->
          users.save(User.builder().username("user1").passwordHash(auth.hash("user123")).role(Role.USER).build())
      );
    };
  }
}
