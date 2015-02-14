package com.iconmaster.srcml.tokenize;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 *
 * @author iconmaster
 */
public class Tokenizer {
	public ArrayList<Token> tokens = new ArrayList<Token>();
	public String input;

	public Tokenizer(String input) {
		this.input = input;
	}
	
	public ArrayList<Token> tokenize() {
		while (!input.isEmpty()) {
			System.out.println(input);
			for (Token.Type type : Token.Type.values()) {
				Matcher m = type.matcher.matcher(input);
				if (m.find()) {
					String g = m.group();
					tokens.add(new Token(type, g));
					input = input.substring(g.length());
					
				}
			}
		}
		return tokens;
	}
}
