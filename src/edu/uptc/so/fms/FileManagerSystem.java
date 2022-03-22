package edu.uptc.so.fms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.uptc.so.fms.entities.DFT;
import edu.uptc.so.fms.entities.FAT;
import edu.uptc.so.fms.entities.FATRow;
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
	
	public void write(String path, byte[] data){
	}
	
	public void write(String path, FileType type){
		String[] aux = path.split("/");
		DFT dirAux = this.rootDir;
		
		for (int i = 1; i < aux.length - 1; i++) {
			dirAux = dirAux.contains(aux[i]);
		}
		
		this.dirs.put(path, dirAux.add(aux[aux.length - 1], Resources.freeDir()));
	}
	
	public short[] read(String path){
		//Searches for path in dirs if exists set auxHead to head
		ArrayList<Short> blocksIDs = new ArrayList<Short>();
		short auxHead = open(path);
		FATRow aux = getFAT().getRowByID(auxHead);
		//Traverse tree until next is -1
		blocksIDs.add(auxHead);
		short count = 0, maxBlocks = 1000;
		while(aux.getNext() != -1) {
			blocksIDs.add(aux.getId());
			aux = getFAT().getRowByID(aux.getNext());
			count ++;
		}
		//Convert from arrayList to regular Array
		short[] toReturn = new short[blocksIDs.size()];
		for(int i = 0; i < toReturn.length; i++) {
			toReturn[i] = blocksIDs.get(i);
		}
		return toReturn;
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
