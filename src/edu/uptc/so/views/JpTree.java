package edu.uptc.so.views;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import edu.uptc.so.fms.entities.DFT;


public class JpTree extends JPanel implements MouseListener{

	private static final long serialVersionUID = 1L;
	private JTree jTree;
	private JPopupMenu jPopMenu;
	private DefaultTreeModel treeModel;
	private DFT root;
	private JButton jButton;

	public JpTree(DFT root, ActionListener listener) {
		initcomponents(listener);
		setupLayoutManager();
		paintTree(root);
		setVisible(true);
	}
	
	
	public void paintTree(DFT root) {
		this.root = root;
		DefaultMutableTreeNode visualRoot = new DefaultMutableTreeNode(this.root.getName());
		for (DFT child : this.root.getChildrenDfts()) {
			if (child != null) {
				addChild(visualRoot, child);
			}
		}
		treeModel = new DefaultTreeModel(visualRoot);
		jTree.addMouseListener(this);
		jTree.setModel(treeModel);
		jTree.repaint();
	}
	
	private void addChild(DefaultMutableTreeNode father, DFT node) {
		if (node != null) {
			DefaultMutableTreeNode visualNode = new DefaultMutableTreeNode(node.getName());
			father.add(visualNode);
			if(node.getChildrenDfts()[0]!=null) {
				for (DFT child : node.getChildrenDfts()) {
					addChild(visualNode, child);
				}	
			}
		}
	} 
	
	private void initcomponents(ActionListener listener) {
		jTree = new JTree();
		jButton = new JButton("Tabla");
		jButton.addActionListener(listener);
	}
	
	private void setupLayoutManager() {
		setLayout(new BorderLayout());
		JScrollPane jScrollPane = new JScrollPane(jTree);
		add(jButton, BorderLayout.SOUTH);
		add(jScrollPane, BorderLayout.CENTER);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		TreePath treePath = jTree.getPathForLocation (e.getX (), e.getY ());
		if (treePath != null) {
			settingsJPopMenu(treePath.toString());
			jTree.setSelectionPath(treePath);
			if (e.getButton() == 3) {
				jPopMenu.show(jTree, e.getX(), e.getY());
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void settingsJPopMenu(String treePath) {
		jPopMenu = new JPopupMenu();
		DFT dft = searchDFT(nameDFT(path(treePath)), root);
		if(dft != null ){
			JLabel jlName = new JLabel("Nombre:      " + dft.getName());
			JLabel jlPath = new JLabel("Ubicación:  " + path(treePath));
			JLabel jlCreatedAt = new JLabel("Creado:       " + dft.getName()); // getCreatedAt
			JLabel jlSize = new JLabel("Tamaño:     " + dft.getName()); //getSize
			jPopMenu.add(jlName);
			jPopMenu.add(jlPath);
			jPopMenu.add(jlCreatedAt);
			jPopMenu.add(jlSize);
		}
	}
	
	private DFT searchDFT(String nameDFT, DFT node) {
		if(!nameDFT.equals(node.getName())) {
			int i = 0;
			DFT children = node.getChildrenDfts()[i];
			while(children != null) {
				DFT temp = searchDFT(nameDFT, children);
	             if (temp!=null) {
	                return  temp;
	             }
	            i++;
				children = node.getChildrenDfts()[i];
	           }
	           return null;
		} else {
			return node;
		}
	}
	
	private String nameDFT(String path) {
		if (path.split("/").length == 1) {
			return path.split("/")[path.split("/").length-1];
		} else {
			return path.split("/")[path.split("/").length-1];
		}
		
	}
	
	private String path(String treePath) {
		treePath = treePath.replace(", ",",");
		treePath = treePath.replace("[","");
		treePath = treePath.replace("]","");
		treePath = treePath.replace(",","/");
		return treePath;
	}
}