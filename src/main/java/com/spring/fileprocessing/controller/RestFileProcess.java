package com.spring.fileprocessing.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestFileProcess {
	

	@Value("${uploadDir}")
	private String UPLOAD_DIR;
	
	/*
	@RequestMapping(value="/downloadfile/{fileName}", method= RequestMethod.GET)
	public ResponseEntity<byte[]> downloadfile(@PathVariable("fileName") String fileName) throws IOException{
		byte[] fileData = Files.readAllBytes(new File(UPLOAD_DIR+fileName).toPath());
		
		HttpHeaders headers = new HttpHeaders();
		//headers.setContentType(MediaType.IMAGE_JPEG);
		headers.setContentDispositionFormData(fileName, fileName);
		//headers.setHeader("Content-Disposition", "attachment; filename=\"myquery.sql\"");
		return new ResponseEntity<byte[]>(fileData,headers,HttpStatus.OK);
	}*/
	
	@GetMapping("/downloadfile/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) throws IOException {

        Path filePath = Paths.get(UPLOAD_DIR+fileName);  // Specify the file path

        // Load the file as a Resource
        Resource resource = new UrlResource(filePath.toUri());

        // Prepare the response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);  // Enable file download
        headers.setContentType(MediaType.APPLICATION_PDF);  // Set the appropriate content type

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
	
}
