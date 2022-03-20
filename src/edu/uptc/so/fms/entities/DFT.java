package edu.uptc.so.fms.entities;

import java.util.Arrays;

public class DFT {
	private String name;
	private short head;
	private byte visibility;
	private short createdAt;
	private short updatedAt;
	private short accessedAt;
	private short[] children;
	
	public DFT(byte[] dftBytes) {
		byte[] aux = Arrays.copyOfRange(dftBytes, 0, 11);
		int i = 0;
		String s = "";
		
		while(aux[i] != 0) {
			s += (char) aux[i];
			i++;
		}
		
		this.name = s;
		this.head = (short)(((dftBytes[11] & 0xFF) << 8) | (dftBytes[12] & 0xFF));
		this.visibility = dftBytes[13];
		this.createdAt = (short)(((dftBytes[14] & 0xFF) << 8) | (dftBytes[15] & 0xFF));;
		this.updatedAt = (short)(((dftBytes[16] & 0xFF) << 8) | (dftBytes[17] & 0xFF));;
		this.accessedAt = (short)(((dftBytes[18] & 0xFF) << 8) | (dftBytes[19] & 0xFF));
	}
	
	public DFT(short id, String name, short head, byte visibility,
			short createdAt, short updatedAt, short accessedAt, short[] children) {
		this.name = name;
		this.head = head;
		this.visibility = visibility;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.accessedAt = accessedAt;
		this.children = children;
	}
	
	public DFT(short id, String name, byte visibility,
			short createdAt, short updatedAt, short accessedAt, short head) {
		this.name = name;
		this.head = head;
		this.visibility = visibility;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.accessedAt = accessedAt;
	}
	
	public DFT(short id, String name, byte visibility,
			short createdAt, short updatedAt, short accessedAt) {
		this.name = name;
		this.visibility = visibility;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.accessedAt = accessedAt;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public short getHead() {
		return head;
	}
	
	public void setHead(short head) {
		this.head = head;
	}
	
	public byte getVisibility() {
		return visibility;
	}
	
	public void setVisibility(byte visibility) {
		this.visibility = visibility;
	}
	
	public short getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(short createdAt) {
		this.createdAt = createdAt;
	}
	
	public short getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(short updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public short getAccessedAt() {
		return accessedAt;
	}
	
	public void setAccessedAt(short accessedAt) {
		this.accessedAt = accessedAt;
	}
	
	public short[] getChildren() {
		return children;
	}
	
	public void setChildren(short[] children) {
		this.children = children;
	}
	
	public String toText() {
		return this.name + " " + this.head;
	}

	public byte[] toBytes() {
		byte[] bytes = new byte[20];
		byte[] bs;
		
		if(this.name.length() < 11) {
			bs = this.name.getBytes();
		}else {
			this.name.substring(0, 11).getBytes();
			bs = this.name.getBytes();
		}
		
		for (int i = 0; i < bs.length; i++) {
			bytes[i] = bs[i];
		}
		
		bytes[11] = (byte)(this.head & 0xff);
		bytes[12] = (byte)((this.head >> 8) & 0xff);
		bytes[13] = this.visibility;
		bytes[14] = (byte)(this.createdAt & 0xff);
		bytes[15] = (byte)((this.createdAt >> 8) & 0xff);
		bytes[16] = (byte)(this.updatedAt & 0xff);
		bytes[17] = (byte)((this.updatedAt >> 8) & 0xff);
		bytes[18] = (byte)(this.accessedAt & 0xff);
		bytes[19] = (byte)((this.accessedAt >> 8) & 0xff);
		
		return bytes;
	}
}