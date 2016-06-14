package com.t360.phonebook;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.t360.phonebook.Dictionary;
import com.t360.phonebook.Encoding;

public class EncodingTest {

	String[] words = {"an", "blau", "Bo\"", "Boot", "bo\"s", "da", "Fee", "fern", "Fest", "fort", "je", "jemand", "mir", "Mix", "Mixer", "Name", "neu", "o\"d", "Ort", "so", "Tor", "Torf", "Wasser"};
	Dictionary dictionary; 
	
	@Before
	public void setUp() throws Exception {
		dictionary = new Dictionary();
		for(String s : words) dictionary.addWord(s);
	}
	
	@After
	public void tearDown() throws Exception {
		dictionary.clear();
	}
	
	
	@Test
	public void testNoneMatches() {
		Encoding encoding = new Encoding(dictionary, "112");
		List<String> matches = encoding.encodeMatchings();
		assertEquals(matches.size(), 0);
	}
	
	
	@Test
	public void test4824() {
		Encoding encoding = new Encoding(dictionary, "4824");
		List<String> matches = encoding.encodeMatchings();
		assertEquals(matches.size(), 3);
		assertListEquals(new String[] {"Torf", "fort", "Tor 4"},  matches);
	}
	
	
	@Test
	public void test562482() {
		Encoding encoding = new Encoding(dictionary, "5624-82");
		List<String> matches = encoding.encodeMatchings();
		assertEquals(matches.size(), 2);
		assertListEquals(new String[] {"mir Tor", "Mix Tor"}, matches);
	}
	
	
	
	@Test
	public void test107835() {
		Encoding encoding = new Encoding(dictionary, "10/783--5");
		List<String> matches = encoding.encodeMatchings();
		assertEquals(matches.size(), 3);
		assertListEquals(new String[] {"neu o\"d 5", "je bo\"s 5", "je Bo\" da"}, matches);
	}
	
	
	
	@Test
	public void test381482() {
		Encoding encoding = new Encoding(dictionary, "381482");
		List<String> matches = encoding.encodeMatchings();
		assertEquals(matches.size(), 1);
		assertListEquals(new String[] {"so 1 Tor"}, matches);
	}
	
	@Test
	public void test10789135() {
		Encoding encoding = new Encoding(dictionary, "1078-913-5");
		List<String> matches = encoding.encodeMatchings();
		assertEquals(matches.size(), 0);
	}
	
	
	@Test
	public void test04824() {
		Encoding encoding = new Encoding(dictionary, "04824");
		List<String> matches = encoding.encodeMatchings();
		assertEquals(matches.size(), 3);
		assertListEquals(new String[] {"0 Torf", "0 fort", "0 Tor 4"}, matches);
	}
	
	@Test
	public void test0() {
		Encoding encoding = new Encoding(dictionary, "/0");
		List<String> matches = encoding.encodeMatchings();
		assertEquals(matches.size(), 0);
		assertListEquals(new String[] {"0"}, matches);
	}
	
	private void assertListEquals(String[] expected, List<String> actual) {
		Set<String> expectedSet = new HashSet<String>(Arrays.asList(expected));
		Set<String> actualSet = new HashSet<String>(actual);
		assertEquals(expectedSet, actualSet);
	}
	
}
