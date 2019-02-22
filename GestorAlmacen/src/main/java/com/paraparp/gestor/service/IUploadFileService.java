package com.paraparp.gestor.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {

	// DEvuelve el nombre unico cambiado de la imagen
	public String copy(MultipartFile file) throws IOException;

	public boolean delete(String filename);
	
	public void deleteAll();
	
	public void init() throws IOException;

}
