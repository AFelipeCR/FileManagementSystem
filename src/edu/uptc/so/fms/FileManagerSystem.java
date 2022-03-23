package edu.uptc.so.fms;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.uptc.so.fms.entities.DFT;
import edu.uptc.so.fms.entities.FAT;
import edu.uptc.so.fms.entities.FATRow;
import edu.uptc.so.fms.utils.Utils;
import edu.uptc.so.resources.Resources;

public class FileManagerSystem implements FMSConstants {
	private static FileManagerSystem fms = null;
	private FAT fat;
	private Map<String, DFT> dirs;

	private DFT rootDir;

	private FileManagerSystem() {
		this.fat = new FAT(Resources.readDisk(0, FAT_SIZE));
		this.dirs = new HashMap<String, DFT>();
	}

	public void overwrite(String path, byte[] data) {
		if (!this.dirs.containsKey(path))
			return;

		double blocks = Math.ceil(data.length / (double) CLUSTER_SIZE);
		DFT node = this.dirs.get(path);

		short p = -1;

		for (short i = 0, n = node.getHead(); i < blocks; i++, p = n, n = this.fat.getRows()[n].getNext()) {
			int from = i * CLUSTER_SIZE;
			int to = from + CLUSTER_SIZE;

			if (to > data.length)
				to = data.length;

			if (n == 0 || n == -1) {
				n = this.fat.freeRow().getId();

				if (p == -1)
					node.setHead(n);
				else
					this.fat.getRows()[p].setNext(n);
			}
			
			this.fat.getRows()[n].setStatus((byte) 1);
			Resources.writeDisk(getPositionToWrite(n), Arrays.copyOfRange(data, from, to));
		}

		clearBlocks(p, this.fat);
	}

	public int getPositionToWrite(short block) {
		return RESERVED_SPACE + block * CLUSTER_SIZE;
	}

	private void clearBlocks(short parent, FAT fat) {
		if (parent == -1)
			return;
		
		short next = fat.getRows()[parent].getNext();

		for (short i = next; i != -1 && fat.getRows()[i].getStatus() == 1; i = fat.getRows()[i].getNext()) {
			fat.getRows()[i].setStatus((byte) 0);
		}

		fat.getRows()[parent].setNext((short) -1);
		Resources.writeDisk(0, fat.toBytes());
	}

	/**
	 * Crear archivo
	 * 
	 * @param path
	 * @param type
	 */
	public void write(String path, FileType type) {
		String[] aux = path.split("/");
		
		String[] pathArray = path.split("/");
		
		DFT dft = this.searchDFT(this.rootDir, Arrays.copyOfRange(pathArray, 0, pathArray.length - 1));

		if(dft == null)
			return;
		
		FATRow head = this.fat.freeRow();

		DFT currentDFT = dft.add(aux[aux.length - 1],
				Resources.freeSpace(DFT_SIZE, FAT_SIZE, FAT_SIZE + DIR_SIZE, (short) 0), type, head.getId());
		this.dirs.put(path, currentDFT);
		head.setStatus((byte) 1);
		head.setNext((short) -1);
		Resources.writeDisk(head.getId() * FAT_ROW_SIZE, head.toBytes());
		Resources.writeDisk(FAT_SIZE + currentDFT.getId() * DFT_SIZE, currentDFT.toBytes());
		int pos = FAT_SIZE + DIR_SIZE + dft.getHead() * CLUSTER_SIZE;
		Resources.writeDisk(pos + Resources.freeSpace(2, pos, pos + CLUSTER_SIZE, (short) 0) * 2,
				Utils.shortToBytes(currentDFT.getId()));
	}

	public DFT readDFT(String path) {
		if (this.dirs.containsKey(path))
			return this.dirs.get(path);
		
		DFT dft = this.searchDFT(this.rootDir, path.split("/"));
		
		if(dft != null)
			 this.dirs.put(path, dft);
		
		return dft;
	}
	
	public DFT searchDFT(DFT dft, String[] path, int index) {
		if(path.length == 1)
			return dft;
		
		if(index >= path.length)
			return null;
		
		for (DFT d : dft.getChildrenList()) {
			
			if(d.getName().contentEquals(path[index]))
				if(index == path.length - 1)
					return d;
				else
					return this.searchDFT(d, path, index + 1);
		}
		
		return null;
	}

	public DFT searchDFT(DFT dft, String[] path) {
		return this.searchDFT(dft, path, 1);
	}
	
	public short[] read(String path){
			//Searches for path in dirs if exists set auxHead to head
			ArrayList<Short> blocksIDs = new ArrayList<Short>();
			short auxHead = this.open(path);
			FATRow aux = this.fat.getRowByID(auxHead);
			//Traverse tree until next is -1
			blocksIDs.add(auxHead);
			
			while(aux.getNext() != -1) {
				blocksIDs.add(aux.getId());
				aux = this.fat.getRowByID(aux.getNext());
			}
			
			//Convert from arrayList to regular Array
			short[] toReturn = new short[blocksIDs.size()];
			
			for(int i = 0; i < toReturn.length; i++) {
				toReturn[i] = blocksIDs.get(i);
			}
			
			return toReturn;
	}

	public short open(String path) {
		if (this.dirs.containsKey(path))
			return this.dirs.get(path).getHead();

		String[] aux = path.split("/");
		DFT dirAux = this.rootDir;

		for (int i = 0; i < aux.length; i++) {
			dirAux = dirAux.contains(aux[i]);
		}

		return dirAux.getHead();
	}

	public void close(String filename, String path) {

	}

	public static FileManagerSystem getInstance() {
		if (fms == null)
			fms = new FileManagerSystem();

		return fms;
	}

	public FAT getFAT() {
		return fat;
	}

	public void format() {
		Resources.writeDisk(0, new byte[DISK_SIZE]);
		this.fat = new FAT(Resources.readDisk(0, FAT_SIZE));
		FATRow head = this.fat.freeRow();
		head.setStatus((byte) 1);
		head.setNext((short) -1);
		DFT dft = new DFT((short) 1, FileType.DIR, "/", head.getId(), (byte) 0, System.currentTimeMillis());
		Resources.writeDisk(head.getId() * FAT_ROW_SIZE, head.toBytes());
		Resources.writeDisk(FAT_SIZE, dft.toBytes());
		this.rootDir = dft;
	}

	public void init() {
		this.rootDir = new DFT(Resources.readDisk(FAT_SIZE, DFT_SIZE));
		DFT[] dfts = new DFT[100];
		short[] ids = DFT.childrenIds(
				Resources.readDisk(FAT_SIZE + DIR_SIZE + this.rootDir.getHead() * CLUSTER_SIZE, CLUSTER_SIZE));

		for (int i = 0; i < dfts.length; i++) {
			if (ids[i] != 0)
				dfts[i] = new DFT(Resources.readDisk(FAT_SIZE + ids[i] * DFT_SIZE, DFT_SIZE));
		}

		this.rootDir.setChildren(dfts);
		this.dirs.put("/", this.rootDir);
	}
}
