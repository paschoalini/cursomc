package com.paschoalini.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class URL {
	public static List<Long> decodeLongList(String s) {
		String[] vet = s.split(",");
		List<Long> list = new ArrayList<>();
		for (int i = 0; i < vet.length; i++) {
			list.add(Long.parseLong(vet[i]));
		}
		return list;
	}

	public static String decodeParam(String s) {
		try {
			return java.net.URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
