package com.iconmaster.srcml.parse;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author iconmaster
 */
public class Tag {
	public String name = "";
	public String rawValue;
	public ArrayList<ArrayList<Tag>> args = new ArrayList<ArrayList<Tag>>();
	public HashMap<String,ArrayList<Tag>> namedArgs = new HashMap<String,ArrayList<Tag>>();

	public Tag(String name) {
		this.name = name;
	}

	public Tag() {}
	
	public void addArg(String arg) {
		ArrayList<Tag> a = new ArrayList<Tag>();
		a.add(rawTag(arg));
		args.add(a);
	}
	
	public void addArg(Tag arg) {
		ArrayList<Tag> a = new ArrayList<Tag>();
		a.add(arg);
		args.add(a);
	}
	
	public void addNamedArg(String name, String arg) {
		ArrayList<Tag> a = new ArrayList<Tag>();
		a.add(rawTag(arg));
		namedArgs.put(name, a);
	}
	
	public void addNamedArg(String name, Tag arg) {
		ArrayList<Tag> a = new ArrayList<Tag>();
		a.add(arg);
		namedArgs.put(name, a);
	}

	@Override
	public String toString() {
		return "Tag{" + "name=" + name + ", rawValue=" + rawValue + ", args=" + args + ", namedArgs=" + namedArgs + '}';
	}
	
	public static Tag rawTag(String text) {
		Tag t = new Tag();
		t.rawValue = text;
		return t;
	}
	
	public static String rawValue(ArrayList<Tag> arg) {
		StringBuilder sb = new StringBuilder();
		for (Tag t : arg) {
			if (t.rawValue!=null) {
				sb.append(t.rawValue);
			}
			for (ArrayList<Tag> arg2 : t.args) {
				sb.append(rawValue(arg2));
			}
		}
		return sb.toString();
	}
}
