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
		String command2 = "CREATE /devices\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"type\" : \"COMPUTER\", \"name\" : \"A3\"}";
		String command3 = "CREATE /connections\r\n"
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
		String command7 = "CREATE /devices\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"type\" : \"COMPUTER\", \"name\" : \"A6\"}";
		String command8 = "CREATE /devices\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"type\" : \"REPEATER\", \"name\" : \"R1\"}";
		String command9 = "MODIFY /devices/A1/strength\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"value\": 2}";
		String command10 = "CREATE /connections\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"source\" : \"A5\", \"targets\" : [\"A4\"]}";
		String command11 = "CREATE /connections\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"source\" : \"R1\", \"targets\" : [\"A2\"]}";
		String command12 = "CREATE /connections\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"source\" : \"R1\", \"targets\" : [\"A5\"]}";
		String command13 = "CREATE /connections\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"source\" : \"A8\", \"targets\" : [\"A1\"]}";
		String command14 = "CREATE /connections\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"source\" : \"A2\", \"targets\" : [\"A4\"]}";
		String command15="FETCH /info-routes?from=A1&to=A5";
		String command16="FETCH /devices";
		MainService f = new MainService();
		System.out.println(f.separator(command).getMesg());
		f.separator(command1).getMesg();
		f.separator(command2).getMesg();
		f.separator(command3).getMesg();
		f.separator(command4).getMesg();
		f.separator(command5).getMesg();
		f.separator(command6).getMesg();
		f.separator(command7).getMesg();
		f.separator(command8).getMesg();
		f.separator(command9).getMesg();
		f.separator(command10).getMesg();
		f.separator(command11).getMesg();
		f.separator(command12).getMesg();
		f.separator(command13).getMesg();
		f.separator(command14).getMesg();
		System.out.println(f.separator(command15).getMesg());
		System.out.println(f.separator(command16).getMesg());

//		String text = "FETCH /info-routes?from=A2&to=A4";
//		System.out.println(f.separator(text));
	}
}
