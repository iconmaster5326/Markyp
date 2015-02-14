package com.iconmaster.markyp.compile;

import com.iconmaster.srcml.parse.Tag;
import java.util.ArrayList;
import java.util.HashMap;

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
		public HashMap<Integer,Object> argMap;

		public Output(String s, String c, HashMap<Integer, Object> argMap) {
			this.s = s;
			this.c = c;
			this.argMap = argMap;
		}
	}
	
	public StringBuilder sb1 = new StringBuilder();
	public StringBuilder sb2 = new StringBuilder();
	public HashMap<Integer,Object> argMap = new HashMap<Integer, Object>();
	
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
				formatArgs(tag,0);
			}
		}
	}
	
	public void formatArgs(Tag tag, int argNo) {
		ArrayList<Tag> arg = tag.args.get(argNo);
		if (arg!=null) {
			for (Tag tag2 : arg) {
				formatTag(tag2);
			}
		}
	}
	
	public Output format(Tag tag) {
		formatTag(tag);
		
		return new Output(sb1.toString(), sb2.toString(), argMap);
	}
}
