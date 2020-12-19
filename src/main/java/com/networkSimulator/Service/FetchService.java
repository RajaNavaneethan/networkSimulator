package com.networkSimulator.Service;

import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.networkSimulator.Cache.NetworkStore;
import com.networkSimulator.DTO.ResponseDTO;

@Service
public class FetchService {
	NetworkStore netstore = NetworkStore.getInstance();
	
	public  ResponseDTO executeFetch(String commandText)
	{
		ResponseDTO resp = new ResponseDTO();
		try {
		String json = commandText.substring(commandText.indexOf("{"));
		JSONObject respMesg  = new JSONObject(json);
		if(!checkCreateCommand(commandText))
		{
			System.out.println("Invalid");
			resp.setMesg("Invalid Command");
		}
		else if(netstore.getIndexMapper().containsKey(respMesg.get("name")))
		{
			System.out.println("Index Mappepr value"+netstore.getIndexMapper().size());
			resp.setMesg("Device "+respMesg.get("name")+" already exists");
		}
		else
		{
			int index = netstore.getIndexMapper().size();
			Map<String,Integer> temp = netstore.getIndexMapper();
			temp.put(respMesg.getString("name"), index);
			netstore.setIndexMapper(temp);
			netstore.add();
			resp.setMesg("Succesfully added "+respMesg.get("name"));
		}
		return resp;
		}
		catch(Exception e)
		{
			resp.setMesg("Invalid Command");
			return resp;
		}
	}
	public boolean checkCreateCommand(String text) {
		//splitting
		String[] commandSplit = text.split("\\s+");
		for(int i=0;i<commandSplit.length;i++)
		{
			switch(i)
			{
			case 0:if(!commandSplit[i].equalsIgnoreCase("CREATE")) return false; break;
			case 1:if(commandSplit[i].charAt(0)!='/')return false; break;
			case 2: if(!commandSplit[i].equalsIgnoreCase("content-type")) return false; break;
			case 3: if(!commandSplit[i].equals(":")) return false; break;
			case 4: if(!commandSplit[i].equalsIgnoreCase("application/json")) return false; break;
			case 5:if(commandSplit[i].charAt(0)!='{')return false; break;
			}
		}
		try {
		JSONObject check  = new JSONObject(text.substring(text.indexOf("{")));
		Iterator<String> keys = check.keys();
		int keyslen = 0 ;
		while(keys.hasNext())
		{
			keyslen++;
			String key = keys.next();
			if(!(key.equalsIgnoreCase("type") || key.equalsIgnoreCase("name")))
			{
				return false;
			}
		}
		if(keyslen>2)
			return false;
		return true;
		}
		catch(Exception E)
		{
			E.printStackTrace();
			return false;
		}
	}
}
