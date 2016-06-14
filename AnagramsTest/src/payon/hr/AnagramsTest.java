package payon.hr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class AnagramsTest {

	List<String> anagrams = null;
		
	@Test(expected=IllegalArgumentException.class)
	public void testNull() {
		String nullString = null;
		anagrams = Anagrams.findAnagrams(nullString);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNonCharacter() {
		String bobChar = "fatih.";
		anagrams = Anagrams.findAnagrams(bobChar);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNonLetter() {
		String bobChar = "fatih1";
		anagrams = Anagrams.findAnagrams(bobChar);
	}

	@Test
	public void testEmpty() {
		String[] empty = new String[] {""};
		anagrams = Anagrams.findAnagrams("");
		assertSetEquals(anagrams, empty);
	}
	
	@Test
	public void testSingleCharacter() {
		String[] single = new String[] {"a"};
		anagrams = Anagrams.findAnagrams("a");
		assertSetEquals(anagrams, single);
	}
	
	@Test
	public void testLengthTwo() {
		String[] twoChars = new String[] {"fa", "af"};
		anagrams = Anagrams.findAnagrams("fa");
		assertSetEquals(anagrams, twoChars);
	}
	
	@Test
	public void testLengthThree() {
		String[] threeChars = new String[] {"atc", "tac", "cat", "act", "cta", "tca"};
		anagrams = Anagrams.findAnagrams("cat");
		assertSetEquals(anagrams, threeChars);
	}
	
	@Test
	public void testWrongAnagrams() {
		String[] wrongAnagrams = new String[] {"fa", "ft"};
		anagrams = Anagrams.findAnagrams("fa");
		assertSetNotSame(anagrams, wrongAnagrams);
	}
	
	@Test
	public void testCorrectCountOfAnagrams() {
		anagrams = Anagrams.findAnagrams("fatih");
		assertEquals(anagrams.size(), 120);
	}
	
	private void assertSetEquals(List<String> actual, String[] expected) {
		Set<String> setActual = new HashSet<String>(actual);
		Set<String> setExpected = new HashSet<String>(Arrays.asList(expected));
		assertEquals(setExpected, setActual);
	}
	
	private void assertSetNotSame(List<String> actual, String[] expected) {
		Set<String> setActual = new HashSet<String>(actual);
		Set<String> setExpected = new HashSet<String>(Arrays.asList(expected));
		assertNotEquals(setExpected, setActual);
	}

}
