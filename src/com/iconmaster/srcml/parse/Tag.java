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
	public HashMap<String,Tag> namedArgs = new HashMap<>();
	boolean raw = false;

	public Tag(String name) {
		this.name = name;
	}

	public Tag() {}
	
	public void addArg(String arg) {
		Tag t = new Tag();
		t.rawValue = arg;
		
		
	}

	@Override
	public String toString() {
		return "Tag{" + "name=" + name + ", rawValue=" + rawValue + ", args=" + args + ", namedArgs=" + namedArgs + (raw ? ", raw" : "") + '}';
	}
	
	
}
