package com.t360.phonebook;

import java.awt.font.NumericShaper;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TelephoneBook {
	
	private Dictionary dictionary = null;
	
	public void encodePhoneNumber(String phoneNumber, PrintWriter out) {
		System.out.print("Encoding telehpone number " + phoneNumber + " ------ ");
		Encoding encoding = new Encoding(dictionary, phoneNumber);
		List<String> possibleEncodings = null;
		try {
			possibleEncodings = encoding.encodeMatchings();
			for(String enc : possibleEncodings) {
				out.write(encoding.getPhoneNumber() + ": " + enc + "\n");
			}
			System.out.println("Found " + possibleEncodings.size());
		} catch(Throwable t) {
			System.out.println("Error encoding phone number..");
		}
		encoding = null;
		possibleEncodings = null;
	}
	
	private void loadDictionary() {
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
	
	private void processTelephoneNumbers() {
		FileInputStream telephoneNumberStream = null;
		PrintWriter out = null;
		Scanner sc = null;
		try {
			try {
				telephoneNumberStream = new FileInputStream("./target/classes/input.txt");
				out = new PrintWriter(new FileWriter("./target/classes/output.txt"));
				sc = new Scanner(telephoneNumberStream, "ASCII");
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					try {
						encodePhoneNumber(line, out);
						out.flush();
					} catch(Throwable t) {
						System.err.println("Error while writing encoded telephone number to output file: " + t.getMessage());
						t.printStackTrace();
					}
				}
				//Scanner suppresses exceptions
				if (sc.ioException() != null) {
					throw sc.ioException();
				}
			} finally {
				if (telephoneNumberStream != null) {
					telephoneNumberStream.close();
				}
				if (sc != null) {
					sc.close();
				}
				if(out != null) {
					out.close();
				}
			}
		} catch(FileNotFoundException fe) {
			System.err.println("Could not locate input file input.txt..");
		} catch(IOException ioe) {
			System.err.println("Error while processing input telephone number file: " + ioe.getMessage());
		}
	}
	
	public static void main(String[] args) {
		TelephoneBook telephoneBook = new TelephoneBook();
		telephoneBook.loadDictionary();
		telephoneBook.processTelephoneNumbers();
		
	}

}
