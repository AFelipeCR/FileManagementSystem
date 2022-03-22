package edu.uptc.so.fms.entities;

import edu.uptc.so.fms.Constants;

public class FAT {
	private short idCount;
	private FATRow[] rows;
	
	public FAT(byte[] fatBytes) {
		this.rows = new FATRow[fatBytes.length / Constants.FAT_ROW_SIZE];
		
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
	
	public FATRow getRowByID(short id) {
		//Traverses all of the rows and if id matches return FATRow element if there´s no match, return null
		boolean found = false;
		int count = 0;
		while(!found && count < rows.length) {
			if(id == rows[count].getId()) {
				return rows[count];
			}
			count++;
		}
		return null;
	}
}
