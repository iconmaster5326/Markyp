package com.iconmaster.markyp.compile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.iconmaster.markyp.compile.ListMode.OrdMode;
import com.iconmaster.srcml.parse.Tag;

/**
 *
 * @author iconmaster
 */
public enum TagHandler {

	BOLD("b") {
		@Override
		public void format(Formatter f, Tag tag) {
			f.sb1.append(Constants.CHAR_BOLD);
			f.sb2.append(Constants.OP_BEGIN);

			f.formatArgs(tag, 0);

			f.sb1.append(Constants.CHAR_BOLD);
			f.sb2.append(Constants.OP_END);
		}
	},
	ITALIC("i") {
		@Override
		public void format(Formatter f, Tag tag) {
			f.sb1.append(Constants.CHAR_ITALIC);
			f.sb2.append(Constants.OP_BEGIN);

			f.formatArgs(tag, 0);

			f.sb1.append(Constants.CHAR_ITALIC);
			f.sb2.append(Constants.OP_END);
		}
	},
	UNDERLINE("u") {
		@Override
		public void format(Formatter f, Tag tag) {
			f.sb1.append(Constants.CHAR_UNDERLINE);
			f.sb2.append(Constants.OP_BEGIN);

			f.formatArgs(tag, 0);

			f.sb1.append(Constants.CHAR_UNDERLINE);
			f.sb2.append(Constants.OP_END);
		}
	},
	OBFUSCATE("obf") {
		@Override
		public void format(Formatter f, Tag tag) {
			f.sb1.append(Constants.CHAR_OBF);
			f.sb2.append(Constants.OP_BEGIN);

			f.formatArgs(tag, 0);

			f.sb1.append(Constants.CHAR_OBF);
			f.sb2.append(Constants.OP_END);
		}
	},
	STRIKETHROUGH("s") {
		@Override
		public void format(Formatter f, Tag tag) {
			f.sb1.append(Constants.CHAR_STRIKE);
			f.sb2.append(Constants.OP_BEGIN);

			f.formatArgs(tag, 0);

			f.sb1.append(Constants.CHAR_STRIKE);
			f.sb2.append(Constants.OP_END);
		}
	},
	BREAK("br") {
		@Override
		public void format(Formatter f, Tag tag) {
			f.sb1.append(Constants.CHAR_BR);
			f.sb2.append(Constants.OP_EXEC);
		}
	},
	PAGE_BREAK("pbr") {
		@Override
		public void format(Formatter f, Tag tag) {
			f.sb1.append(Constants.CHAR_PBR);
			f.sb2.append(Constants.OP_EXEC);
		}
	},
	ALIGN("align") {
		@Override
		public void format(Formatter f, Tag tag) {
			String type = Tag.rawValue(tag.args.get(0));

			f.argMap.put(f.sb1.length(), type);
			f.sb1.append(Constants.CHAR_ALIGN);
			f.sb2.append(Constants.OP_BEGIN);

			f.formatArgs(tag, 1);

			f.sb1.append(Constants.CHAR_BR);
			f.sb2.append(Constants.OP_EXEC);

			f.argMap.put(f.sb1.length(), type);
			f.sb1.append(Constants.CHAR_ALIGN);
			f.sb2.append(Constants.OP_END);
		}
	},
	COLOR("color") {
		@Override
		public void format(Formatter f, Tag tag) {
			String type = Tag.rawValue(tag.args.get(0));

			f.argMap.put(f.sb1.length(), type);
			f.sb1.append(Constants.CHAR_COLOR);
			f.sb2.append(Constants.OP_BEGIN);

			f.formatArgs(tag, 1);

			f.argMap.put(f.sb1.length(), type);
			f.sb1.append(Constants.CHAR_COLOR);
			f.sb2.append(Constants.OP_END);
		}
	},
	SELECTOR("sel") {
		@Override
		public void format(Formatter f, Tag tag) {
			f.sb1.append(Constants.CHAR_SEL);
			f.sb2.append(Constants.OP_BEGIN);

			f.formatArgs(tag, 0);

			f.sb1.append(Constants.CHAR_SEL);
			f.sb2.append(Constants.OP_END);
		}
	},
	SCORE("score") {
		@Override
		public void format(Formatter f, Tag tag) {
			f.argMap.put(
					f.sb1.length(),
					new String[] { Tag.rawValue(tag.args.get(0)),
							Tag.rawValue(tag.args.get(1)) });
			f.sb1.append(Constants.CHAR_SCORE);
			f.sb2.append(Constants.OP_EXEC);
		}
	},
	TRANSLATION("lang") {
		@Override
		public void format(Formatter f, Tag tag) {
			ArrayList<String> ss = new ArrayList<String>();
			for (ArrayList<Tag> arg : tag.args) {
				ss.add(Tag.rawValue(arg));
			}
			f.argMap.put(f.sb1.length(), ss);
			f.sb1.append(Constants.CHAR_LANG);
			f.sb2.append(Constants.OP_EXEC);
		}
	},
	CLICK("click") {
		@Override
		public void format(Formatter f, Tag tag) {
			String type = null;
			String value = null;
			for (Entry<String, ArrayList<Tag>> namedArg : tag.namedArgs
					.entrySet()) {
				type = clickNames.containsKey(namedArg.getKey()) ? clickNames
						.get(namedArg.getKey()) : namedArg.getKey();
				value = Tag.rawValue(namedArg.getValue());
			}
			String[] ss = new String[] { type, value };

			f.argMap.put(f.sb1.length(), ss);
			f.sb1.append(Constants.CHAR_CLICK);
			f.sb2.append(Constants.OP_BEGIN);

			f.formatArgs(tag, 0);

			f.argMap.put(f.sb1.length(), ss);
			f.sb1.append(Constants.CHAR_CLICK);
			f.sb2.append(Constants.OP_END);
		}
	},
	HOVER("hover") {
		@Override
		public void format(Formatter f, Tag tag) {
			String type = null;
			String value = null;
			for (Entry<String, ArrayList<Tag>> namedArg : tag.namedArgs
					.entrySet()) {
				type = hoverNames.containsKey(namedArg.getKey()) ? hoverNames
						.get(namedArg.getKey()) : namedArg.getKey();
				value = Tag.rawValue(namedArg.getValue());
			}
			String[] ss = new String[] { type, value };

			f.argMap.put(f.sb1.length(), ss);
			f.sb1.append(Constants.CHAR_HOVER);
			f.sb2.append(Constants.OP_BEGIN);

			f.formatArgs(tag, 0);

			f.argMap.put(f.sb1.length(), ss);
			f.sb1.append(Constants.CHAR_HOVER);
			f.sb2.append(Constants.OP_END);
		}
	},
	LIST("list") {
		@Override
		public void format(Formatter f, Tag tag) {
			String sep = tag.namedArgs.containsKey("sep") ? Tag
					.rawValue(tag.namedArgs.get("sep")) : null;
			String smode = tag.namedArgs.containsKey("type") ? Tag
					.rawValue(tag.namedArgs.get("type")) : "bullet";
			OrdMode omode = listModes.containsKey(smode) ? listModes.get(smode)
					: OrdMode.BULLET;
			int pos = tag.namedArgs.containsKey("pos") ? Integer.parseInt(Tag
					.rawValue(tag.namedArgs.get("pos"))) : 1;

			ListMode mode = new ListMode(sep, omode, pos);

			f.argMap.put(f.sb1.length(), mode);
			f.sb1.append(Constants.CHAR_LIST);
			f.sb2.append(Constants.OP_BEGIN);

			for (int i = 0; i < tag.args.size(); i++) {
				f.formatArgs(tag, i);

				f.sb1.append(Constants.CHAR_BR);
				f.sb2.append(Constants.OP_EXEC);
			}

			f.argMap.put(f.sb1.length(), mode);
			f.sb1.append(Constants.CHAR_LIST);
			f.sb2.append(Constants.OP_END);
		}
	},
	TAB_SIZE("tabsize") {
		@Override
		public void format(Formatter f, Tag tag) {
			String tabS = Tag.rawValue(tag.args.get(0));
			Integer tab = Integer.parseInt(tabS);
			f.argMap.put(f.sb1.length(), tab);
			f.sb1.append(Constants.CHAR_TABSIZE);
			f.sb2.append(Constants.OP_EXEC);
		}
	},
	TAB("tab") {
		@Override
		public void format(Formatter f, Tag tag) {
			if (!tag.args.isEmpty()) {
				String tabS = Tag.rawValue(tag.args.get(0));
				try {
					Integer tab = Integer.parseInt(tabS);
					f.argMap.put(f.sb1.length(), tab);
				} catch (Exception ex) {
				}
			}
			f.sb1.append(Constants.CHAR_TAB);
			f.sb2.append(Constants.OP_EXEC);
		}
	};

	public static HashMap<String, TagHandler> handlers = new HashMap<String, TagHandler>();
	public static HashMap<String, String> clickNames = new HashMap<String, String>();
	public static HashMap<String, String> hoverNames = new HashMap<String, String>();
	public static HashMap<String, OrdMode> listModes = new HashMap<String, OrdMode>();

	private String name;

	private TagHandler(String name) {
		this.name = name;
	}

	public abstract void format(Formatter f, Tag tag);

	// Run when the class is instantiated
	static {
		clickNames.put("url", "open_url");
		clickNames.put("run", "run_command");
		clickNames.put("page", "change_page");
		clickNames.put("suggest", "suggest_command");

		hoverNames.put("text", "show_text");
		hoverNames.put("item", "show_item");
		hoverNames.put("entity", "show_entity");
		hoverNames.put("achievement", "show_achievement");

		listModes.put("bullet", OrdMode.BULLET);
		listModes.put("number", OrdMode.NUMERIC);
		listModes.put("lower", OrdMode.LOWER);
		listModes.put("upper", OrdMode.UPPER);

		for (TagHandler tag : values()) {
			handlers.put(tag.name, tag);
		}
	}
}
