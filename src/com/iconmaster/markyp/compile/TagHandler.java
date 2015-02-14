package com.iconmaster.markyp.compile;

import com.iconmaster.srcml.parse.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

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
	public static HashMap<String,String> clickNames = new HashMap<String, String>();
	public static HashMap<String,String> hoverNames = new HashMap<String, String>();
	
	public static void init() {
		clickNames.put("url", "open_url");
		clickNames.put("run", "run_command");
		clickNames.put("page", "change_page");
		clickNames.put("suggest", "suggest_command");
		
		hoverNames.put("text", "show_text");
		hoverNames.put("item", "show_item");
		hoverNames.put("entity", "show_entity");
		hoverNames.put("achievement", "show_achievement");
		
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
		
		new TagHandler("lang") {
			@Override
			public void format(Formatter f, Tag tag) {
				ArrayList<String> ss = new ArrayList<String>();
				for (ArrayList<Tag> arg : tag.args) {
					ss.add(Tag.rawValue(arg));
				}
				f.argMap.put(f.sb1.length(), ss);
				f.sb1.append(CHAR_LANG);
				f.sb2.append(OP_EXEC);
			}
		};
		
		new TagHandler("click") {
			@Override
			public void format(Formatter f, Tag tag) {
				String type = null;
				String value = null;
				for (Entry<String,ArrayList<Tag>> namedArg : tag.namedArgs.entrySet()) {
					type = clickNames.containsKey(namedArg.getKey()) ? clickNames.get(namedArg.getKey()) : namedArg.getKey();
					value = Tag.rawValue(namedArg.getValue());
				}
				String[] ss = new String[] {type,value};
				
				f.argMap.put(f.sb1.length(), ss);
				f.sb1.append(CHAR_CLICK);
				f.sb2.append(OP_BEGIN);
				
				f.formatArgs(tag,0);
				
				f.argMap.put(f.sb1.length(), ss);
				f.sb1.append(CHAR_CLICK);
				f.sb2.append(OP_END);
			}
		};
		
		new TagHandler("hover") {
			@Override
			public void format(Formatter f, Tag tag) {
				String type = null;
				String value = null;
				for (Entry<String,ArrayList<Tag>> namedArg : tag.namedArgs.entrySet()) {
					type = hoverNames.containsKey(namedArg.getKey()) ? hoverNames.get(namedArg.getKey()) : namedArg.getKey();
					value = Tag.rawValue(namedArg.getValue());
				}
				String[] ss = new String[] {type,value};
				
				f.argMap.put(f.sb1.length(), ss);
				f.sb1.append(CHAR_HOVER);
				f.sb2.append(OP_BEGIN);
				
				f.formatArgs(tag,0);
				
				f.argMap.put(f.sb1.length(), ss);
				f.sb1.append(CHAR_HOVER);
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
