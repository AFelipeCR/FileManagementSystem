package edu.uptc.so.fms.entities;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import edu.uptc.so.fms.Attributes;
import edu.uptc.so.fms.Constants;
import edu.uptc.so.fms.FileType;
import edu.uptc.so.fms.utils.Utils;

public class DFT {
	private short id;
	private byte type;
	private String name;
	private short head;
	private byte visibility;
	private long createdAt;
	private long updatedAt;
	private long accessedAt;
	private short size;
	private DFT[] children;

	public DFT(byte[] dftBytes) {
		this.id = Utils.bytesToShort(Arrays.copyOfRange(dftBytes, 0, 2));
		this.type = dftBytes[2];
		this.name = Utils.bytesToString(Arrays.copyOfRange(dftBytes, 0, 11), 3, 11);
		this.head = Utils.bytesToShort(Arrays.copyOfRange(dftBytes, 14, 16));
		this.visibility = dftBytes[16];
		this.createdAt = Utils.bytesToLong(Arrays.copyOfRange(dftBytes, 17, 25));
		this.updatedAt = Utils.bytesToLong(Arrays.copyOfRange(dftBytes, 25, 33));
		this.accessedAt = Utils.bytesToLong(Arrays.copyOfRange(dftBytes, 33, 41));
		this.size = Utils.bytesToShort(Arrays.copyOfRange(dftBytes, 41, 43));
		
		if(this.type == FileType.DIR.ordinal())
			this.children = new DFT[100];
	}
	
	public DFT(short id, FileType type, String name, short head, byte visibility, long createdAt) {
		this.id = id;
		this.type = (byte) type.ordinal();
		this.name = name;
		this.head = head;
		this.visibility = visibility;
		this.createdAt = this.updatedAt = this.accessedAt = createdAt;
		
		if(type == FileType.DIR)
			this.children = new DFT[100];
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

	public String toText() {
		Format format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return this.id + " name: " + this.name + " " + FileType.values()[this.type] + " "
				+ Attributes.values()[this.visibility] + " " + format.format(new Date(this.createdAt)) + " "
				+ format.format(new Date(this.updatedAt));
	}

	public byte[] toBytes() {
		byte[] bytes = new byte[Constants.DFT_SIZE];
		Utils.fillBytes(bytes, Utils.shortToBytes(this.id), 0);
		bytes[2] = this.type;
		Utils.fillBytes(bytes, Utils.stringToBytes(this.name, 11), 3);
		Utils.fillBytes(bytes, Utils.shortToBytes(this.head), 14);
		bytes[16] = this.visibility;
		Utils.fillBytes(bytes, Utils.longToBytes(this.createdAt), 17);
		Utils.fillBytes(bytes, Utils.longToBytes(this.updatedAt), 25);
		Utils.fillBytes(bytes, Utils.longToBytes(this.accessedAt), 33);
		Utils.fillBytes(bytes, Utils.shortToBytes(this.size), 41);

		return bytes;
	}

	public DFT contains(String filename) {
		DFT d = null;

		for (DFT s : this.children) {
			if (s != null && s.name.contentEquals(filename)) {
				d = s;
				break;
			}
		}

		return d;
	}

	public DFT add(String path, short id, FileType type, short head) {
		int i = 0;

		DFT aux = this.children[i];
		i++;
		
		while (aux != null && i < this.children.length) {
			aux = this.children[i];
			i++;
		}
		
		return this.children[i - 1] = new DFT(id, type, path, head, (byte) Attributes.NORMAL.ordinal(),
				System.currentTimeMillis());
	}

	public short getId() {
		return id;
	}

	public byte getType() {
		return type;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public long getAccessedAt() {
		return accessedAt;
	}

	public short getSize() {
		return size;
	}

	public DFT[] getChildren() {
		return children;
	}

	public String longListing() {
		String s = "";
		
		for (DFT c : children) {
			if(c != null)
				s += c.name+ " ";
		}
		
		return s;
	}
	
	public DFT[] getChildrenDfts() {
		return children;
	}
	
	public void setChildren(DFT[] children) {
		this.children = children;
	}

	public static short[] childrenIds(byte[] childrenBytes) {
		short[] ids = new short[100];
		for (int i = 0, j = 0; i < childrenBytes.length && j < ids.length ; i+=2, j++) {
			ids[j] = Utils.bytesToShort(Arrays.copyOfRange(childrenBytes, i, i + 2));
		}
		
		return ids;
	}
}