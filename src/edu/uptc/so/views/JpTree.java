package edu.uptc.so.views;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import edu.uptc.so.fms.FileType;
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
		if(root != null){
			this.root = root;
			DefaultMutableTreeNode visualRoot = new DefaultMutableTreeNode(this.root.getName());
			
			for (DFT child : this.root.getChildrenList()) {
				this.addChild(visualRoot, child);
			}
			
			this.treeModel = new DefaultTreeModel(visualRoot);
			this.jTree.addMouseListener(this);
			this.jTree.setModel(treeModel);
			this.jTree.repaint();
		} else {
			DefaultMutableTreeNode visualRoot = new DefaultMutableTreeNode("root empty");
			this.treeModel = new DefaultTreeModel(visualRoot);
			this.jTree.addMouseListener(this);
			this.jTree.setModel(treeModel);
			this.jTree.repaint();
		}
            
	}
	
	private void addChild(DefaultMutableTreeNode father, DFT node) {
		DefaultMutableTreeNode visualNode = new DefaultMutableTreeNode(node.getName());
		father.add(visualNode);
		
		if(node.getType() == FileType.DIR.ordinal()) {
			
			for (DFT child : node.getChildrenList()) {
				if(child.getType() == FileType.DIR.ordinal())
					this.addChild(visualNode, child);
			}
		}
	} 
	
	private void initcomponents(ActionListener listener) {
		this.jTree = new JTree();
		this.jButton = new JButton("Tabla");
		this.jButton.addActionListener(listener);
	}
	
	private void setupLayoutManager() {
		this.setLayout(new BorderLayout());
		JScrollPane jScrollPane = new JScrollPane(jTree);
		this.add(jButton, BorderLayout.SOUTH);
		this.add(jScrollPane, BorderLayout.CENTER);
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
			this.jTree.setSelectionPath(treePath);
			if (e.getButton() == 3) {
				this.jPopMenu.show(jTree, e.getX(), e.getY());
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
		this.jPopMenu = new JPopupMenu();
		DFT dft = searchDFT(nameDFT(path(treePath)), root);
		
		if(dft != null ){
			JLabel jlName = new JLabel("Nombre:      " + dft.getName());
			JLabel jlPath = new JLabel("Ubicacion:  " + path(treePath));
			Format format = new SimpleDateFormat("dd/MM/yyyy");
			JLabel jlCreatedAt = new JLabel("Creado:       " + format.format(new Date(dft.getCreatedAt())));
			JLabel jlSize = new JLabel("Tama�o:     " + dft.getSize());
			this.jPopMenu.add(jlName);
			this.jPopMenu.add(jlPath);
			this.jPopMenu.add(jlCreatedAt);
			this.jPopMenu.add(jlSize);
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