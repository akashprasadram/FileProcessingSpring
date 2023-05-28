package com.spring.fileprocessing.controller;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FilePocessController {
	
	@Value("${uploadDir}")
	private String UPLOAD_DIR;
	
	@RequestMapping(value="/home",method = RequestMethod.GET)
	public String home(ModelMap map) {
		System.out.println("Home page called............");
		map.addAttribute("fileName", "");
		return "uploadpage";
	}
	
	@RequestMapping(value="/fileupload", method= RequestMethod.POST)
	public String uploadfile(@RequestParam("file") MultipartFile file,ModelMap map) throws IllegalStateException, IOException {
		file.transferTo(new File(UPLOAD_DIR+file.getOriginalFilename()));
		
		//We can store all the file names in a database and fetch them and return them as list in modelAttribute
		//For now I am not storing any file names into the database and only return the same file which is uploaded
		map.addAttribute("fileName", file.getOriginalFilename());
		return "uploadpage";
	}
	
	@RequestMapping(value="/redirectsuccess", method= RequestMethod.GET)
	public String downloadfile() {
		return "success";
	}
	
	
	/*
	 * @RequestMapping(value="/downloadfile2/{fileName}", method= RequestMethod.GET)
	 * public ResponseEntity<ModelAndView> downloadfile2(@PathVariable("fileName")
	 * String fileName, ModelAndView mav) throws IOException{ byte[] fileData =
	 * Files.readAllBytes(new File(UPLOAD_DIR+fileName).toPath());
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * //headers.setContentType(MediaType.IMAGE_JPEG);
	 * headers.setContentDispositionFormData("success", fileName);
	 * 
	 * mav.setViewName("success"); mav.addObject(new
	 * ResponseEntity<byte[]>(fileData,headers,HttpStatus.OK)); //mav.setStatus(new
	 * ResponseEntity<byte[]>(fileData,headers,HttpStatus.OK));
	 * 
	 * return new ResponseEntity<>(mav,headers,HttpStatus.OK); }
	 */
	
	/*
	 * @RequestMapping(value="/downloadfile2/{fileName}", method= RequestMethod.GET)
	 * public ResponseEntity<String> downloadfile2(@PathVariable("fileName") String
	 * fileName) throws IOException{ byte[] fileData = Files.readAllBytes(new
	 * File(UPLOAD_DIR+fileName).toPath());
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * //headers.setContentType(MediaType.IMAGE_JPEG);
	 * headers.setContentDispositionFormData("success", fileName);
	 * 
	 * //mav.setStatus(new ResponseEntity<byte[]>(fileData,headers,HttpStatus.OK));
	 * 
	 * return new ResponseEntity<>("success",headers,HttpStatus.OK); }
	 */
}
