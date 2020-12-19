package com.networkSimulator.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.networkSimulator.DTO.ResponseDTO;

@Service
public class MainService {
	
	@Autowired
	CreationService create ;
	
	@Autowired
	FetchService fetch ;
	
	@Autowired
	ModifyService modify;
	
	public ResponseDTO separator(String commandText) {
		ResponseDTO resp ;
		try {
			String[] splited = commandText.split("\\s+");
			String type = splited[0];
			switch(type) {
			case "CREATE": System.out.println("inside Create");return create.executeCreate(commandText);
			case "MODIFY": System.out.println("inside Modify");return fetch.executeFetch(commandText);
			case "FETCH": System.out.println("inside Fetch");return fetch.executeFetch(commandText);
			default: System.out.println("inside Default");break;
			}
		resp = new ResponseDTO();
		resp.setMesg("Invalid Command");
		return resp;
		}
		catch(Exception E)
		{
			E.printStackTrace();
			resp = new ResponseDTO();
			resp.setMesg("Invalid Command");
			return resp;
		}
		
	}
	public static void main(String[] args)
	{
		String command = "CREATE /devices\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"type\" : \"COMPUTER\", \"name\" : \"A1\"}";
		String command1 = "CREATE /devices\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"type\" : \"COMPUTER\", \"name\" : \"A2\"}";
		String command3 = "CREATE /devices\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"type\" : \"COMPUTER\", \"name\" : \"A3\"}";
		String command2 = "CREATE /connections\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"source\" : \"A1\", \"targets\" : [\"A2\",\"A3\"]}";
		String command5 = "CREATE /connections\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"source\" : \"A3\", \"targets\" : [\"A4\"]}";
		String command4 = "CREATE /devices\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"type\" : \"COMPUTER\", \"name\" : \"A4\"}";
		String command6 = "CREATE /devices\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"type\" : \"COMPUTER\", \"name\" : \"A5\"}";
		MainService f = new MainService();
		f.separator(command).getMesg();
		f.separator(command1).getMesg();
		f.separator(command3).getMesg();
		f.separator(command2).getMesg();
		f.separator(command4).getMesg();
		f.separator(command5).getMesg();
		f.separator(command6).getMesg();
		String text = "FETCH /info-routes?from=A2&to=A4";
		System.out.println(f.separator(text));
	}
}
