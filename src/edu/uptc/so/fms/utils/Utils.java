package edu.uptc.so.fms.utils;

import java.nio.ByteBuffer;

import edu.uptc.so.fms.FMSConstants;
import edu.uptc.so.fms.entities.DFT;
import edu.uptc.so.resources.Resources;

public interface Utils extends FMSConstants {
	public static byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}

	public static long bytesToLong(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.put(bytes);
	    buffer.flip();
	    return buffer.getLong();
	}
	
	public static byte[] shortToBytes(short x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
	    buffer.putShort(x);
	    return buffer.array();
	}

	public static short bytesToShort(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(Short.BYTES);
	    buffer.put(bytes);
	    buffer.flip();
	    return buffer.getShort();
	}
	
	public static byte[] stringToBytes(String text, int length) {
		byte[] bs, bytes = new byte[length];

		if (text.length() < length) {
			bs = text.getBytes();
		} else {
			text.substring(0, length).getBytes();
			bs = text.getBytes();
		}

		
		for (int i = 0; i < bs.length; i++) {
			bytes[i] = bs[i];
		}
		
		return bytes;
	}
	
	public static String bytesToString(byte[] bytes, int from, int length) {
		String s = "";
		
		for (int i = from; i < from + length; i++) {
			if(bytes[i] == 0)
				break;
			
			s += (char) bytes[i];
		}
		
		return s;
	}
	
	public static void fillBytes(byte[] bytes, byte[] part, int position) {
		for (int i = position; i < position + part.length; i++) {
			bytes[i] = part[i - position];
		}
	}
	
	public static void loadDFTChildren(DFT dft) {
		DFT[] dfts = new DFT[100];
		short[] ids = DFT.childrenIds(
				Resources.readDisk(FAT_SIZE + DIR_SIZE + dft.getHead() * CLUSTER_SIZE, CLUSTER_SIZE));

		for (int i = 0; i < dfts.length; i++) {
			if (ids[i] != 0)
				dfts[i] = new DFT(Resources.readDisk(FAT_SIZE + ids[i] * DFT_SIZE, DFT_SIZE));
		}

		dft.setChildren(dfts);
	}
	
}
