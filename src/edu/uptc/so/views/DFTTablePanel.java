
package edu.uptc.so.views;

import edu.uptc.so.fms.Attributes;
import edu.uptc.so.fms.FileManagerSystem;
import edu.uptc.so.fms.FileType;
import edu.uptc.so.fms.entities.DFT;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DFTTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableProces;
	private DefaultTableModel model;
	private JButton jButton;

	public DFTTablePanel(ActionListener listener) {
		buildTable(listener);
	}

	public void buildTable(ActionListener listener) {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("DFT Table"));
		jButton = new JButton("Arbol");
		jButton.addActionListener(listener);
		add(jButton, BorderLayout.NORTH);
		model = new DefaultTableModel();
		// "id", "type", "name", "head", "visibility","createdAt","updatedAt",
		// "accessedAt", "size","children"
		String[] headers = { "id", "name", "type", "head", "visibility", "createdAt", "updatedAt", "accessedAt", "size",
				"children" };
		model.setColumnIdentifiers(headers);
		tableProces = new JTable(model);
		add(new JScrollPane(tableProces), BorderLayout.CENTER);
		
		this.paintTable();
	}

	public void paintTable() {
		model.setRowCount(0);
		this.fillWithTree(FileManagerSystem.getInstance().getRoot());
	}

	public void fillWithTree(DFT dft) {
		Format format = new SimpleDateFormat("dd/MM/yyyy");
		
		String[] info = { dft.getId() + "", dft.getName(),
				FileType.values()[dft.getType()] + "", dft.getHead() + "",
				Attributes.values()[dft.getVisibility()] + "",
				format.format(new Date(dft.getCreatedAt())) + "",
				format.format(new Date(dft.getUpdatedAt())) + "",
				format.format(new Date(dft.getAccessedAt())) + "", dft.getSize() + "",
				dft.getChildrenList() == null ? "" : dft.getChildrenList().size() + "" };
		model.addRow(info);
		
		
		if(dft.getType() == FileType.DIR.ordinal()) {
			List<DFT> children = dft.getChildrenList();
			
			for (int i = 0; i < children.size(); i++) {
				this.fillWithTree(children.get(i));
			}
		}
	}
}
