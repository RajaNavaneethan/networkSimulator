package com.networkSimulator.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.networkSimulator.DTO.ResponseDTO;
import com.networkSimulator.Service.MainService;

@RestController
public class MainController {

	
	@Autowired
	public MainService mainService;

	@PostMapping(value  = "/ajiranet/process",produces=MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseDTO > getValues(@RequestBody String bodymsg)
	{
		try {	
			ResponseDTO resp = mainService.separator(bodymsg);			
			return ResponseEntity.badRequest()
			        .header("Content-Type", "Application/JSON")
			        .body(resp);	
		}
		catch(Exception E)
		{
			System.out.println("Printing an error");
			E.printStackTrace();
			return ResponseEntity.badRequest().body(null);
		}
	}
}
