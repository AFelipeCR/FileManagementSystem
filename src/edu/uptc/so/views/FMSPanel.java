package edu.uptc.so.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import edu.uptc.so.views.console.ConsolePanel;

public class FMSPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final String DFT_TABLE = "table", DFT_TREE = "tree";
        

	public FMSPanel() {
		this.removeAll();
		this.setLayout(new BorderLayout());
		CardLayout cards = new CardLayout();
		
		
		JPanel dftPanel = new JPanel();
		dftPanel.setLayout(cards);
		DFTTablePanel dftTable = new DFTTablePanel((ActionEvent e) -> cards.show(dftPanel, DFT_TREE));
		DFTTreePanel dftTree = new DFTTreePanel((ActionEvent e) -> cards.show(dftPanel, DFT_TABLE));
		dftPanel.add(dftTable, DFT_TABLE);
		dftPanel.add(dftTree, DFT_TREE);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 2));
		FATTablePanel fatTable = new FATTablePanel();
		centerPanel.add(fatTable);
		centerPanel.add(dftPanel);
		
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(new ConsolePanel((Object o) -> { 
			dftTable.paintTable();
			dftTree.paintTree();
			fatTable.paintTable();
		}), BorderLayout.SOUTH);
	}
}