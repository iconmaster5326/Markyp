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
	public static final char OP_EXEC = 3;
	
	public static final char CHAR_BOLD = 0;
	public static final char CHAR_ITALIC = 1;
	public static final char CHAR_UNDERLINE = 2;
	public static final char CHAR_BR = 3;
	public static final char CHAR_ALIGN = 4;
	public static final char CHAR_OBF = 5;
	public static final char CHAR_STRIKE = 6;
	public static final char CHAR_COLOR = 7;
	public static final char CHAR_SEL = 8;
	public static final char CHAR_SCORE = 9;
	public static final char CHAR_LANG = 10;
	public static final char CHAR_CLICK = 11;
	public static final char CHAR_HOVER = 12;
	
	public static HashMap<String,TagHandler> handlers = new HashMap<String, TagHandler>();
	
	public static void init() {
		new TagHandler("b") {
			@Override
			public void format(Formatter f, Tag tag) {
				f.sb1.append(CHAR_BOLD);
				f.sb2.append(OP_BEGIN);
				
				f.formatArgs(tag,0);
				
				f.sb1.append(CHAR_BOLD);
				f.sb2.append(OP_END);
			}
		};
		
		new TagHandler("i") {
			@Override
			public void format(Formatter f, Tag tag) {
				f.sb1.append(CHAR_ITALIC);
				f.sb2.append(OP_BEGIN);
				
				f.formatArgs(tag,0);
				
				f.sb1.append(CHAR_ITALIC);
				f.sb2.append(OP_END);
			}
		};
		
		new TagHandler("u") {
			@Override
			public void format(Formatter f, Tag tag) {
				f.sb1.append(CHAR_UNDERLINE);
				f.sb2.append(OP_BEGIN);
				
				f.formatArgs(tag,0);
				
				f.sb1.append(CHAR_UNDERLINE);
				f.sb2.append(OP_END);
			}
		};
		
		new TagHandler("obf") {
			@Override
			public void format(Formatter f, Tag tag) {
				f.sb1.append(CHAR_OBF);
				f.sb2.append(OP_BEGIN);
				
				f.formatArgs(tag,0);
				
				f.sb1.append(CHAR_OBF);
				f.sb2.append(OP_END);
			}
		};
		
		new TagHandler("s") {
			@Override
			public void format(Formatter f, Tag tag) {
				f.sb1.append(CHAR_STRIKE);
				f.sb2.append(OP_BEGIN);
				
				f.formatArgs(tag,0);
				
				f.sb1.append(CHAR_STRIKE);
				f.sb2.append(OP_END);
			}
		};
		
		new TagHandler("br") {
			@Override
			public void format(Formatter f, Tag tag) {
				f.sb1.append(CHAR_BR);
				f.sb2.append(OP_EXEC);
			}
		};
		
		new TagHandler("align") {
			@Override
			public void format(Formatter f, Tag tag) {
				String type = Tag.rawValue(tag.args.get(0));
				
				f.argMap.put(f.sb1.length(), type);
				f.sb1.append(CHAR_ALIGN);
				f.sb2.append(OP_BEGIN);
				
				f.formatArgs(tag,1);
				
				f.sb1.append(CHAR_BR);
				f.sb2.append(OP_EXEC);
				
				f.argMap.put(f.sb1.length(), type);
				f.sb1.append(CHAR_ALIGN);
				f.sb2.append(OP_END);
			}
		};
		
		new TagHandler("color") {
			@Override
			public void format(Formatter f, Tag tag) {
				String type = Tag.rawValue(tag.args.get(0));
				
				f.argMap.put(f.sb1.length(), type);
				f.sb1.append(CHAR_COLOR);
				f.sb2.append(OP_BEGIN);
				
				f.formatArgs(tag,1);
				
				f.argMap.put(f.sb1.length(), type);
				f.sb1.append(CHAR_COLOR);
				f.sb2.append(OP_END);
			}
		};
		
		new TagHandler("sel") {
			@Override
			public void format(Formatter f, Tag tag) {
				f.sb1.append(CHAR_SEL);
				f.sb2.append(OP_BEGIN);
				
				f.formatArgs(tag,0);
				
				f.sb1.append(CHAR_SEL);
				f.sb2.append(OP_END);
			}
		};
		
		new TagHandler("score") {
			@Override
			public void format(Formatter f, Tag tag) {
				f.argMap.put(f.sb1.length(), new String[] {Tag.rawValue(tag.args.get(0)),Tag.rawValue(tag.args.get(1))});
				f.sb1.append(CHAR_SCORE);
				f.sb2.append(OP_EXEC);
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
