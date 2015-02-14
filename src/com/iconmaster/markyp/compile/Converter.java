package com.iconmaster.markyp.compile;

import java.util.Stack;

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
		boolean brk = false;
		Stack<String> alignMode = new Stack<String>();
		alignMode.push("left");
		
		for (int i = 0; i < format.s.length(); i++) {
			char c = format.s.charAt(i);
			char op = format.c.charAt(i);
			
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
						case (TagHandler.CHAR_ITALIC):
							line.append("\",{text:\"\",italic:true,extra:[\"");
							break;
						case (TagHandler.CHAR_UNDERLINE):
							line.append("\",{text:\"\",underline:true,extra:[\"");
							break;
						case (TagHandler.CHAR_OBF):
							line.append("\",{text:\"\",obfuscated:true,extra:[\"");
							break;
						case (TagHandler.CHAR_STRIKE):
							line.append("\",{text:\"\",strikethrough:true,extra:[\"");
							break;
						case (TagHandler.CHAR_ALIGN):
							alignMode.push((String) format.argMap.get(i));
							break;
						case (TagHandler.CHAR_COLOR):
							line.append("\",{text:\"\",color:\"");
							line.append((String) format.argMap.get(i));
							line.append("\",extra:[\"");
							break;
						case (TagHandler.CHAR_SEL):
							line.append("\",{selector:\"");
							break;
					}
					break;
				case (TagHandler.OP_END):
					switch (c) {
						case (TagHandler.CHAR_BOLD):
						case (TagHandler.CHAR_ITALIC):
						case (TagHandler.CHAR_UNDERLINE):
						case (TagHandler.CHAR_OBF):
						case (TagHandler.CHAR_STRIKE):
						case (TagHandler.CHAR_COLOR):
							line.append("\"]},\"");
							break;
						case (TagHandler.CHAR_SEL):
							line.append("\"},\"");
							break;
						case (TagHandler.CHAR_ALIGN):
							alignMode.pop();
							break;
					}
					break;
				case (TagHandler.OP_EXEC):
					switch (c) {
						case (TagHandler.CHAR_BR):
							brk = true;
							break;
					}
					break;
			}
			
			if (col==width) {
				brk = true;
			}
			
			if (brk) {
				if (alignMode.peek().equalsIgnoreCase("left")) {
					for (int j=col;j<width;j++) {
						line.append(" ");
					}
				} else if (alignMode.peek().equalsIgnoreCase("right")) {
					for (int j=width;j>col;j--) {
						sb.append(" ");
					}
				} else if (alignMode.peek().equalsIgnoreCase("center")) {
					for (int j=width;j>col;j-=2) {
						sb.append(" ");
					}
					for (int j=col;j<width;j+=2) {
						line.append(" ");
					}
				}
				
				sb.append(line);
				//sb.append("\",\"");
				line = new StringBuilder();
				col = 0;
				
				brk = false;
			}
		}
		
		sb.append(line);
		sb.append("\"]");
		return sb.toString();
	}
}
