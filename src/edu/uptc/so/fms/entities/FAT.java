package edu.uptc.so.fms.entities;

public class FAT {
	private short idCount;
	private FATRow[] rows;
	
	public FAT(byte[] fatBytes) {
		this.rows = new FATRow[fatBytes.length / 5];
		
		for (int i = 0, j = 0; i < this.rows.length; i++, j+=3) {
			short next = (short)(((fatBytes[ j + 1] & 0xFF) << 8) | (fatBytes[j + 2] & 0xFF));
			this.rows[i] = new FATRow(fatBytes[j], (short) i, next);
		}
	}
	
	public int setRow(int index, FATRow row) {
		this.rows[index] = row;
		return this.idCount++;
	}
	
	public FATRow[] getRows() {
		return rows;
	}
}
