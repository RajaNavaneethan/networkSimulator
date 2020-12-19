package com.networkSimulator.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.springframework.stereotype.Service;

@Service	
public class NetworkStore {
	private Vector<Vector<Integer>> vec = new Vector<Vector<Integer>>();
	private static NetworkStore instance;
	private Map<String,String> deviceTypeMapper = new HashMap<>();
	private Map<String,Integer> indexMapper  = new HashMap<String,Integer>();
	public Map<String, Integer> getIndexMapper() {
		return indexMapper;
	}
	public void setIndexMapper(Map<String, Integer> indexMapper) {
		this.indexMapper = indexMapper;
	}
	
	public Vector<Vector<Integer>> getVec() {
		return vec;
	}
	public Map<String, String> getDeviceTypeMapper() {
		return deviceTypeMapper;
	}
	public void setDeviceTypeMapper(Map<String, String> deviceTypeMapper) {
		this.deviceTypeMapper = deviceTypeMapper;
	}
	public void setVec(Vector<Vector<Integer>> vec) {
		this.vec = vec;
	}
	private NetworkStore() {
		System.out.println("Insidie the constructor");
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
		System.out.println(v.size());
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
	public static NetworkStore getInstance() {
		if(instance == null) { 
			instance = new NetworkStore();
		}
		return instance;
	}
	
	
}
