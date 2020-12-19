package com.networkSimulator.Service;

import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.networkSimulator.Cache.NetworkStore;
import com.networkSimulator.DTO.ResponseDTO;

@Service
public class ModifyService {
	NetworkStore netstore = NetworkStore.getInstance();
	public  ResponseDTO executeModify(String commandText)
	{
		ResponseDTO resp = new ResponseDTO();
		try {
		String json = commandText.substring(commandText.indexOf("{"));
		JSONObject respMesg  = new JSONObject(json);
		if(!checkModifyCommand(commandText))
		{
			resp.setMesg("Invalid Command");
		}
		else
		{
			String[] commandSplit = commandText.split("\\s+");
			String[] deviceName = commandSplit[1].split("/");
			if(deviceName.length!=3 || !deviceName[0].equals("devices") || !deviceName[2].equals("strength"))
			{
				resp.setMesg("Invalid Command");
				return resp;
			}
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
	public boolean checkModifyCommand(String text) {
		//splitting
		String[] commandSplit = text.split("\\s+");
		for(int i=0;i<commandSplit.length;i++)
		{
			switch(i)
			{
			case 0:if(!commandSplit[i].equalsIgnoreCase("MODIFY")) return false; break;
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
			if(!(key.equalsIgnoreCase("value")))
				return false;
		}
		if(keyslen>1)
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
