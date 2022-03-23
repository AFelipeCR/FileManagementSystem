
package edu.uptc.so.views;

import edu.uptc.so.fms.Attributes;
import edu.uptc.so.fms.FileType;
import edu.uptc.so.fms.entities.DFT;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DFTTable extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableProces;
	private DefaultTableModel modelo;
	private JLabel lblTitle;
	private JButton jButton;

	public DFTTable(ActionListener listener) {
		buildtable(listener);
	}

	public void buildtable(ActionListener listener) {
		setLayout(new BorderLayout());
		lblTitle = new JLabel("DFT info");
		jButton = new JButton("Arbol");
		jButton.addActionListener(listener);
		add(jButton, BorderLayout.SOUTH);
		add(lblTitle, BorderLayout.NORTH);
		modelo = new DefaultTableModel();
		// "id", "type", "name", "head", "visibility","createdAt","updatedAt",
		// "accessedAt", "size","children"
		String[] headers = { "id", "name", "type", "head", "visibility", "createdAt", "updatedAt", "accessedAt", "size",
				"children" };
		modelo.setColumnIdentifiers(headers);
		tableProces = new JTable(modelo);
		add(new JScrollPane(tableProces), BorderLayout.CENTER);
	}

	public void setInfoTable(DFT root) {
		modelo.setRowCount(0);
		Format format = new SimpleDateFormat("dd/MM/yyyy");

		if (root != null) {
			String[] info = { root.getId() + "", root.getName(), FileType.values()[root.getType()]+ "", root.getHead() + "",
					Attributes.values()[root.getVisibility()] + "", format.format(new Date(root.getCreatedAt())) + "",
					format.format(new Date(root.getUpdatedAt())) + "",
					format.format(new Date(root.getAccessedAt())) + "", root.getSize() + "",
					root.getChildrenList().size() + "" };
			modelo.addRow(info);

			fillWithTree(root.getChildrenList());
		}
	}

	public void fillWithTree(List<DFT> root) {
		Format format = new SimpleDateFormat("dd/MM/yyyy");
		for (int i = 0; i < root.size(); i++) {
			if (root.get(i) != null) {
				// "id", "type", "name", "head", "visibility","createdAt","updatedAt",
				// "accessedAt", "size","children"
				String[] info = { root.get(i).getId() + "", root.get(i).getName(),
						FileType.values()[root.get(i).getType()] + "", root.get(i).getHead() + "",
						Attributes.values()[root.get(i).getVisibility()] + "",
						format.format(new Date(root.get(i).getCreatedAt())) + "",
						format.format(new Date(root.get(i).getUpdatedAt())) + "",
						format.format(new Date(root.get(i).getAccessedAt())) + "", root.get(i).getSize() + "",
						root.get(i).getChildrenList().size() + "" };
				modelo.addRow(info);

				if (!root.isEmpty())
					fillWithTree(root.get(i).getChildrenList());
			}
		}
	}
}
