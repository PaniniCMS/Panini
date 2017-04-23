package com.paninicms.utils;

public class PaniniUtils {
	public static boolean is(String[] args, int idx, String val) {
		return is(args, idx, val, false);
	}
	
	public static boolean is(String[] args, int idx, String val, boolean ignoreCase) {
		try {
			return (args[idx].toLowerCase().equals(val.toLowerCase()));
		} catch (Exception e) {
			return false;
		}
	}
}
