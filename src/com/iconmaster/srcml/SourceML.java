package com.iconmaster.srcml;

import com.iconmaster.srcml.parse.Parser;
import com.iconmaster.srcml.parse.Tag;
import com.iconmaster.srcml.tokenize.Token;
import com.iconmaster.srcml.tokenize.Tokenizer;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class SourceML {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Tokenizer t = new Tokenizer("\\b*[arg=\\i{}]");
		ArrayList<Token> at = t.tokenize();
		System.out.println(at);
		Parser p = new Parser(at);
		ArrayList<Tag> tag = p.parse();
		System.out.println(tag);
	}
	
}
