package com.t360.phonebook;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.t360.phonebook.Dictionary;

public class DictionaryTest {

	String[] words = {"an", "blau", "Bo\"", "Boot", "bo\"s", "da", "Fee", "fern", "Fest", "fort", "je", "jemand", "mir", "Mix", "Mixer", "Name", "neu", "o\"d", "Ort", "so", "Tor", "Torf", "Wasser"};
	Dictionary dictionary = null;
	
	@Before
	public void setUp() throws Exception {
		dictionary = new Dictionary();
		for(String s : words) {
			dictionary.addWord(s);
		}
	}
	
	@After
	public void tearDown() throws Exception {
		dictionary.clear();
	}
	
	@Test
	public void testSearchCharsInEncodings() {
		char c = '7';
		char[] returnVal = dictionary.searchCharsInEncodings(c);
		assertEquals(returnVal[0], 'b');
		assertEquals(returnVal[1], 'k');
		assertEquals(returnVal[2], 'u');
	}
	
	/**
	@Test
	public void testSearchBeginningWith() {
		List<String> result = dictionary.searchBeginningWith('b');
		assertTrue(result.size() == 4);
	}
	**/
	
	/**
	@Test
	public void testSearchBeginningWithMultiple() {
		List<String> result = dictionary.searchBeginningWith(new char[] {'b', 'd'});
		assertTrue(result.size() == 5);
	}
	**/
	
	/**
	@Test
	public void testSearchIndexWithChar() {
		List<String> result = dictionary.searchBeginningWith('b');
		result = dictionary.searchIndexWithChar('l', result, 1, 4);
		assertEquals(result.get(0), "blau");
		result = dictionary.searchBeginningWith('b');
		result = dictionary.searchIndexWithChar('s', result, 2, 3);
		assertEquals(result.get(0), "bo\"s");
	}
	**/
	
	/**
	@Test
	public void testSearchIndexWithCharMultiple() {
		List<String> result = dictionary.searchBeginningWith('b');
		result = dictionary.searchIndexWithChar(new char[] {'l', 'o'}, result, 1, 4);
		assertEquals(result.size(), 2);
	}
	**/
	
	@Test
	public void testEmpty() {
		List<String> result = dictionary.searchNumberEncodings("");
		assertEquals(result.size(), 0);
	}
	
	@Test
	public void test562() {
		List<String> result = dictionary.searchNumberEncodings("562");
		assertEquals(result.size(), 2);
		assertEquals(result.get(0), "mir");
		assertEquals(result.get(1), "Mix");
	}
	
	@Test
	public void test83() {
		List<String> result = dictionary.searchNumberEncodings("83");
		assertEquals(result.size(), 1);
		assertEquals(result.get(0), "o\"d");
	}

}
