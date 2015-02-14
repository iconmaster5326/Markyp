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
		
		Tag tag = Parser.parse("line 1\\pbr{}line 2\\br{}line 3");
		Formatter f = new Formatter();
		Output out = f.format(tag);
		Converter c = new Converter();
		System.out.println("/give @a minecraft:written_book 1 0 {title:\"\",author:\"\",pages:"+c.toJSON(out)+"}");
	}
}