package edu.uptc.so.filemanagementsystem.console;

public enum Commands {
	DIR("dir");
	
	private String name;
	
	private Commands(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
