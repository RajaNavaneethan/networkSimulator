package com.networkSimulator.Cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.stereotype.Service;

@Service	
public class NetworkStore {
	private Vector<Vector<Integer>> vec = new Vector<Vector<Integer>>();
	private static NetworkStore instance;
	private Map<Integer,String> deviceTypeMapper = new HashMap<>();
	private Map<String,Integer> indexMapper  = new HashMap<String,Integer>();
	private Map<Integer,Integer> Strength = new HashMap<>();
	public Map<String, Integer> getIndexMapper() {
		return indexMapper;
	}
	public void setIndexMapper(Map<String, Integer> indexMapper) {
		this.indexMapper = indexMapper;
	}
	public Vector<Vector<Integer>> getVec() {
		return vec;
	}
	public Map<Integer, String> getDeviceTypeMapper() {
		return deviceTypeMapper;
	}
	public void setDeviceTypeMapper(Map<Integer, String> deviceTypeMapper) {
		this.deviceTypeMapper = deviceTypeMapper;
	}
	public void setVec(Vector<Vector<Integer>> vec) {
		this.vec = vec;
	}
	private NetworkStore() {
	}
	public Map<Integer, Integer> getStrength() {
		return Strength;
	}
	public void setStrength(Map<Integer, Integer> strength) {
		Strength = strength;
	}
	public static NetworkStore getInstance() {
		if(instance == null) { 
			instance = new NetworkStore();
		}
		return instance;
	}
	
	public ArrayList<Integer> DFSUtil(int source, int destn, HashSet<Integer> visited,ArrayList<Integer> temp,int token)
	{

		if(source == destn)
			return temp;
		if(token ==0)
			return null;
		Vector<Integer> val = vec.get(source);
		int count = Integer.MAX_VALUE;
		ArrayList<Integer> result = null;
		for(int i=0;i<val.size();i++)
		{
			if(!visited.contains(i) && val.get(i)!=0)
			{
				visited.add(i);
				int index = temp.size();
				//sending duplicate 
				ArrayList<Integer> temp1 = new ArrayList<Integer>(temp);
				temp1.add(i);
				/* as per example for the route from A1 to A5 the strength of the signal 
				 * is reduced at the destination server */
				int newToken = deviceTypeMapper.get(i).equalsIgnoreCase("Computer") ? token-1: (token)*2;
				ArrayList<Integer> t = DFSUtil(i,destn,visited,temp1,newToken);
				if(t!=null && t.size()<count)
				{
					result = t;
					count = t.size();
				}
				else
					temp1.remove(index);
				visited.remove(i);
			}
		}
		if(result==null)
			return null;
		else
			return result;
	}
	
	public String calculateRoute(String source,String destination)
	{
		if(!indexMapper.containsKey(source) || !indexMapper.containsKey(destination))
			return "Nodes do not exist";
		if(!deviceTypeMapper.get(indexMapper.get(source)).equalsIgnoreCase("COMPUTER") || !deviceTypeMapper.get(indexMapper.get(destination)).equalsIgnoreCase("COMPUTER"))
			return "Route cannot be calculated with repeater";
		int sourceIndex = indexMapper.get(source);
		int destnIndex = indexMapper.get(destination);
		if(sourceIndex == destnIndex)
			return "Route is"+sourceIndex+"->"+destnIndex;
		HashSet<Integer> s = new HashSet<>();
		s.add(sourceIndex);
		System.out.println("Printing the routes "+Strength.get(sourceIndex));
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(sourceIndex);
		ArrayList<Integer> arr =  DFSUtil(sourceIndex,destnIndex,s,temp,Strength.get(sourceIndex));
		if(arr==null)
			return "No Routes Found";
		StringBuffer res = new StringBuffer("Route is ");
		for(int i=0;i<arr.size();i++)
		{
			if(i==arr.size()-1)
				res.append(findName(arr.get(i)));
			else
			{
				res.append(findName(arr.get(i)));
				res.append("->");
			}
		}
		return res.toString();
	}
	
	public String findName(int index)
	{
		 for (Map.Entry<String,Integer> entry : indexMapper.entrySet())  
		 {
			 if(entry.getValue()==index)
				 return entry.getKey();
		 }
		 return "";
	}
	public  void add()
	{
		NetworkStore netstore = NetworkStore.getInstance();
		Vector<Vector<Integer>> v = netstore.getVec();
		Vector<Integer> val = new Vector<>();
		int size =v.size();
		for(int i=0;i<size;i++)
		{
			val.add(0);
		}
		v.add(val);
		for(int i=0;i<v.size();i++)
		{
			v.get(i).add(0);
		}
		netstore.setVec(v);
	}
	
	
}
