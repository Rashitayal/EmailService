package com.api.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.email.entities.Email;
import com.api.email.service.EmailService;

@RestController
@CrossOrigin
public class EmailController {
	
	@Autowired
	private EmailService emailService;

	@PostMapping("/email/send")
	public ResponseEntity<?> sendEmail(@RequestBody Email email) {
		System.out.println(email);
		boolean status = emailService.sendEmail(email);
		if(status)
			return ResponseEntity.ok("email sent successfully");
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("email not sent");
	}
	
	
}
