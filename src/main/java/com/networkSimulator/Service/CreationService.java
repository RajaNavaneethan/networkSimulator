package com.networkSimulator.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.networkSimulator.Cache.NetworkStore;
import com.networkSimulator.DTO.ResponseDTO;

@Service
public class CreationService {
	
	static NetworkStore netstore = NetworkStore.getInstance();
	
	enum TYPE{
		connect,
		create,
		neither
	}
		
	public  ResponseDTO executeCreate(String commandText)
	{
		ResponseDTO resp = new ResponseDTO();
		try {
		String json = commandText.substring(commandText.indexOf("{"));
		JSONObject respMesg  = new JSONObject(json);
		TYPE check = checkCreateCommand(commandText);
		if(check==TYPE.neither)
		{
			resp.setMesg("Invalid Command");
		}
		else if(check==TYPE.create) 
		{
			if(netstore.getIndexMapper().containsKey(respMesg.get("name")))
			{
				resp.setMesg("Device "+respMesg.get("name")+" already exists");
			}
			else
			{
				int index = netstore.getIndexMapper().size();
				Map<String,Integer> temp = netstore.getIndexMapper();
				Map<Integer,String> temp1 = netstore.getDeviceTypeMapper();
				Map<Integer,Integer> temp2 = netstore.getStrength();
				temp.put(respMesg.getString("name"), index);
				temp1.put(index, respMesg.getString("type"));
				temp2.put(index,5);
				netstore.setIndexMapper(temp);
				netstore.setDeviceTypeMapper(temp1);
				netstore.add();
				resp.setMesg("Succesfully added "+respMesg.get("name"));
			}
		}
		else
		{
			JSONArray jar = new JSONArray(respMesg.get("targets").toString());
			String source = respMesg.getString("source");
			if(!netstore.getIndexMapper().containsKey(source))
			{
				resp.setMesg("Node '"+source+"' not found");
				return resp;
			}

			Vector<Vector<Integer>> vec = netstore.getVec();
			int sourceIndex =netstore.getIndexMapper().get(source);
			for(int i=0;i<jar.length();i++)
			{
				if(!netstore.getIndexMapper().containsKey(jar.get(i)))
				{	
					resp.setMesg("Node '"+jar.get(i)+"' not found");
					return resp;
				}
				if(source.equals(jar.get(i)))
				{
					resp.setMesg("Cannot connect device to itself");
					return resp;
				}
				int targetIndex =netstore.getIndexMapper().get(jar.get(i));
				Vector<Integer> val = vec.get(sourceIndex);
				Vector<Integer> val1 = vec.get(targetIndex);
				if(val.get(targetIndex) != 0)
				{
					resp.setMesg("Devices are already connected");
					return resp;
				}
				else
				{
					val.set(targetIndex,5);
					val1.set(sourceIndex,5);
				}
				vec.set(sourceIndex, val);
				vec.set(targetIndex, val1);
				netstore.setVec(vec);
			}
		}
		return resp;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			resp.setMesg("Invalid Command");
			return resp;
		}
	}
	
	public TYPE checkCreateCommand(String text) {
		//splitting
		String[] commandSplit = text.split("\\s+");
		for(int i=0;i<commandSplit.length;i++)
		{
			switch(i)
			{
			case 0:if(!commandSplit[i].equalsIgnoreCase("CREATE")) return TYPE.neither; break;
			case 1:if(!(commandSplit[i].equalsIgnoreCase("/devices") || commandSplit[i].equalsIgnoreCase("/connections")))
					return TYPE.neither; break;
			case 2: if(!commandSplit[i].equalsIgnoreCase("content-type")) return TYPE.neither; break;
			case 3: if(!commandSplit[i].equals(":")) return TYPE.neither; break;
			case 4: if(!commandSplit[i].equalsIgnoreCase("application/json")) return TYPE.neither; break;
			case 5:if(commandSplit[i].charAt(0)!='{')return TYPE.neither; break;
			}
		}
		try {
		JSONObject input  = new JSONObject(text.substring(text.indexOf("{")));
		TYPE check = checkType(input);
		return check;
		}
		catch(Exception E)
		{
			E.printStackTrace();
			return TYPE.neither;
		}
	}
	
	public TYPE checkType(JSONObject input )
	{
		boolean isCreate = false;
		if(input.has("type")  && input.has("name"))
			isCreate = true;
		else if(input.has("source")  && input.has("targets"))
			isCreate = false;
		else
			return TYPE.neither;
		Iterator<String> keys = input.keys();
		int keyslen = 0 ;
		while(keys.hasNext())
		{
			keys.next();
			keyslen++;
		}
		if(keyslen>2)
			return TYPE.neither;
		return isCreate ? TYPE.create : TYPE.connect;
	}
}
