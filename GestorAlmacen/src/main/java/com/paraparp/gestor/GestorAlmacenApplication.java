package com.paraparp.gestor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.paraparp.gestor.service.IUploadFileService;

@SpringBootApplication
public class GestorAlmacenApplication implements CommandLineRunner{

	@Autowired
	IUploadFileService uploadFileService;
	
	public static void main(String[] args) {
		SpringApplication.run(GestorAlmacenApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
		//Cada vez que se incia el proyecto se elimina y vuelve a crear la carpeta /uploads
		uploadFileService.deleteAll();
		uploadFileService.init();
		
	}

}

