package com.iconmaster.markyp;

import com.iconmaster.srcml.parse.Parser;
import com.iconmaster.srcml.parse.Tag;

/**
 *
 * @author iconmaster
 */
public class Markyp {
	public static void main(String[] args) {
		Tag tag = Parser.parse("This is an \b{example}.");
	}
}
