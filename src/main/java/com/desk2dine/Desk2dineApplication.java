package com.desk2dine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.desk2dine.util.DBSetup;

@SpringBootApplication
public class Desk2dineApplication {
	public static void main(String[] args) {
		
		DBSetup setup = new DBSetup();
		setup.createTables();
		
		
		SpringApplication.run(Desk2dineApplication.class, args);
	}

}
