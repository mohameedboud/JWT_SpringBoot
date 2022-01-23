package com.example.jsonwebtoken;

import com.example.jsonwebtoken.dao.TaskRepository;
import com.example.jsonwebtoken.entities.AppRole;
import com.example.jsonwebtoken.entities.AppUser;
import com.example.jsonwebtoken.entities.Task;
import com.example.jsonwebtoken.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.stream.Stream;

@SpringBootApplication
public class JsonWebTokenApplication implements CommandLineRunner {

     @Autowired
     private AccountService accountService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RepositoryRestConfiguration restConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(JsonWebTokenApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCPE() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        restConfiguration.exposeIdsFor(Task.class);

        accountService.saveUser(new AppUser(null, "admin", "1234", null));
        accountService.saveUser(new AppUser(null, "user", "1234", null));
        accountService.saveRole(new AppRole(null, "ADMIN"));
        accountService.saveRole(new AppRole(null, "USER"));
        accountService.addRoleToUser("admin", "ADMIN");
        accountService.addRoleToUser("user", "USER");



        Stream.of("T1","T2","T3").forEach(t ->{
            taskRepository.save(new Task(null, t));
        });
        taskRepository.findAll().forEach(t ->{
            System.out.println(t.getTaskName());
        });
    }
}
