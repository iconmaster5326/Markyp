package com.iconmaster.markyp.compile;

import com.iconmaster.srcml.parse.Tag;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class Formatter {
	public static class Output {
		public String s;
		public String c;

		public Output(String s, String c) {
			this.s = s;
			this.c = c;
		}
	}
	
	public static void format(StringBuilder sb1, StringBuilder sb2, Tag tag) {
		if (tag.rawValue!=null) {
			sb1.append(tag.rawValue);
			sb2.append(new char[tag.rawValue.length()]);
		} else {
			TagHandler handler = TagHandler.handlers.get(tag.name);
			if (handler!=null) {
				handler.format(sb1, sb2, tag);
			} else {
				formatArgs(sb1, sb2, tag);
			}
		}
	}
	
	public static void formatArgs(StringBuilder sb1, StringBuilder sb2, Tag tag) {
		for (ArrayList<Tag> arg : tag.args) {
			for (Tag tag2 : arg) {
				format(sb1, sb2, tag2);
			}
		}
	}
	
	public static Output format(Tag tag) {
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		format(sb1, sb2, tag);
		
		return new Output(sb1.toString(), sb2.toString());
	}
}
