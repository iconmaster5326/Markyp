package com.iconmaster.srcml.tokenize;

import java.util.regex.Pattern;

/**
 *
 * @author iconmaster
 */
public class Token {
	public static enum Type {
		ESC_SLASH("\\\\\\\\"),
		ESC_LBRACE("\\\\\\["),
		ESC_RBRACE("\\\\\\]"),
		ESC_LBRACKET("\\\\\\{"),
		ESC_RBRACKET("\\\\\\}"),
		SLASH("\\\\"),
		RAWBRACE("\\["),
		RAWBRACKET("\\{"),
		LBRACE("\\["),
		RBRACE("\\]"),
		LBRACKET("\\{"),
		RBRACKET("\\}"),
		EQUALS("\\="),
		WORD("[^\\{\\}\\[\\]\\\\\\*\\=]+");
		
		public Pattern matcher;
		
		private Type(String matches) {
			matcher = Pattern.compile("^"+matches);
		}
	}
	
	public Type type;
	public String value;

	public Token(Type type, String value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Token{" + "type=" + type + ", value='" + value + "'}";
	}
}
