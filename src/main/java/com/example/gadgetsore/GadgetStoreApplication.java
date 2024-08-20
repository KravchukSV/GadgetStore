package com.example.gadgetsore;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Gadget Store",
                description = "RESTfull API which is implemented by an online gadget store",
                version = "v.1.0",
                contact = @Contact(
                        name = "Serhii",
                        email = "kravchuk.s.v.1@gmail.com"
                )
        )
)

@SpringBootApplication
public class GadgetStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(GadgetStoreApplication.class, args);
    }

}
