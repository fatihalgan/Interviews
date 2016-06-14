package com.t360.phonebook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

import static org.junit.Assert.*;

public class TelephoneBookTest {

	private Dictionary dictionary = null;
	
	@Before
	public void setUp() {
		BufferedReader reader = null;
		dictionary = new Dictionary();
		try {
			try {
				reader = new BufferedReader(new FileReader("./target/classes/dictionary.txt"));
				String line = null;
				System.out.println("Loading dictionary...");
				int numEntries = 0;
				while((line = reader.readLine()) != null) {
					dictionary.addWord(line);
					numEntries++;
				}
				System.out.println("Loaded dictionary successfully with " + numEntries + " words..");
			} finally {
				if(reader != null) reader.close();
			}
		} catch(FileNotFoundException fe) {
			System.err.println("Could not locate dictionary file dictionary.txt..");
		} catch(IOException ioe) {
			System.err.println("Error while loading dictionary file: " + ioe.getMessage());
		}
	}
	
	@After
	public void tearDown() {
		dictionary.clear();
	}
	
	@Test
	public void test90246201398625() {
		Encoding encoding = new Encoding(dictionary, "902462013-98625");
		List<String> matches = encoding.encodeMatchings();
		System.out.println("Found matches: " + matches.size());
		assertSame(0, matches.size());
	}
	
	@Test
	public void test2203234302015256933179964() {
		Encoding encoding = new Encoding(dictionary, "220323430201-5256-93/3-179964");
		List<String> matches = encoding.encodeMatchings();
		System.out.println("Found matches: " + matches.size());
		assertSame(0, matches.size());
	}
	
	@Test
	public void test08867590632769458140() {
		Encoding encoding = new Encoding(dictionary, "0886/7/-59063/276-9458140");
		List<String> matches = encoding.encodeMatchings();
		assertEquals(matches.size(), 6);
		assertListEquals(new String[] {"0 O\"l 6 um 9 Eid Wucht Mont 0", "0 O\"l 6 um 9 Eis Wucht Mont 0", "0 Po 6 um 9 Eid Wucht Mont 0", "0 Po 6 um 9 Eis Wucht Mont 0", "0 Opium 9 Eid Wucht Mont 0", "0 Opium 9 Eis Wucht Mont 0"}, matches);
	}
	
	private void assertListEquals(String[] expected, List<String> actual) {
		Set<String> expectedSet = new HashSet<String>(Arrays.asList(expected));
		Set<String> actualSet = new HashSet<String>(actual);
		assertEquals(expectedSet, actualSet);
	}
}
