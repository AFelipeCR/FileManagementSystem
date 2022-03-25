package edu.uptc.so.fms;

public interface FMSConstants {
	public static final int DFT_SIZE = 43;
	public static final int FAT_ROW_SIZE = 3;
	public static final int BLOCKS = 1000;
	public static final int CLUSTER_SIZE = 512;
	public static final int FAT_SIZE = FAT_ROW_SIZE * BLOCKS;
	public static final int DIR_SIZE = DFT_SIZE * BLOCKS;
	public static final int RESERVED_SPACE = FAT_SIZE + DIR_SIZE;
	public static final int DISK_SIZE = RESERVED_SPACE + BLOCKS * CLUSTER_SIZE;
}
