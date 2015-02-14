package com.iconmaster.markyp.compile;

import java.util.ArrayList;
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
						case (TagHandler.CHAR_CLICK):
							String[] ss = (String[]) format.argMap.get(i);
							line.append("\",{text:\"\",clickEvent:{action:\"");
							line.append(ss[0]);
							line.append("\",value:\"");
							line.append(ss[1]);
							line.append("\"},extra:[\"");
							break;
						case (TagHandler.CHAR_HOVER):
							ss = (String[]) format.argMap.get(i);
							line.append("\",{text:\"\",hoverEvent:{action:\"");
							line.append(ss[0]);
							line.append("\",value:\"");
							line.append(ss[1]);
							line.append("\"},extra:[\"");
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
						case (TagHandler.CHAR_CLICK):
						case (TagHandler.CHAR_HOVER):
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
						case (TagHandler.CHAR_SCORE): {
							String[] args = (String[]) format.argMap.get(i);
							
							line.append("\",{score:{name:\"");
							line.append(args[0]);
							line.append("\",objective:\"");
							line.append(args[1]);
							line.append("\"}},\"");
						}
						break;
						case (TagHandler.CHAR_LANG): {
							ArrayList<String> args = (ArrayList<String>) format.argMap.get(i);
							line.append("\",{translate:\"");
							line.append(args.get(0));
							line.append("\"");
							if (args.size()>1) {
								line.append(",with:[");
								for (int j=1;j<args.size();j++) {
									line.append("\"");
									line.append(args.get(j));
									line.append("\",");
								}
								line.deleteCharAt(line.length()-1);
								line.append("]");
							}
							line.append("},\"");
						}
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
