package edu.uptc.so.fms.entities;

import edu.uptc.so.fms.utils.Utils;

public class FATRow {
	private byte status;
	private short id;
	private short next;
	
	public FATRow(byte status, short id, short next) {
		this.status = status;
		this.id = id;
		this.next = next;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public short getId() {
		return id;
	}

	public short getNext() {
		return next;
	}

	public void setNext(short next) {
		this.next = next;
	}
	
	public  String toText(){
		return status + " " + id + " " + next;
		
	}

	public byte[] toBytes() {
		byte[] bs = new byte[3];
		bs[0] = this.status;
		Utils.fillBytes(bs, Utils.shortToBytes(this.next), 1);
		
		return bs;
	}
}
