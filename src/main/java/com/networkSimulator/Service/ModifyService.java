package com.networkSimulator.Service;

import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.networkSimulator.DTO.ResponseDTO;
import com.networkSimulator.Store.NetworkStore;
import com.networkSimulator.util.IntegerUtil;

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
			resp.setMesg("Invalid Command 1");
		}
		else
		{
			String[] commandSplit = commandText.split("\\s+");
			String[] deviceName = commandSplit[1].split("/");
			if(deviceName.length!=4 || !deviceName[1].equals("devices") || !deviceName[3].equals("strength"))
			{
				resp.setMesg("Invalid Command 2");
				return resp;
			}
			int index = netstore.getIndexMapper().size();
			String device = deviceName[2];
			JSONObject check  = new JSONObject(commandText.substring(commandText.indexOf("{")));
			if(!IntegerUtil.isInteger(check.get("value").toString()))
				resp.setMesg("value should be an integer");
			else
			{
				if(!netstore.getIndexMapper().containsKey(device))
					resp.setMesg("Device Not found");
				else
				{
					Map<Integer,Integer> temp  = netstore.getStrength();
					temp.put(netstore.getIndexMapper().get(device), Integer.parseUnsignedInt(check.get("value").toString()));
					netstore.setStrength(temp);
					resp.setMesg("Successfully defined strength");
				}
			}
		}
		return resp;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			resp.setMesg("Invalid Command exc");
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
	public static void main(String[] args)
	{
		ModifyService c= new ModifyService();
		String s = "MODIFY /devices/A1/strength\r\n"
				+ "content-type : application/json\r\n"
				+ "{\"value\": somevalues}";
		System.out.println(c.executeModify(s).getMesg());
		
	}
}
