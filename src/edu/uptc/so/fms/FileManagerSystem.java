package edu.uptc.so.fms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.uptc.so.fms.entities.DFT;
import edu.uptc.so.fms.entities.FAT;
import test.Resources;

public class FileManagerSystem {
	private static FileManagerSystem fms = null;
	private Map<String, DFT> dirs;
	
	private DFT rootDir;
	
	private FileManagerSystem() {
		this.dirs = new HashMap<String, DFT>();
	}
	
	public void main(String[] args) {
	}
	
	public void overwrite(String path, byte[] data) {
		if(!this.dirs.containsKey(path)) return;
		
		double blocks = Math.ceil(data.length / (double) Constants.CLUSTER_SIZE);
		
		DFT node = this.dirs.get(path);
		FAT fat = getFAT();

		short p = -1;

		for (short i = 0, n = node.getHead(); i < blocks; i++, p=n, n = fat.getRows()[n].getNext()) {
			int from = i * Constants.CLUSTER_SIZE;
			int to = from  + Constants.CLUSTER_SIZE;
			if(to > data.length) to = data.length;
			if(n == 0 || n == -1) {
				n = Resources.freeDir();
				if(p == -1) node.setHead(n);
				else fat.getRows()[p].setNext(n);
			}
			fat.getRows()[n].setStatus((byte) 1);
			Resources.writeDisk(getPositionToWrite(n), Arrays.copyOfRange(data, from, to)); 
		}
		clearBlocks(p, fat);
	}

	public int getPositionToWrite(short block){
		return Constants.RESERVED_SPACE + block * Constants.CLUSTER_SIZE;
	}
	
	private void clearBlocks(short parent, FAT fat) {
		short next = fat.getRows()[parent].getNext();
		for (short i = next; fat.getRows()[i].getStatus() == 1; i = fat.getRows()[i].getNext()) {
			fat.getRows()[i].setStatus((byte) 0);
		}
		fat.getRows()[parent].setNext((short)-1);
		Resources.writeDisk(0, fat.toBytes());
	}

	public void write(String path, FileType type){
		String[] aux = path.split("/");
		DFT dirAux = this.rootDir;
		
		for (int i = 1; i < aux.length - 1; i++) {
			dirAux = dirAux.contains(aux[i]);
		}
		
		this.dirs.put(path, dirAux.add(aux[aux.length - 1], Resources.freeDir()));
	}
	
	public DFT read(String path){
		if(this.dirs.containsKey(path))
			return this.dirs.get(path);

		String[] aux = path.split("/");
		DFT dirAux = this.rootDir;
		
		for (int i = 0; i < aux.length; i++) {
			dirAux = dirAux.contains(aux[i]);
		}
		
		return dirAux;
	}
	
	public short open(String path){
		if(this.dirs.containsKey(path))
			return this.dirs.get(path).getHead();

		String[] aux = path.split("/");
		DFT dirAux = this.rootDir;
		
		for (int i = 0; i < aux.length; i++) {
			dirAux = dirAux.contains(aux[i]);
		}
		
		return dirAux.getHead();
	}
	
	public void  close(String filename, String path){
		
	}
	
	public byte[] getFormat() {
		byte[] bytes = new byte[1000];
		
		return bytes;
	}
	
	public FAT getFAT() {
		byte[] bytes= null;
		bytes = Resources.readDisk(0, Constants.FAT_SIZE);
		return new FAT(bytes);
	}
	
	public static FileManagerSystem getInstance() {
		if(fms == null)
			fms = new FileManagerSystem();
		
		return fms;
	}

	public void format() {
		Resources.format(Constants.DISK_SIZE);
		Resources.formatFAT(Constants.FAT_SIZE);
		this.rootDir = new DFT((short) 0, (byte) FileType.DIR.ordinal(), "/", (byte) 0, System.currentTimeMillis());
		Resources.formatDirectories(Constants.FAT_SIZE,
				this.rootDir.toBytes());
		this.dirs.put("/", this.rootDir);
	}
}
