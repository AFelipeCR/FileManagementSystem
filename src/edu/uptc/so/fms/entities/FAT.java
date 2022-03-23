package edu.uptc.so.fms.entities;

import edu.uptc.so.fms.FMSConstants;

public class FAT {
	private short idCount;
	private FATRow[] rows;
	
	public FAT(byte[] fatBytes) {
		this.rows = new FATRow[fatBytes.length / FMSConstants.FAT_ROW_SIZE];
		
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
		byte[] bs = new byte[FMSConstants.FAT_SIZE];
		for (int i = 0; i < rows.length; i++) {
			System.arraycopy(rows[i].toBytes(), 0, bs, i * FMSConstants.FAT_ROW_SIZE, FMSConstants.FAT_ROW_SIZE);
		}				
		return bs;
	}
}
