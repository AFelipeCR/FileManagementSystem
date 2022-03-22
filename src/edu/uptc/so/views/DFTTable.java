
package edu.uptc.so.views;

import edu.uptc.so.fms.entities.DFT;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DFTTable extends JPanel{
    
    private JTable tableProces;
    private DefaultTableModel modelo;
    private JLabel lblTitle;

    public DFTTable() {
    }
    public void buildtable(){
        setLayout(new BorderLayout());
        lblTitle = new JLabel("DFT info");
        add(lblTitle, BorderLayout.NORTH);
        modelo = new DefaultTableModel();
        //"id", "type", "name", "head", "visibility","createdAt","updatedAt", "accessedAt", "size","children"
        String[] headers = {"name", "head", "visibility",
            "createdAt","updatedAt", "accessedAt", "size","children"};
        modelo.setColumnIdentifiers(headers);
        tableProces = new JTable(modelo);
        add(new JScrollPane(tableProces),BorderLayout.CENTER);
    }
    
    public void setInfoTable(DFT[] root){

        modelo.setRowCount(0);
        fillWithTree(root);
    }
    
    public void fillWithTree(DFT[] root){
        for (int i = 0; i < root.length; i++) {
            String[] info = {root[i].getName(), root[i].getHead() +"", root[i].getVisibility() +""};
            modelo.addRow(info);
            fillWithTree(root[i].getChildrenDfts());
        }
    }
}
