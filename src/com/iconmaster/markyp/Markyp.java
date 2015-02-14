package com.iconmaster.markyp;

import com.iconmaster.markyp.compile.JSONConverter;
import com.iconmaster.markyp.compile.TagHandler;
import com.iconmaster.srcml.parse.Parser;
import com.iconmaster.srcml.parse.Tag;

/**
 *
 * @author iconmaster
 */
public class Markyp {
	public static void main(String[] args) {
		TagHandler.init();
		
		Tag tag = Parser.parse("\\*{Hello, \\b{Markyp}.}");
		JSONConverter json = new JSONConverter();
		System.out.println(tag);
		System.out.println(json.convert(tag));
	}
}