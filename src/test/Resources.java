package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Resources {
	private static final File disk = new File("./disk.txt");
	

	public static byte[] readDisk(int position, int bufferSize) {
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
		System.out.println("Escritura en: " + position + ", bytes:" + buffer.length);
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

	public static short freeDir() {
		return (short) (new Random().nextInt(99) + 1);
	}
	
	public static short freeSpace(int size, int from, int to, Short s) {
		try {
			RandomAccessFile raf = new RandomAccessFile(disk, "r");
			byte[] bytes = new byte[2];
			short ret = -1;
			
			for (int i = from; i < to; i += size) {
				raf.seek(i);
				raf.read(bytes);
				
				if(bytes[0] == 0 && bytes[1] == 0) {
					ret = (short) ((i - from) / size);
					break;
				}
			}
			
			raf.close();
			return ret;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
}
