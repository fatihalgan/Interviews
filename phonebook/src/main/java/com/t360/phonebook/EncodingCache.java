package com.t360.phonebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EncodingCache {

	private Map<String, List<String>> cache = new HashMap<String, List<String>>();
	
	public List<String> get(String number) {
		List<String> items = null;
		if((items = cache.get(number)) == null) {
			items = new ArrayList<String>(0);
		}
		return items;
	}
	
	public void set(String number, List<String> encodings) {
		if(cache.size() > 500000) cache.clear();
		cache.put(number, encodings);
	}
}
