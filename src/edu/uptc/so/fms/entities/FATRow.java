package edu.uptc.so.fms.entities;

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

	public void setId(short id) {
		this.id = id;
	}

	public short getNext() {
		return next;
	}

	public void setNext(short next) {
		this.next = next;
	}
	
	public  String toText(){
		return id + " " + status + " " + next;
		
	}
}
