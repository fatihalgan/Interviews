package payon.hr;

import java.util.ArrayList;
import java.util.List;

public class Anagrams {
	
	public static List<String> findAnagrams(String word) {
		if(word == null) throw new IllegalArgumentException("findAnagrams: Parameter cannot have null value");
		if(!word.matches("[a-zA-Z]*")) throw new IllegalArgumentException("findAnagrams: Parameter must be a word"); 
		return generateAnagrams(new ArrayList<String>(), "", word);
	}
	
	private static List<String> generateAnagrams(List<String> anagrams, String prefix, String word) {
		 if(word.length() <= 1) {
			 anagrams.add(prefix + word);
		 } else {
			 for(int i = 0; i < word.length(); i++) {
				 String cur = word.substring(i, i + 1);
				 String before = word.substring(0, i); 
		         String after = word.substring(i + 1); 
		         generateAnagrams(anagrams, prefix + cur, before + after);
			 }
		 }
		 return anagrams;
	 }

	public static void main(String[] args) {
		String word = args[0];
		Anagrams anag = new Anagrams();
		List<String> solution = anag.findAnagrams(word);
		solution.forEach(System.out::println);
	}
}
