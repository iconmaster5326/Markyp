package com.iconmaster.markyp.compile;

/**
 *
 * @author iconmaster
 */
public class Converter {
	public int width = 19;

	public String toJSON(Formatter.Output format) {
		StringBuilder sb = new StringBuilder("[\"");
		StringBuilder line = new StringBuilder();
		int col = 0;
		
		for (int i = 0; i < format.s.length(); i++) {
			char c = format.s.charAt(i);
			char op = format.c.charAt(i);
			
			if (col==width) {
				sb.append(line);
				sb.append("\",\"");
				line = new StringBuilder();
				col = 0;
			}
			
			switch (op) {
				case (TagHandler.OP_TEXT):
					line.append(c);
					col++;
					break;
				case (TagHandler.OP_BEGIN):
					switch (c) {
						case (TagHandler.CHAR_BOLD):
							line.append("\",{text:\"\",bold:true,extra:[\"");
							break;
					}
					break;
				case (TagHandler.OP_END):
					switch (c) {
						case (TagHandler.CHAR_BOLD):
							line.append("\"]},\"");
							break;
					}
					break;
			}
		}
		
		sb.append(line);
		sb.append("\"]");
		return sb.toString();
	}
}
