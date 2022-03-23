package edu.uptc.so.fms.utils;
import edu.uptc.so.fms.entities.DFT;

public interface TestUtils {
	public static String childrenString(DFT dft){
		String s = "";
		
		for(DFT d: dft.getChildrenList()) {
			s += d.getName() + (d.getType() == 0 ? " ": "/ ");
		}
		
		return s;
	}
}
