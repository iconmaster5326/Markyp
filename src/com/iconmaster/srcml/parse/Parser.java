package com.iconmaster.srcml.parse;

import com.iconmaster.srcml.tokenize.Token;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class Parser {
	public ArrayList<Token> input;
	public Tag tag;
	
	public Parser(String name, ArrayList<Token> input) {
		this.input = input;
		tag = new Tag(name);
	}
	
	public Tag parse() {
		while (!input.isEmpty()) {
			step();
		}
		return tag;
	}
	
	public void step() {
		Token token = input.remove(0);
		
		System.out.println("PRESTEP "+input.size()+" "+token);
		System.out.println("\t"+tag);

		switch (token.type) {
			case SLASH:
				boolean named = false;
				String name = "";
				loop: while (!input.isEmpty()) {
					Token token2 = input.remove(0);

					switch (token2.type) {
						case LBRACKET:
							named = true;
							ArrayList<Token> tokens = new ArrayList<Token>();
							int depth = 0;
							loop2: while (!input.isEmpty()) {
								Token token3 = input.remove(0);
								
								switch (token3.type) {
									case LBRACKET:
										depth++;
										tokens.add(token3);
										break;
									case RBRACKET:
										depth--;
										if (depth==-1) {
											Parser p = new Parser("", tokens);
											Tag tag2 = p.parse();
											tag.addArg(tag2);
											break loop2;
										}
									default:
										tokens.add(token3);
										break;
								}
							}
							break;
						default:
							if (named) {
								input.add(0,token2);
								break loop;
							} else {
								name += token2.value;
								break;
							}
					}
				}
				break;
			case WORD:
				tag.addArg(token.value);
				break;
		}
		
		System.out.println("POSTSTEP "+input.size()+" "+token);
		System.out.println("\t"+tag);
	}
}
