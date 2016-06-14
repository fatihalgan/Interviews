package com.t360.phonebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Dictionary {

	private Map<Character, List<String>> dictionary = new TreeMap<Character, List<String>>();
	static final char[][] encodings = {{'e'}, {'j', 'n', 'q'}, {'r', 'w', 'x'}, {'d', 's', 'y'}, {'f', 't'}, {'a', 'm'}, {'c', 'i', 'v'}, {'b', 'k', 'u'}, {'l', 'o', 'p'}, {'g', 'h', 'z'}};
	
	public Dictionary() {
		super();
	}
	
	public char[] searchCharsInEncodings(char c) {
		int number = Integer.parseInt(String.valueOf(c));
		return encodings[number];
	}
	
	public void addWord(String word) {
		char begin = word.toLowerCase().charAt(0);
		List<String> sublist = dictionary.get(Character.valueOf(begin));
		if(sublist == null) {
			sublist = new ArrayList<String>();
			dictionary.put(begin, sublist);			
		}
		sublist.add(word);
	}
	
	private void addWord(Map<Character, List<String>> subdictionary, int index, String word) {
		String temp = stripUmlaughtChars(word);
		if(temp.length() < index + 1) return;
		char begin = temp.toLowerCase().charAt(index);
		List<String> sublist = subdictionary.get(Character.valueOf(begin));
		if(sublist == null) {
			sublist = new ArrayList<String>();
			subdictionary.put(begin, sublist);			
		}
		sublist.add(word);
	}
	
	public void clear() {
		dictionary.clear();
	}
	
	private void addBeginningWith(char c, Map<Character, List<String>> subDictionary) {
		List<String> lst = dictionary.get(Character.toLowerCase(c));
		if(lst == null) return;
		subDictionary.put(Character.valueOf(c), lst);
		return;
	}
	
	private Map<Character, List<String>> addBeginningWith(char[] c) {
		Map<Character, List<String>> subDictionary = new HashMap<Character, List<String>>();
		for(char ch : c) {
			addBeginningWith(ch, subDictionary);
		}
		return subDictionary;
	}
	
	private void addIndexWithChar(char c, int index, int totalLength, Map<Character, List<String>> subDictionary, Map<Character, List<String>> returnVal) {
		List<String> subList = subDictionary.get(Character.valueOf(c)); 
		if(subList == null) return;
		for(String word : subList) {
			String stripped = stripUmlaughtChars(word);
			if(stripped.length() < index + 1) continue;
			if(stripped.charAt(index) == c && stripped.length() == totalLength) addWord(returnVal, index, word);
		}
		return;
	}
	
	private Map<Character, List<String>> searchIndexWithChar(char[] c, Map<Character, List<String>> subDictionary, int index, int totalLength) {
		Map<Character, List<String>> returnVal = new HashMap<Character, List<String>>();
		for(char ch : c) {
			addIndexWithChar(ch, index, totalLength, subDictionary, returnVal);
		}
		return returnVal;
	}
	
	private Map<Character, List<String>> reconfigureSubDictionary(Map<Character, List<String>> subDictionary, int index) {
		Map<Character, List<String>> returnValue = new HashMap<Character, List<String>>();
		for(Map.Entry<Character, List<String>> mapElement : subDictionary.entrySet()) {
			for(String s: mapElement.getValue()) {
				addWord(returnValue, index, s);
			}
		}
		return returnValue;
	}
	
	public List<String> searchNumberEncodings(String number) {
		List<String> returnVal = new ArrayList<String>();
		if(number == null || number.length() == 0) return returnVal;
		char[] nums = number.toCharArray();
		char[] possibleChars = searchCharsInEncodings(nums[0]);
		Map<Character, List<String>> subDictionary = addBeginningWith(possibleChars);
		for(int i = 1; i < nums.length; i++) {
			subDictionary = reconfigureSubDictionary(subDictionary, i);
			possibleChars = searchCharsInEncodings(nums[i]);
			subDictionary = searchIndexWithChar(possibleChars, subDictionary, i, number.length());			
		}
		for(Map.Entry<Character, List<String>> entries : subDictionary.entrySet()) {
			returnVal.addAll(entries.getValue());
		}
		return returnVal;
	}
	
	
	
	private static String stripUmlaughtChars(String word) {
		return word.replaceAll("[\"-]", "");
	}
	
	
}
