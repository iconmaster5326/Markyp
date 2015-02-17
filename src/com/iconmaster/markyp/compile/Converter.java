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

	/**
	 * Used to pass variables through methods. Reassigned each run.
	 */
	private ConverterData data;

	public String toJSON(Formatter.Output format) {
		data = new ConverterData(format);
		boolean bullet = true;
		StringBuilder sb = new StringBuilder("[\"");
		data.alignMode.push("left");

		for (int i = 0; i < data.format.s.length(); i++) {

			parse(i);

			if (data.col == width) {
				data.brk = true;
				bullet = false;
			}

			if (data.brk) {
				data.row++;
				if (data.alignMode.peek().equalsIgnoreCase("left")) {
					for (int j = data.col; j < width; j++) {
						data.line.append(" ");
					}
				} else if (data.alignMode.peek().equalsIgnoreCase("right")) {
					for (int j = width; j > data.col; j--) {
						sb.append(" ");
					}
				} else if (data.alignMode.peek().equalsIgnoreCase("center")) {
					for (int j = width; j > data.col; j -= 2) {
						sb.append(" ");
					}
					for (int j = data.col; j < width; j += 2) {
						data.line.append(" ");
					}
				}

				if (bullet && !data.listMode.isEmpty()) {
					int tabs = tabSize * data.listMode.size();
					ListMode lm = data.listMode.peek();
					String prefix = lm.nextPoint();
					for (int j = 0; j < tabs; j++) {
						sb.append(" ");
					}
					sb.append(prefix);
					sb.append(" ");
					data.col += prefix.length() + tabs + 1;
				}

				sb.append(data.line);
				if (data.row == height) {
					data.row = 0;
					sb.append("\",/*@PBR@*/\"");
				}
				data.line = new StringBuilder();
				data.col = 0;

				data.brk = false;
				bullet = true;
			}
		}

		sb.append(data.line);
		sb.append("\"]");
		return sb.toString();
	}

	private void parse(int i) {
		char c = data.format.s.charAt(i);
		char op = data.format.c.charAt(i);

		switch (op) {
		case (Constants.OP_TEXT):
			data.line.append(c);
			data.col++;
			break;
		case (Constants.OP_BEGIN):
			parseBegin(i, c);
			break;
		case (Constants.OP_END):
			parseEnd(c);
			break;
		case (Constants.OP_EXEC):
			parseExec(i, c);
			break;
		}
	}

	private void parseBegin(int i, char c) {
		switch (c) {
		case (Constants.CHAR_BOLD):
			data.line.append("\",{text:\"\",bold:true,extra:[\"");
			break;
		case (Constants.CHAR_ITALIC):
			data.line.append("\",{text:\"\",italic:true,extra:[\"");
			break;
		case (Constants.CHAR_UNDERLINE):
			data.line.append("\",{text:\"\",underline:true,extra:[\"");
			break;
		case (Constants.CHAR_OBF):
			data.line.append("\",{text:\"\",obfuscated:true,extra:[\"");
			break;
		case (Constants.CHAR_STRIKE):
			data.line.append("\",{text:\"\",strikethrough:true,extra:[\"");
			break;
		case (Constants.CHAR_ALIGN):
			data.alignMode.push((String) data.format.argMap.get(i));
			break;
		case (Constants.CHAR_COLOR):
			data.line.append("\",{text:\"\",color:\"");
			data.line.append((String) data.format.argMap.get(i));
			data.line.append("\",extra:[\"");
			break;
		case (Constants.CHAR_SEL):
			data.line.append("\",{selector:\"");
			break;
		case (Constants.CHAR_CLICK):
			String[] ss = (String[]) data.format.argMap.get(i);
			data.line.append("\",{text:\"\",clickEvent:{action:\"");
			data.line.append(ss[0]);
			data.line.append("\",value:\"");
			data.line.append(ss[1]);
			data.line.append("\"},extra:[\"");
			break;
		case (Constants.CHAR_HOVER):
			ss = (String[]) data.format.argMap.get(i);
			data.line.append("\",{text:\"\",hoverEvent:{action:\"");
			data.line.append(ss[0]);
			data.line.append("\",value:\"");
			data.line.append(ss[1]);
			data.line.append("\"},extra:[\"");
			break;
		case (Constants.CHAR_LIST):
			data.listMode.push((ListMode) data.format.argMap.get(i));
			break;
		}
	}

	private void parseEnd(char c) {
		switch (c) {
		case (Constants.CHAR_BOLD):
		case (Constants.CHAR_ITALIC):
		case (Constants.CHAR_UNDERLINE):
		case (Constants.CHAR_OBF):
		case (Constants.CHAR_STRIKE):
		case (Constants.CHAR_COLOR):
		case (Constants.CHAR_CLICK):
		case (Constants.CHAR_HOVER):
			data.line.append("\"]},\"");
			break;
		case (Constants.CHAR_SEL):
			data.line.append("\"},\"");
			break;
		case (Constants.CHAR_ALIGN):
			data.alignMode.pop();
			break;
		case (Constants.CHAR_LIST):
			data.listMode.pop();
			break;
		}
	}

	private void parseExec(int i, char c) {
		switch (c) {
		case (Constants.CHAR_BR):
			data.brk = true;
			break;
		case (Constants.CHAR_PBR):
			data.brk = true;
			data.row = height - 1;
			break;
		case (Constants.CHAR_SCORE): {
			String[] args = (String[]) data.format.argMap.get(i);

			data.line.append("\",{score:{name:\"");
			data.line.append(args[0]);
			data.line.append("\",objective:\"");
			data.line.append(args[1]);
			data.line.append("\"}},\"");
		}
			break;
		case (Constants.CHAR_LANG): {
			@SuppressWarnings("unchecked")
			ArrayList<String> args = (ArrayList<String>) data.format.argMap
					.get(i);
			data.line.append("\",{translate:\"");
			data.line.append(args.get(0));
			data.line.append("\"");
			if (args.size() > 1) {
				data.line.append(",with:[");
				for (int j = 1; j < args.size(); j++) {
					data.line.append("\"");
					data.line.append(args.get(j));
					data.line.append("\",");
				}
				data.line.deleteCharAt(data.line.length() - 1);
				data.line.append("]");
			}
			data.line.append("},\"");
		}
			break;
		case (Constants.CHAR_TABSIZE):
			this.tabSize = (Integer) data.format.argMap.get(i);
			break;
		case (Constants.CHAR_TAB):
			int tabs = tabSize;
			if (data.format.argMap.containsKey(i)) {
				tabs = (Integer) data.format.argMap.get(i);
			}
			for (int j = 0; j < tabs; j++) {
				data.line.append(" ");
				data.col++;
			}
			break;
		}
	}

	private class ConverterData {

		Formatter.Output format;

		int col = 0;
		int row = 0;
		boolean brk = false;
		Stack<ListMode> listMode = new Stack<ListMode>();
		Stack<String> alignMode = new Stack<String>();
		StringBuilder line = new StringBuilder();

		ConverterData(Formatter.Output format) {
			this.format = format;
		}
	}
}
