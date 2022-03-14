package edu.uptc.so.fms;

import edu.uptc.so.fms.entities.DFT;
import edu.uptc.so.fms.entities.FAT;
import edu.uptc.so.fms.entities.FATRow;

public class FileManagerSystem {
	public static void main(String[] args) {
		FAT fat = new FAT();
		System.out.println(fat.setRow(0, new FATRow((byte) 0, (short)0, (short)0)));
		System.out.println(fat.setRow(0, new FATRow((byte) 0, (short)0, (short)0)));
		System.out.println(fat.setRow(0, new FATRow((byte) 0, (short)0, (short)0)));
	}
	
	public static void write(String filename, String path, byte[] data){
		//DFT dft = new DFT(1, filename, 1, 1, 1, 1);
	}
	
	public static void read(String filename, String path){
		
	}
	
	public static void open(String filename, String path){
		
	}
	
	public static void close(String filename, String path){
		
	}
}
