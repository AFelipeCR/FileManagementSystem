package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

import edu.uptc.so.fms.Constants;

public class Resources {
	

	public static byte[] readDisk(int position, int bufferSize) {
		File disk = new File("./disk.txt");
		

		try {
			RandomAccessFile raf = new RandomAccessFile(disk, "r");
			byte[] bytes = new byte[bufferSize];
			raf.seek(position);
			raf.read(bytes);
			raf.close();
			return bytes;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public static byte[] writeDisk(int position, byte[] buffer) {
		File disk = new File("./disk.txt");
		

		try {
			RandomAccessFile raf = new RandomAccessFile(disk, "rw");
			raf.seek(position);
			raf.write(buffer);
			raf.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
		
		return null;
	}
	
	
	public static void format(int size) {
		File disk = new File("./disk.txt");
		
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(disk));
			byte[] bs = new byte[size];
			dos.write(bs);
			dos.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	public static void formatFAT(int size) {
		File disk = new File("./disk.txt");
		
		try {
			byte[] bs = new byte[size];
			RandomAccessFile raf = new RandomAccessFile(disk, "rw");
			raf.write(bs);
			raf.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	public static void formatDirectories(int fatSize, byte[] buffer) {
		File disk = new File("./disk.txt");
		
		try {
			RandomAccessFile raf = new RandomAccessFile(disk, "rw");
			raf.seek(fatSize);
			raf.write(buffer);
			raf.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	public static short freeDir() {
		return (short) (new Random().nextInt(99) + 1);
	}
}
