package edu.uptc.so.views;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.uptc.so.fms.entities.DFT;
import edu.uptc.so.fms.entities.FATRow;

public class JpCard extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private CardLayout cards = new CardLayout( );
        private JPanel panelRight;
        

	public JpCard(DFT root, FATRow[] row) {
		removeAll();
		setLayout(new GridLayout(1, 2));
		
		FATTable fattable = new FATTable(this);
		fattable.setInfoTable(row);
		add(fattable);
		
		panelRight = new JPanel();
		panelRight.setLayout(null);
		panelRight.setLayout(cards);
		
		DFTTable dftTable = new DFTTable(this);
		dftTable.setInfoTable(root);
		panelRight.add(dftTable, BorderLayout.CENTER);
		JpTree jpTree = new JpTree(root, this);
		panelRight.add(jpTree);
		add(panelRight);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cards.next(panelRight);
	}
}