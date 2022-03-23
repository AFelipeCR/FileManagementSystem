


import edu.uptc.so.fms.FileManagerSystem;
import edu.uptc.so.fms.entities.DFT;
import edu.uptc.so.fms.entities.FATRow;
import edu.uptc.so.views.JpCard;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class TestView extends JFrame{
	private static final long serialVersionUID = 1L;

	public TestView(DFT df, FATRow[] fat){
        setTitle("FMS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        
        setLayout(new BorderLayout());
        JpCard panel = new JpCard(df, fat);
        add(panel, BorderLayout.CENTER);
        
        setVisible(true);
    }
	
	public static void main(String[] args) {
		FileManagerSystem.getInstance().init();
		new TestView(FileManagerSystem.getInstance().readDFT("/"), FileManagerSystem.getInstance().getFAT().getRows());
	}
    
//    public static void main(String[] args) {
//        byte[] b = new byte[3];
//        b[0] = 15;
//        b[1] = 15;
//        b[2] = 15;
//        //DFT df = new DFT(b);
//        FATRow[] fat = new FATRow[2];
//        
//        DFT dft = new DFT((short)1, FileType.DIR, "root", (short)2 ,(byte)1, System.currentTimeMillis());
//		dft.add("carpeta 1", (short)2, FileType.DIR, (short)1);
//		dft.add("carpeta 2", (short)3, FileType.DIR, (short)2);
//		dft.add("carpeta 3", (short)4, FileType.DIR, (short)3);
//		dft.add("carpeta 4", (short)5, FileType.DIR, (short)1);
//		
//        new testView(dft, fat);
//    }
}
