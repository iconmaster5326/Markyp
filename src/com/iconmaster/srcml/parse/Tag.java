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
	public ArrayList<ArrayList<Tag>> args = new ArrayList<>();
	public HashMap<String,ArrayList<Tag>> namedArgs = new HashMap<>();
	boolean raw = false;

	public Tag(String name) {
		this.name = name;
	}

	public Tag() {}
	
	public void addArg(String arg) {
		ArrayList<Tag> a = new ArrayList<>();
		Tag t = new Tag();
		t.rawValue = arg;
		a.add(t);
		args.add(a);
	}
	
	public void addArg(Tag arg) {
		ArrayList<Tag> a = new ArrayList<>();
		a.add(arg);
		args.add(a);
	}

	@Override
	public String toString() {
		return "Tag{" + "name=" + name + ", rawValue=" + rawValue + ", args=" + args + ", namedArgs=" + namedArgs + (raw ? ", raw" : "") + '}';
	}
	
	
}
