package edu.uptc.so.fms.console;

import java.util.Scanner;

import edu.uptc.so.fms.FileManagerSystem;
import edu.uptc.so.fms.utils.TestUtils;

public class FMSConsole implements Runnable {
	private String dir = "/";
	private Thread t;
	private Scanner sc;

	public FMSConsole() {
		this.sc = new Scanner(System.in);
		this.t = new Thread(this);
	}

	@Override
	public void run() {
		while(true) {
			this.menu();
		}
	}

	public void start() {
		t.start();
	}

	public void menu() {
		System.out.print("fms#" + this.dir + ">");
		
		String[] line = sc.nextLine().split(" ");
		
		switch (line[0]) {
		case "list": {
			System.out.println(TestUtils.childrenString(FileManagerSystem.getInstance().readDFT(this.dir)));;
			break;
		}
		case "cd":{
			this.dir = this.dir + (line.length == 1 ? "": line[1]) + "/";
			break;
		}
		case "":
			break;
		default:
			System.out.println("No existe comando " + line[0]);
		}
	}
}
