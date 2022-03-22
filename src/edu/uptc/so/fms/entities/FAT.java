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


	public FATRow freeRow() {
		for (FATRow fatRow : rows) {
			if(fatRow.getStatus() == 0)
				return fatRow;
		}
		
		return null;
}

	public byte[] toBytes() {
		byte[] bs = new byte[Constants.FAT_SIZE];
		
		return bs;
	}
}
