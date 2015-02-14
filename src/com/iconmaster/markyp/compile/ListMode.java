package com.iconmaster.markyp.compile;

import com.sun.javafx.binding.StringFormatter;

/**
 *
 * @author iconmaster
 */
public class ListMode {
	public static enum OrdMode {
		BULLET,NUMERIC,LOWER,UPPER;
		
		public String format(int pos) {
			switch (this) {
				case BULLET:
					return "";
				case NUMERIC:
					return String.valueOf(pos);
				case LOWER:
					return ""+(char)('a'+pos-1);
				case UPPER:
					return ""+(char)('A'+pos-1);
				default:
					return String.valueOf(pos);
			}
		}
		
		public String defaultSep() {
			switch (this) {
				case BULLET:
					return "*";
				case NUMERIC:
					return "%s.";
				case LOWER:
					return "%s.";
				case UPPER:
					return "%s.";
				default:
					return "%s";
			}
		}
	}
	
	public String sep;
	public OrdMode mode;
	public int pos;

	public ListMode(String sep, OrdMode mode, int pos) {
		this.sep = sep;
		this.mode = mode;
		this.pos = pos;
		
		if (sep==null) {
			this.sep = mode.defaultSep();
		}
	}
	
	public String nextPoint() {
		String out;
		if (sep.contains("%s")) {
			out = StringFormatter.format(sep, mode.format(pos)).get();
		} else {
			out = sep;
		}
		pos++;
		return out;
	}
}
