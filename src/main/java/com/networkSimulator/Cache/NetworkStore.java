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
		System.out.println("Insidie the constructor");
	}
	
	public ArrayList<Integer> DFSUtil(int source, int destn, HashSet<Integer> visited,ArrayList<Integer> temp,int token)
	{
		if(token ==0)
			return null;
		if(source == destn)
			return temp;
		Vector<Integer> val = vec.get(source);
		int count = Integer.MAX_VALUE;
		ArrayList<Integer> result = null;
		for(int i=0;i<val.size();i++)
		{
			if(!visited.contains(i) && val.get(i)!=0)
			{
				visited.add(i);
				int index = temp.size();
				temp.add(i);
				int newToken = deviceTypeMapper.get(i).equalsIgnoreCase("Computer") ? token-1: (token-1)*2;
				ArrayList<Integer> t = DFSUtil(i,destn,visited,temp,newToken);
				if(t!=null && t.size()<count)
					result = t;
				else
					temp.remove(index);
				visited.remove(i);
			}
		}
		if(result==null)
			return null;
		else
			return result;
	}
	
//	public void DFS(int source,int destn,HashSet<Integer> visited,ArrayList<Integer> temp)
//	{
//		
//	}
	public String calculateRoute(String source,String destination)
	{
		if(!deviceTypeMapper.get(indexMapper.get(source)).equalsIgnoreCase("COMPUTER") || !deviceTypeMapper.get(indexMapper.get(destination)).equalsIgnoreCase("COMPUTER"))
			return "Route cannot be calculated with repeater";
		int sourceIndex = indexMapper.get(source);
		int destnIndex = indexMapper.get(destination);
		HashSet<Integer> s = new HashSet<>();
		s.add(sourceIndex);
		System.out.println("Printing the routes "+Strength.get(sourceIndex));
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(sourceIndex);
		ArrayList<Integer> arr =  DFSUtil(sourceIndex,destnIndex,s,temp,Strength.get(sourceIndex));
		for(Integer a : arr)
			System.out.println(a);
		return "null";
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
//		System.out.println(v.size());
		netstore.setVec(v);
		for(int i=0;i<v.size();i++)
		{
			Vector<Integer> temp = v.get(i);
			for(int j=0;j<temp.size();j++)
			{
				System.out.print(temp.get(j)+"\t");
			}
			System.out.println("\n");
		}
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
	
	
}
