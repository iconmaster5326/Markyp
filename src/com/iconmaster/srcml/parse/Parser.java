package com.iconmaster.srcml.parse;

import com.iconmaster.srcml.tokenize.Token;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author iconmaster
 */
public class Parser {
	public ArrayList<Token> input;
	public Stack<Tag> stack = new Stack<>();
	
	public static enum Mode {
		TEXT,CMDWORD;
	}
	
	public Mode mode = Mode.TEXT;
	
	public Parser(ArrayList<Token> input) {
		this.input = input;
	}
	
	public Tag parse() {
		stack.push(new Tag("srcml"));
		while (!input.isEmpty()) {
			step();
		}
		return tag();
	}
	
	public void step() {
		Token token = input.remove(0);

		switch (mode) {
			case TEXT:
				switch (token.type) {
					case SLASH:
						stack.push(new Tag());
						mode = Mode.CMDWORD;
						break;
					case RBRACKET:
						Tag tag = stack.pop();
						break;
					default:
						tag().addArg(token.value);
						break;
				}
				break;
			case CMDWORD:
				switch (token.type) {
					case LBRACKET:
						stack.push(new Tag());
						mode = Mode.TEXT;
						break;
					default:
						tag().name += token.value;
						break;
				}
				break;
		}
	}
	
	public Tag tag() {
		return stack.peek();
	}
}
