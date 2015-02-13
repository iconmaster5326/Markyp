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
					default:
				}
				break;
			case CMDWORD:
				switch (token.type) {
					
				}
				break;
		}
	}
	
	public Tag tag() {
		return stack.peek();
	}
}
