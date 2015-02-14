package com.iconmaster.markyp.compile;

import com.iconmaster.srcml.parse.Tag;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class Formatter {
	public static String escape(String str) {
		return str.replace("\\", "\\\\").replace("\"", "\\\"");
	}

	public static class Output {
		public String s;
		public String c;

		public Output(String s, String c) {
			this.s = s;
			this.c = c;
		}
	}
	
	public StringBuilder sb1 = new StringBuilder();
	public StringBuilder sb2 = new StringBuilder();
	
	public void formatTag(Tag tag) {
		if (tag.rawValue!=null) {
			String form = escape(tag.rawValue);
			sb1.append(form);
			sb2.append(new char[form.length()]);
		} else {
			TagHandler handler = TagHandler.handlers.get(tag.name);
			if (handler!=null) {
				handler.format(this, tag);
			} else {
				formatArgs(tag);
			}
		}
	}
	
	public void formatArgs(Tag tag) {
		for (ArrayList<Tag> arg : tag.args) {
			for (Tag tag2 : arg) {
				formatTag(tag2);
			}
		}
	}
	
	public Output format(Tag tag) {
		formatTag(tag);
		
		return new Output(sb1.toString(), sb2.toString());
	}
}
