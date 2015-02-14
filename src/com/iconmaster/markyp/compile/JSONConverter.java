package com.iconmaster.markyp.compile;

import com.iconmaster.srcml.parse.Tag;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class JSONConverter {
	public static String escape(String str) {
		return str.replace("\\", "\\\\").replace("\"", "\\\"");
	}
	
	public String convert(ArrayList<Tag> tags) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		if (!tags.isEmpty()) {
			for (Tag tag2 : tags) {
				sb.append(convert(tag2));
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	public String convert(Tag tag) {
		StringBuilder sb = new StringBuilder();
		if (tag.name.isEmpty()) {
			if (!tag.args.isEmpty()) {
				return convert(tag.args.get(0));
			} else {
				return "\""+escape(tag.rawValue)+"\"";
			}
		} else {
			TagHandler handler = TagHandler.handlers.get(tag.name);
			if (handler!=null) {
				handler.handleTag(this, sb, tag);
			}
		}
		return sb.toString();
	}
}
