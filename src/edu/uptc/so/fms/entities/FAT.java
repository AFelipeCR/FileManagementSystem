package edu.uptc.so.fms.entities;

public class FAT {
	private short idCount;
	private FATRow[] rows = new FATRow[10];
	
	public int setRow(int index, FATRow row) {
		this.rows[index] = row;
		return this.idCount++;
	}
	
	public FATRow[] getRows() {
		return rows;
	}
}
