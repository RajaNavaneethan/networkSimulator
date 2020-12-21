package com.networkSimulator.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.networkSimulator.DTO.ResponseDTO;

@Service
public class MainService {
	
	@Autowired
	CreationService create ;
	
	@Autowired
	FetchService fetch;
	
	@Autowired
	ModifyService modify;
	
	public ResponseDTO separator(String commandText) {
		ResponseDTO resp ;
		try {
			String[] splited = commandText.split("\\s+");
			String type = splited[0];
			switch(type) {
			case "CREATE": System.out.println("inside Create");return create.executeCreate(commandText);
			case "MODIFY": System.out.println("inside Modify");return modify.executeModify(commandText);
			case "FETCH": System.out.println("inside Fetch");return fetch.executeFetch(commandText);
			default: System.out.println("inside Default");break;
			}
		resp = new ResponseDTO();
		resp.setHttpResponse(400);
		resp.setMesg("Invalid Command");
		return resp;
		}
		catch(Exception E)
		{
			E.printStackTrace();
			resp = new ResponseDTO();
			resp.setHttpResponse(400);
			resp.setMesg("Invalid Command");
			return resp;
		}
		
	}
}
