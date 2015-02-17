package com.iconmaster.markyp.compile;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author iconmaster
 */
public class Converter {
	public int width = 19;
	public int height = 13;
	public int tabSize = 1;

	public String toJSON(Formatter.Output format) { // TODO Modularize
		StringBuilder sb = new StringBuilder("[\"");
		StringBuilder line = new StringBuilder();
		int col = 0;
		int row = 0;
		boolean bullet = true;
		boolean brk = false;
		Stack<String> alignMode = new Stack<String>();
		Stack<ListMode> listMode = new Stack<ListMode>();
		alignMode.push("left");

		for (int i = 0; i < format.s.length(); i++) {
			char c = format.s.charAt(i);
			char op = format.c.charAt(i);

			switch (op) {
				case (Constants.OP_TEXT):
					line.append(c);
					col++;
					break;
				case (Constants.OP_BEGIN):
					switch (c) {
						case (Constants.CHAR_BOLD):
							line.append("\",{text:\"\",bold:true,extra:[\"");
							break;
						case (Constants.CHAR_ITALIC):
							line.append("\",{text:\"\",italic:true,extra:[\"");
							break;
						case (Constants.CHAR_UNDERLINE):
							line.append("\",{text:\"\",underline:true,extra:[\"");
							break;
						case (Constants.CHAR_OBF):
							line.append("\",{text:\"\",obfuscated:true,extra:[\"");
							break;
						case (Constants.CHAR_STRIKE):
							line.append("\",{text:\"\",strikethrough:true,extra:[\"");
							break;
						case (Constants.CHAR_ALIGN):
							alignMode.push((String) format.argMap.get(i));
							break;
						case (Constants.CHAR_COLOR):
							line.append("\",{text:\"\",color:\"");
							line.append((String) format.argMap.get(i));
							line.append("\",extra:[\"");
							break;
						case (Constants.CHAR_SEL):
							line.append("\",{selector:\"");
							break;
						case (Constants.CHAR_CLICK):
							String[] ss = (String[]) format.argMap.get(i);
							line.append("\",{text:\"\",clickEvent:{action:\"");
							line.append(ss[0]);
							line.append("\",value:\"");
							line.append(ss[1]);
							line.append("\"},extra:[\"");
							break;
						case (Constants.CHAR_HOVER):
							ss = (String[]) format.argMap.get(i);
							line.append("\",{text:\"\",hoverEvent:{action:\"");
							line.append(ss[0]);
							line.append("\",value:\"");
							line.append(ss[1]);
							line.append("\"},extra:[\"");
							break;
						case (Constants.CHAR_LIST):
							listMode.push((ListMode) format.argMap.get(i));
							break;
					}
					break;
				case (Constants.OP_END):
					switch (c) {
						case (Constants.CHAR_BOLD):
						case (Constants.CHAR_ITALIC):
						case (Constants.CHAR_UNDERLINE):
						case (Constants.CHAR_OBF):
						case (Constants.CHAR_STRIKE):
						case (Constants.CHAR_COLOR):
						case (Constants.CHAR_CLICK):
						case (Constants.CHAR_HOVER):
							line.append("\"]},\"");
							break;
						case (Constants.CHAR_SEL):
							line.append("\"},\"");
							break;
						case (Constants.CHAR_ALIGN):
							alignMode.pop();
							break;
						case (Constants.CHAR_LIST):
							listMode.pop();
							break;
					}
					break;
				case (Constants.OP_EXEC):
					switch (c) {
						case (Constants.CHAR_BR):
							brk = true;
							break;
						case (Constants.CHAR_PBR):
							brk = true;
							row = height-1;
							break;
						case (Constants.CHAR_SCORE): {
							String[] args = (String[]) format.argMap.get(i);

							line.append("\",{score:{name:\"");
							line.append(args[0]);
							line.append("\",objective:\"");
							line.append(args[1]);
							line.append("\"}},\"");
						}
						break;
						case (Constants.CHAR_LANG): {
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
						case (Constants.CHAR_TABSIZE):
							this.tabSize = (Integer) format.argMap.get(i);
							break;
						case (Constants.CHAR_TAB):
							int tabs = tabSize;
							if (format.argMap.containsKey(i)) {
								tabs = (Integer) format.argMap.get(i);
							}
							for (int j=0;j<tabs;j++) {
								line.append(" ");
								col++;
							}
							break;
					}
					break;
			}

			if (col==width) {
				brk = true;
				bullet = false;
			}

			if (brk) {
				row++;
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

				if (bullet && !listMode.isEmpty()) {
					int tabs = tabSize * listMode.size();
					ListMode lm = listMode.peek();
					String prefix = lm.nextPoint();
					for (int j=0;j<tabs;j++) {
						sb.append(" ");
					}
					sb.append(prefix);
					sb.append(" ");
					col+=prefix.length()+tabs+1;
				}

				sb.append(line);
				if (row==height) {
					row = 0;
					sb.append("\",/*@PBR@*/\"");
				}
				line = new StringBuilder();
				col = 0;

				brk = false;
				bullet = true;
			}
		}

		sb.append(line);
		sb.append("\"]");
		return sb.toString();
	}
}
