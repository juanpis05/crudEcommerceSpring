package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class uploadFileService {

	// En la DB se almacena el nombre y la extension de la imagen
	private String folder = "images//";

	// Este método es la carga de la imagen
	public String saveImages(MultipartFile file, String nombre) throws IOException {

		// Validación de imagen
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			// Variable de tipo path que redirige al directorio del proyecto
			Path path = Paths.get(folder + nombre + "_" + file.getOriginalFilename());
			Files.write(path, bytes);
			return nombre + "_" + file.getOriginalFilename();
		}

		return "default.jpg";
	}

	// Método para eliminar imagen
	public void deleteImages(String nombre) {
		String ruta = "images//";
		// Importe de java.io
		File file = new File(ruta + nombre);
		file.delete();

	}

}
