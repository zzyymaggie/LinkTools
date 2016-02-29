/**
 * Copyright © zzyymaggie. All Rights Reserved.
 */
package xyz.zzyymaggie.link.tools.utils;

import java.util.ArrayList;
import java.util.List;

import jregex.Matcher;
import jregex.Pattern;
import jregex.Replacer;

public abstract class RegexUtil {

	private RegexUtil() { }
	
	public static String extract1(Pattern p, String src) {
		return extract(p, src, 1);
	}
	
	public static String extract(Pattern p, String src, int group) {
		Matcher m = p.matcher(src);
		
		String extractive = null;
		if(m.find()) {
			extractive = m.group(group);
		}
		
		return extractive;
	}
	
	public static List<String> extract(Pattern p, String src) {
		List<String> extractives = new ArrayList<String>();
		extract(p, src, extractives);
		return extractives;
	}
	
	public static List<String> extract(Pattern p, String src, List<String> extractives) {
		Matcher m = p.matcher(src);
		
		if(m.find()) {
			int count = m.groupCount();
			for (int i = 0; i < count; i++) {
				extractives.add(m.group(i));
			}
		}
		
		return extractives;
	}
	
	public static List<List<String>> extractGroups(Pattern p, String src) {
		Matcher m = p.matcher(src);
		
		List<List<String>> extractives = new ArrayList<List<String>>();
		
		while(m.find()) {
			int count = m.groupCount();
			
			List<String> extractive = new ArrayList<String>(count);
			extractives.add(extractive);
			
			for (int i = 0; i < count; i++) {
				extractive.add(m.group(i));
			}
			
		}
		
		return extractives;
	}
	
	public static boolean match(Pattern p, String src){
		Matcher m = p.matcher(src);
		return m.matches();
	}
	
	public static String erase(Pattern p, String src) {
		return replace(p, "", src);
	}
	
	public static String replace(Pattern p, String to, String src) {
		Replacer replacer = p.replacer(to);
		return replacer.replace(src);
	}
	
	public static boolean find(Pattern p, String src){
	    Matcher m = p.matcher(src);
	    return m.find();
	}
}
