package edu.uptc.so.fms.console;

import edu.uptc.so.fms.FileManagerSystem;
import edu.uptc.so.fms.FileType;
import edu.uptc.so.fms.utils.TestUtils;

public class FMSConsole {

	private String dir = "/";

	public String evaluate(String command) {
		if(command.isBlank()) return dir;
		command = command.trim();
		
		String[] line = command.split(" ");
		
		switch (line[0]) {
		
		case "list": {
			System.out.println(TestUtils.childrenString(FileManagerSystem.getInstance().readDFT(this.dir)));;
			break;
		}
		case "cd": {
			this.dir = (line.length == 1 ? "/": this.dir + line[1]) + "/";
			break;
		}
		case "cd..": {
			String[] route = this.dir.split("/");
			if(route.length == 0) 
				break;
			route[route.length - 1] = "";
			this.dir = String.join("/",route);
			break;
		}
		case "mkdir": {
			FileManagerSystem.getInstance().write(this.dir + line[1] + "/", FileType.DIR);
			break;
		} 
		case "touch": {
			FileManagerSystem.getInstance().write(this.dir + line[1], FileType.FILE);
			break;
		}
		case "nano": {
			// read and write, wait ctrl x
			break;
		}
		default:
			System.out.println("No existe comando " + line[0]);
		}
		return dir;
	}
}
