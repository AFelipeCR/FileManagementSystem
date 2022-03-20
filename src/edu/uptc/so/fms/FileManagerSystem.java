package edu.uptc.so.fms;

import edu.uptc.so.fms.entities.DFT;
import edu.uptc.so.fms.entities.FAT;
import test.Resources;

public class FileManagerSystem {
	private static DFT rootDir;
	public static void main(String[] args) {
	}
	
	public static void write(String path, byte[] data){
		//DFT dft = new DFT(1, filename, 1, 1, 1, 1);
	}
	
	public static void write(String path){
		//DFT dft = new DFT(1, filename, 1, 1, 1, 1);
	}
	
	public static void read(String path){
		if(rootDir == null)
			rootDir = new DFT(Resources.readDisk(Constants.FAT_SIZE, 20));
	}
	
	public static void open(String filename, String path){
		
	}
	
	public static void close(String filename, String path){
		
	}
	
	public static byte[] getFormat() {
		byte[] bytes = new byte[1000];
		
		return bytes;
	}
	
	public static FAT getFAT() {
		byte[] bytes= null;
		bytes = Resources.readDisk(0, Constants.FAT_SIZE);
		return new FAT(bytes);
	}
}
