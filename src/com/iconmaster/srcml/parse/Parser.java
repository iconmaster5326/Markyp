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
						case RAWBRACKET:
							String rawS = "";
							named = true;
							int depth = 0;
							loop2: while (!input.isEmpty()) {
								token = input.remove(0);
								
								switch (token.type) {
									case RAWBRACKET:
									case LBRACKET:
										depth++;
										rawS += token.value;
										break;
									case RBRACKET:
										depth--;
										if (depth==-1) {
											newTag.addArg(Tag.rawTag(rawS));
											break loop2;
										} else {
											rawS += token.value;
										}
										break;
									default:
										rawS += token.value;
										break;
								}
							}
							break;
						case LBRACKET:
							ArrayList<Token> tokens = new ArrayList<Token>();
							named = true;
							depth = 0;
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
						case RAWBRACE:
							String lvalue = "";
							String rawrvalue = "";
							boolean eq = false;
							depth = 0;
							
							loop2: while (!input.isEmpty()) {
								token = input.remove(0);
								
								if (eq) {
									switch (token.type) {
										case RAWBRACE:
										case LBRACE:
											rawrvalue += token.value;
											depth++;
											break;
										case RBRACE:
											depth--;
											if (depth==-1) {
												newTag.addNamedArg(lvalue, rawrvalue);
												break loop2;
											} else {
												rawrvalue += token.value;
											}
											break;
										default:
											rawrvalue += token.value;
											break;
									}
								} else {
									switch (token.type) {
										case EQUALS:
											eq = true;
											break;
										default:
											lvalue += token.value;
											break;
									}
								}
							}
							break;
						case LBRACE:
							lvalue = "";
							ArrayList<Token> rvalue = new ArrayList<Token>();
							eq = false;
							depth = 0;
							
							loop2: while (!input.isEmpty()) {
								token = input.remove(0);
								
								if (eq) {
									switch (token.type) {
										case RAWBRACE:
										case LBRACE:
											rvalue.add(token);
											depth++;
											break;
										case RBRACE:
											depth--;
											if (depth==-1) {
												Parser p = new Parser(rvalue);
												newTag.namedArgs.put(lvalue, p.parse());
												break loop2;
											} else {
												rvalue.add(token);
											}
											break;
										default:
											rvalue.add(token);
											break;
									}
								} else {
									switch (token.type) {
										case EQUALS:
											eq = true;
											break;
										default:
											lvalue += token.value;
											break;
									}
								}
							}
							break;
						default:
							if (named) {
								input.add(0,token);
								break loop;
							} else {
								newTag.name += token.value;
								break;
							}
					}
				}
				tags.add(newTag);
				break;
			default:
				tags.add(Tag.rawTag(token.value));
				break;
		}
	}
}
