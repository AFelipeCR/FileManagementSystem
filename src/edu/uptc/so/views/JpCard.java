package edu.uptc.so.views;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.uptc.so.fms.entities.DFT;

public class JpCard extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private CardLayout cards = new CardLayout( );

	public JpCard(DFT root) {
		setLayout(null);
		setLayout(cards);
		
		
		JpTree jpTree = new JpTree(root, this);
		add(jpTree);
		
		DFTTable dftTable = new DFTTable(this);
		dftTable.setInfoTable(root);
		add(dftTable);
		
		
	}
	
//	public static void main(String[] args) {
//		JFrame f = new JFrame("Card");
//		f.setSize(700, 600);
//		f.setResizable(false);
//		f.setLocationRelativeTo(null);
//		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
//		
//		DFT dft = new DFT((short)1, (byte)1, "hola", (byte)1, 21032022);
//		dft.add("carepeta 1", (short)2);
//		dft.add("carepeta 2", (short)3);
//		dft.add("carepeta 3", (short)4);
//		
//		f.setContentPane(new JpCard(dft));
//		f.setVisible(true);
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cards.next(JpCard.this);
		
	}
}