package edu.uptc.so.fms.entities;

public class DFT {
	private short id;
	private String name;
	private short head;
	private byte visibility;
	private short createdAt;
	private short updatedAt;
	private short accessedAt;
	private short[] children;
	
	public DFT(short id, String name, short head, byte visibility,
			short createdAt, short updatedAt, short accessedAt, short[] children) {
		this.id = id;
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
		this.id = id;
		this.name = name;
		this.head = head;
		this.visibility = visibility;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.accessedAt = accessedAt;
	}
	
	public DFT(short id, String name, byte visibility,
			short createdAt, short updatedAt, short accessedAt) {
		this.id = id;
		this.name = name;
		this.visibility = visibility;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.accessedAt = accessedAt;
	}
	
	public short getId() {
		return id;
	}
	
	public void setId(short id) {
		this.id = id;
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
}