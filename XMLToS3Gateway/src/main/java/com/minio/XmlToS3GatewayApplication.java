package com.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = "com.minio")
public class XmlToS3GatewayApplication {

    public static void main(String[] args) {
		SpringApplication.run(XmlToS3GatewayApplication.class, args);

	}

}
