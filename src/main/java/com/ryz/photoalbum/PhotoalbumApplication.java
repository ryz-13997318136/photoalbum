package com.ryz.photoalbum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages="com.ryz.photoalbum")
@EntityScan(basePackages="com.ryz.photoalbum.pojo")
@EnableJpaRepositories(basePackages="com.ryz.photoalbum.repository")
@SpringBootApplication
public class PhotoalbumApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoalbumApplication.class, args);
	}
}
