package com.iconmaster.markyp.compile;

import com.iconmaster.srcml.parse.Tag;
import java.util.HashMap;

/**
 *
 * @author iconmaster
 */
public abstract class TagHandler {
	public static final char OP_TEXT = 0;
	public static final char OP_BEGIN = 1;
	public static final char OP_END = 2;
	
	public static final char CHAR_BOLD = 0;
	public static final char CHAR_BR = 1;
	
	public static HashMap<String,TagHandler> handlers = new HashMap<String, TagHandler>();
	
	public static void init() {
		new TagHandler("b") {
			@Override
			public void format(Formatter f, Tag tag) {
				f.sb1.append(CHAR_BOLD);
				f.sb2.append(OP_BEGIN);
				
				f.formatArgs(tag);
				
				f.sb1.append(CHAR_BOLD);
				f.sb2.append(OP_END);
			}
		};
	}
	
	String name;

	public TagHandler(String name) {
		this.name = name;
		
		handlers.put(name, this);
	}
	
	public abstract void format(Formatter f, Tag tag);
}
