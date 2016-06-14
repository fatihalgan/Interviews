package com.t360.phonebook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Encoding {

	private String prefixNumber;
	private String posfixNumber;
	private List<Encoding> children = new ArrayList<Encoding>();
	private Encoding parent = null;
	boolean foundPosfixMatch = false;
	private Dictionary dictionary;
	private EncodingCache cache = null;
	
	public Encoding(Dictionary dictionary, String phoneNumber) {
		this(dictionary, null, phoneNumber, new EncodingCache());
	}
	
	private Encoding(Dictionary dictionary, String prefixNumber, String posfixNumber, EncodingCache cache) {
		this.dictionary = dictionary;
		this.prefixNumber = prefixNumber;
		this.posfixNumber = posfixNumber;
		this.cache = cache;
	}
	
	private List<Encoding> getChildren() {
		return children;
	}

	private Encoding getParent() {
		return parent;
	}
	
	private void setParent(Encoding parent) {
		this.parent = parent;
	}
	
	private void createChildren() {
		String tmp = stripNonDigits(posfixNumber);
		if(tmp.length() < 3) return;
		for(int i = 1; i < tmp.length(); i++) {
			String posfix = tmp.substring(i, tmp.length());
			String prefix = tmp.substring(0, i);
			Encoding e = new Encoding(dictionary, prefix, posfix, cache);
			children.add(e);
			e.setParent(this);
		}
		Collections.reverse(children);
	}
	
	public String getPhoneNumber() {
		if(prefixNumber != null) return prefixNumber + posfixNumber;
		else return posfixNumber;
	}
	
	public List<String> encodeMatchings() {
		List<String> prefixTmp = new ArrayList<String>(0), posfixTmp = new ArrayList<String>(0);
		if(posfixNumber.length() > 1) {
			posfixTmp = cache.get(posfixNumber);
			if(posfixTmp.size() == 0) {
				posfixTmp = dictionary.searchNumberEncodings(stripNonDigits(posfixNumber));
				cache.set(posfixNumber, posfixTmp);
			}
		}
		if(posfixTmp.size() > 0) {
			foundPosfixMatch = true;
		}
		if(prefixNumber != null) {
			if(prefixNumber.length() > 1) {
				prefixTmp = cache.get(prefixNumber);
				if(prefixTmp.size() == 0) {
					prefixTmp = dictionary.searchNumberEncodings(stripNonDigits(prefixNumber));
					cache.set(prefixNumber, prefixTmp);
				}
			}
		}
		if(prefixNumber == null ) encodeChildren(posfixTmp);
		else if(prefixTmp.size() > 0) encodeChildren(posfixTmp);
		else if(prefixNumber.length() == 1) encodeChildren(posfixTmp);
		return cartesianMatchings(prefixNumber, prefixTmp, posfixNumber, posfixTmp);
	}
	
	private void encodeChildren(List<String> alreadyEncoded) {
		createChildren();
		List<String> temp = null;
		for(Encoding enc : children) {
			temp = enc.encodeMatchings();
			alreadyEncoded.addAll(temp);
		}
	}
	
	private List<String> cartesianMatchings(String prefixNo, List<String> prefixList, String posfixNo, List<String> posfixList) {
		//This is for the root element only which does not have any prefixNumber
		if(prefixNo == null) return posfixList;
		//If both prefix and postfix lists have elements, match(cartesian product) every prefix element with every postfix element
		if(posfixList.size() > 0 && prefixList.size() > 0) return cartesianLists(prefixList, posfixList);
		//If matchings found in prefixList but not found in postfixList then check if this node can be encoded
		//by itself using posfixNumber(length of posfixNumber must be 1). Then match(cartesian product) every prefix element with
		//posfix number
		if(posfixList.size() == 0 && posfixNumber.length() == 1 && prefixList.size() > 0) {
			posfixList.add(posfixNumber);
			foundPosfixMatch = true;
			return cartesianLists(prefixList, posfixList);
		} else if(posfixList.size() > 0 && prefixList.size() == 0 && prefixNumber.length() == 1) {//Do the reverse if matchins found in posfixList
			//but not in prefixList
			if(parent.foundPosfixMatch == false && !foundPosfixMatchingInSiblings()) prefixList.add(prefixNumber);
			return cartesianLists(prefixList, posfixList);
		} else if(prefixList.size() == 0 && prefixNumber.length() > 1) {
			foundPosfixMatch = false;
		}
		//if no matchings can be created, return an empty list
		return new ArrayList<String>(0);
	}
	
	private boolean foundPosfixMatchingInSiblings() {
		List<Encoding> siblings = parent.getChildren();
		for(int i = 0; i < siblings.size() - 1; i++) {
			if(siblings.get(i).foundPosfixMatch) return true;
		}
		return false;
	}
	
	private List<String> cartesianLists(List<String> prefixList, List<String> posfixList) {
		List<String> returnVal = new ArrayList<String>();
		for(String pre : prefixList) {
			for(String pos : posfixList) {
				if(pre.length() == 1 && Character.isDigit(pre.charAt(0)) && Character.isDigit(pos.charAt(0))) continue;
				returnVal.add(pre + " " + pos);				
			}
		}
		return returnVal;
	}
	
	private static String stripNonDigits(String number) {
		return number.replaceAll("[/-]", "");
	}
}
