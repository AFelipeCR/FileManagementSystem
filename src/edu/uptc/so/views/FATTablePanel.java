
package edu.uptc.so.views;

import edu.uptc.so.fms.FileManagerSystem;
import edu.uptc.so.fms.entities.FATRow;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FATTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableProcess;
	private DefaultTableModel model;

	public FATTablePanel() {
		createTable();
	}

	private void createTable() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("FAT Table"));
		this.model = new DefaultTableModel();
		String[] headers = { "Status", "id", "next" };
		this.model.setColumnIdentifiers(headers);
		this.tableProcess = new JTable(model);
		this.add(new JScrollPane(tableProcess), BorderLayout.CENTER);
		
		this.paintTable();
	}

	public void paintTable() {
		this.model.setRowCount(0);
		this.fillWithList();
	}

	public void fillWithList() {
		FATRow[] rows = FileManagerSystem.getInstance().getFAT().getRows();

		for (int i = 0; i < rows.length; i++) {
			if (rows[i] != null) {
				String[] info = { rows[i].getStatus() + "", rows[i].getId() + "", rows[i].getNext() + "" };

				this.model.addRow(info);
			}
		}
	}

}
