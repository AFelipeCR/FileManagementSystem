
package edu.uptc.so.views;

import edu.uptc.so.fms.entities.FATRow;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FATTable extends JPanel{
    
    private static final long serialVersionUID = 1L;
    private JTable tableProces;
    private DefaultTableModel modelo;
    private JLabel lblTitle;
    private JButton jButton;

    public FATTable(ActionListener listener) {
        createTable(listener);
    }

    private void createTable(ActionListener listener) {
         setLayout(new BorderLayout());
        lblTitle = new JLabel("DFT info");
        jButton = new JButton("Arbol");
		jButton.addActionListener(listener);
		add(jButton, BorderLayout.SOUTH);
        add(lblTitle, BorderLayout.NORTH);
        modelo = new DefaultTableModel();
        //"id", "type", "name", "head", "visibility","createdAt","updatedAt", "accessedAt", "size","children"
        String[] headers = {"Status", "id", "next"};
        modelo.setColumnIdentifiers(headers);
        tableProces = new JTable(modelo);
        add(new JScrollPane(tableProces),BorderLayout.CENTER);
    }
    
    public void setInfoTable(FATRow[] row){
        modelo.setRowCount(0);
        fillWithList(row);
    }
    
    public void fillWithList(FATRow[] row){
        for (int i = 0; i < row.length; i++) {
            if (row[i]!=null) {
                        String[] info = {row[i].getStatus()+"",
                            row[i].getId()+"", row[i].getNext() +""};
                modelo.addRow(info);
            }
        }
    }
    
}
