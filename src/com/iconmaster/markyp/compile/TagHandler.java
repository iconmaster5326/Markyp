package com.iconmaster.markyp.compile;

import com.iconmaster.srcml.parse.Tag;
import java.util.HashMap;

/**
 *
 * @author iconmaster
 */
public abstract class TagHandler {
	public static HashMap<String,TagHandler> handlers = new HashMap<String, TagHandler>();
	
	public static void init() {
		new TagHandler("srcml") {
			@Override
			public void handleTag(JSONConverter json, StringBuilder sb, Tag tag) {
				sb.append(json.convert(tag.args.get(0)));
			}
		};
		
		new TagHandler("b") {
			@Override
			public void handleTag(JSONConverter json, StringBuilder sb, Tag tag) {
				sb.append("{text:\"\",bold:true,extra:");
				sb.append(json.convert(tag.args.get(0)));
				sb.append("}");
			}
		};
	}
	
	String name;

	public TagHandler(String name) {
		this.name = name;
		
		handlers.put(name, this);
	}
	
	public abstract void handleTag(JSONConverter json, StringBuilder sb, Tag tag);
}
