package com.heaven.client;

import com.heaven.client.service.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

    private static final int BEGIN_PORT = 8999;
    private static final int N_PORT = 10;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        new Client().start(BEGIN_PORT, N_PORT);
    }

}
