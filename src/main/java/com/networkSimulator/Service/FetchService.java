package com.networkSimulator.Service;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.networkSimulator.Cache.NetworkStore;
import com.networkSimulator.DTO.ResponseDTO;

@Service
public class FetchService {
	NetworkStore netstore = NetworkStore.getInstance();
	enum TYPE{
		devices,
		info,
		neither
	}
	public  ResponseDTO executeFetch(String commandText)
	{
		ResponseDTO resp = new ResponseDTO();
		try {
		TYPE check = checkFetchCommand(commandText);
		if(check==TYPE.neither)
		{
			resp.setHttpResponse(400);
			resp.setMesg("Invalid Command");
		}
		else if(check==TYPE.devices)
		{
			JSONArray jarr = new JSONArray();
			Map<String,Integer> devices = netstore.getIndexMapper();
			Map<Integer,String> types = netstore.getDeviceTypeMapper();
			 for (Map.Entry<String,Integer> entry : devices.entrySet())  
			 {
				JSONObject json = new JSONObject();
				 json.put("name", entry.getKey());
				 json.put("type", types.get(entry.getValue()));
				 jarr.put(json);
			 }
			 resp.setHttpResponse(200);
			 resp.setMesg(jarr.toString());
		}
		else
		{
			String com = commandText.split("/")[1];
			String[] routes = com.split("\\?");
			String[] srcDest = routes[1].split("&");
			String[] from = srcDest[0].split("=");
			String[] to = srcDest[1].split("=");
			resp.setHttpResponse(200);
			resp.setMesg(netstore.calculateRoute(from[1],to[1]));
		}
		return resp;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			resp.setHttpResponse(400);
			resp.setMesg("Invalid Command");
			return resp;
		}
	}
	public TYPE checkFetchCommand(String text) {
		//splitting
		
		String[] commandSplit = text.split("\\s+");
		try {
		if(commandSplit.length > 2)
			return TYPE.neither;
			
		for(int i=0;i<commandSplit.length;i++)
		{
			switch(i)
			{
			case 0:if(!commandSplit[i].equalsIgnoreCase("FETCH")) return TYPE.neither; break;
			case 1:if(commandSplit[i].charAt(0)!='/')return TYPE.neither; break;
			}
		}
		String[] com = commandSplit[1].split("/");
		if(com.length == 2 && com[1].equals("devices"))
			return TYPE.devices;
		else if(com.length==2)
		{
			//info-routes?
			String info = com[1];
			String[] routes = info.split("\\?");
			if(routes.length!=2 || !routes[0].equals("info-routes"))
				return TYPE.neither;
			else  
			{
				String[] srcDest = routes[1].split("&");
				if(srcDest.length != 2)
					return TYPE.neither;
				String[] from = srcDest[0].split("=");
				String[] to = srcDest[1].split("=");
				if(from.length != 2 || to.length!=2)
					return TYPE.neither;
				if(!from[0].equals("from") || !to[0].equals("to"))
					return TYPE.neither;
				return TYPE.info;
			}
		}
		else
			return TYPE.neither;
		}
		catch(Exception E)
		{
			E.printStackTrace();
			return TYPE.neither;
		}
	}
	
}
