package edu.uptc.so.fms.console;

import java.util.Scanner;

import edu.uptc.so.fms.FileManagerSystem;

public class FMSConsole implements Runnable {
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
		System.out.print("fms> ");
		
		String[] line = sc.nextLine().split(" ");
		
		switch (line[0]) {
		case "ls": {
			System.out.println(FileManagerSystem.getInstance().read(line[1]).longListing());
			break;
		}
		default:
			System.out.println("No existe comando " + line[0]);
		}
	}
}
