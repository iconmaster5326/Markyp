package com.iconmaster.srcml.parse;

import com.iconmaster.srcml.tokenize.Token;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class Parser {
	public ArrayList<Token> input;
	public ArrayList<Tag> tags = new ArrayList<Tag>();
	
	public Parser(ArrayList<Token> input) {
		this.input = input;
	}
	
	public ArrayList<Tag> parse() {
		while (!input.isEmpty()) {
			step();
		}
		
		return tags;
	}
	
	public void step() {
		Token token = input.remove(0);

		switch (token.type) {
			case SLASH:
				Tag newTag = new Tag();
				boolean named = false;
				loop: while (!input.isEmpty()) {
					token = input.remove(0);
					
					switch (token.type) {
						case LBRACKET:
							ArrayList<Token> tokens = new ArrayList<Token>();
							named = true;
							int depth = 0;
							loop2: while (!input.isEmpty()) {
								token = input.remove(0);
								
								switch (token.type) {
									case RAWBRACKET:
									case LBRACKET:
										depth++;
										tokens.add(token);
										break;
									case RBRACKET:
										depth--;
										if (depth==-1) {
											Parser p = new Parser(tokens);
											newTag.args.add(p.parse());
											break loop2;
										} else {
											tokens.add(token);
										}
										break;
									default:
										tokens.add(token);
										break;
								}
							}
							break;
						default:
							if (named) {
								input.add(0,token);
								tags.add(newTag);
								break loop;
							} else {
								newTag.name += token.value;
								break;
							}
					}
				}
				break;
			default:
				tags.add(Tag.rawTag(token.value));
				break;
		}
	}
}
