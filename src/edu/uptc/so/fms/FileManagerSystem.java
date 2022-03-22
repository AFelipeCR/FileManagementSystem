package edu.uptc.so.fms;

import java.util.HashMap;
import java.util.Map;

import edu.uptc.so.fms.entities.DFT;
import edu.uptc.so.fms.entities.FAT;
import edu.uptc.so.fms.entities.FATRow;
import test.Resources;

public class FileManagerSystem {
	private static FileManagerSystem fms = null;
	private FAT fat;
	private Map<String, DFT> dirs;

	private DFT rootDir;

	private FileManagerSystem() {
		this.fat = new FAT(Resources.readDisk(0, Constants.FAT_SIZE));
		this.dirs = new HashMap<String, DFT>();
	}

	public void main(String[] args) {
	}

	/**
	 * Sobreescribir archivo
	 * 
	 * @param path
	 * @param data
	 */
	public void write(String path, byte[] data) {
	}

	/**
	 * Crear archivo
	 * 
	 * @param path
	 * @param type
	 */
	public void write(String path, FileType type) {
		String[] aux = path.split("/");
		DFT dirAux = this.rootDir;

		for (int i = 1; i < aux.length - 1; i++) {
			dirAux = dirAux.contains(aux[i]);
		}

		if (type == FileType.FILE) {
			FATRow head = this.fat.freeRow();
			this.dirs.put(path, dirAux.add(aux[aux.length - 1], Resources.freeDir(), type, head.getId()));
			head.setStatus((byte) 1);
			head.setNext((short) -1);
			Resources.writeDisk(head.getId() * Constants.FAT_ROW_SIZE, head.toBytes());
		} else {
			this.dirs.put(path, dirAux.add(aux[aux.length - 1], Resources.freeDir(), type));
		}
	}

	public DFT read(String path) {
		if (this.dirs.containsKey(path))
			return this.dirs.get(path);

		String[] aux = path.split("/");
		DFT dirAux = this.rootDir;

		for (int i = 0; i < aux.length; i++) {
			dirAux = dirAux.contains(aux[i]);
		}

		return dirAux;
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
		Resources.format(Constants.DISK_SIZE);
		Resources.formatFAT(Constants.FAT_SIZE);
		Resources.formatDirectories(Constants.FAT_SIZE,
				new DFT((short) 0, FileType.DIR, "/", (byte) 0, System.currentTimeMillis()).toBytes());
	}

	public void init() {
		this.rootDir = new DFT(Resources.readDisk(Constants.FAT_SIZE, Constants.DFT_SIZE));
		this.dirs.put("/", this.rootDir);
	}
}
