
package test;

import edu.uptc.so.fms.entities.DFT;
import edu.uptc.so.fms.entities.FATRow;
import edu.uptc.so.views.JpCard;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class testView extends JFrame{

    public testView(DFT df, FATRow[] fat){
        setTitle("view files");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        
        setLayout(new BorderLayout());
        JpCard panel = new JpCard(df, fat);
        add(panel, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
//    public static void main(String[] args) {
//        byte[] b = new byte[3];
//        b[0] = 15;
//        b[1] = 15;
//        b[2] = 15;
//        //DFT df = new DFT(b);
//        FATRow[] fat = new FATRow[2];
//        new testView(null, fat);
//    }
}
