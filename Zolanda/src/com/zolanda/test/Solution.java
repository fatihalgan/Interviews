package com.zolanda.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {

	static String reverse(String str) {
		if(str.length() < 2) return str;
		String subs = str.substring(str.length() - 1);
		String remaining = str.substring(0, str.length() - 1);
		return subs.concat(reverse(remaining));
	}
	
	static String cross(int h) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < h; i++) {
            for(int k = 0; k < h; k++) {
                if(k == i || k == (h - (i + 1))) builder.append('x');
                else builder.append(' ');
            }
            builder.append("\n");
        }
        return builder.toString();

    }
	
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String res;
        int _h;
        _h = in.nextInt();
        
        res = cross(_h);
        System.out.println(res);
        
    }
}
