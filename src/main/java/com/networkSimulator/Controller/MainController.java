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

// Main Controller to get the input from the user on the endpoint /ajiranet/process
@RestController
public class MainController {

	
	@Autowired
	public MainService mainService;

	@PostMapping(value  = "/ajiranet/process",produces=MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseDTO > getValues(@RequestBody String bodymsg)
	{
		ResponseDTO resp;
		try {	
			resp = mainService.separator(bodymsg);
			if(resp.getHttpResponse()==200)
				return ResponseEntity.ok()
						.header("Content-Type", "Application/JSON")
						.body(resp);
			return ResponseEntity.badRequest()
						.header("Content-Type", "Application/JSON")
						.body(resp);
		}
		catch(Exception E)
		{
			resp = new ResponseDTO();
			resp.setMesg("Invalid Command");
			E.printStackTrace();
			return ResponseEntity.badRequest().body(resp);
		}
	}
}
