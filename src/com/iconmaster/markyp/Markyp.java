package com.iconmaster.markyp;

import com.iconmaster.markyp.compile.Converter;
import com.iconmaster.markyp.compile.Formatter;
import com.iconmaster.markyp.compile.Formatter.Output;
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
		
		Tag tag = Parser.parse("\\tabsize{4}\\tab{}");
		Formatter f = new Formatter();
		Output out = f.format(tag);
		Converter c = new Converter();
		System.out.println(c.toJSON(out));
	}
}