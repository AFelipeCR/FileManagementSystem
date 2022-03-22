package edu.uptc.so.fms;

public class Constants {
	public static final int DFT_SIZE = 43;
	public static final int FAT_ROW_SIZE = 5;
	public static final int FAT_SIZE = FAT_ROW_SIZE * 1000;
	public static final int DIR_SIZE = DFT_SIZE * 1000;
	public static final int DISK_SIZE = FAT_SIZE + DIR_SIZE + 10240;
	public static final int CLUSTER_SIZE = 512;
}
