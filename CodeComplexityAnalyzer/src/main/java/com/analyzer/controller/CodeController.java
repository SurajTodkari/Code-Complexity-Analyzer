package com.analyzer.controller;

import java.io.IOException;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.analyzer.dto.CodeRequest;
import com.analyzer.dto.ComplexityResponse;
import com.analyzer.service.CodeAnalysisService;

@CrossOrigin(origins = "http://localhost:5173"+ "")
@RestController
@RequestMapping("/code")
public class CodeController {
	
	private final CodeAnalysisService codeAnalysisService;

    public CodeController(CodeAnalysisService codeAnalysisService) {
        this.codeAnalysisService = codeAnalysisService;
    }

	@PostMapping("/uploadFile")
	public ResponseEntity<ComplexityResponse> uploadFile(@RequestParam("file") MultipartFile file){
		if(file.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty. Please upload a valid file.");
		}
		else {
			try {
				String codeContent = new String(file.getBytes());
				ComplexityResponse response = codeAnalysisService.analyze(codeContent);
	            return ResponseEntity.ok(response);
				
			} catch (IOException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading the file: " + e.getMessage());
			}			 
		}
	}
	
	@PostMapping("/uploadCode")
	public ResponseEntity<ComplexityResponse> uploadCode(@RequestBody CodeRequest request ){	
		ComplexityResponse response = codeAnalysisService.analyze(request.getCode());
		return ResponseEntity.ok(response);
	}
}




