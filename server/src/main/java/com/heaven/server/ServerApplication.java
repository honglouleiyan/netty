package com.heaven.server;

import com.heaven.server.service.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ServerApplication {

    private static final int BEGIN_PORT = 8999;
    private static final int N_PORT = 10;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        new Server().start(BEGIN_PORT, N_PORT);
        log.info("启动netty server from " + BEGIN_PORT + " to " + N_PORT);
    }

}
