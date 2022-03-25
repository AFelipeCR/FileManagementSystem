package edu.uptc.so.views.console;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.uptc.so.fms.FMSConstants;
import edu.uptc.so.fms.FileManagerSystem;
import edu.uptc.so.fms.FileType;
import edu.uptc.so.fms.entities.DFT;
import edu.uptc.so.fms.utils.CustomListener;
import edu.uptc.so.fms.utils.Utils;
import edu.uptc.so.resources.Resources;

public class ConsoleNano implements FMSConstants {
	public void open(String path, short[] read, CustomListener onWrite) {
		DFT dft = FileManagerSystem.getInstance().openDFT(path);
		
		if (dft.getType() == FileType.DIR.ordinal())
			return;

		byte[] full = new byte[read.length * CLUSTER_SIZE];
		
		for (int i = 0; i < read.length; i++) {
			Utils.fillBytes(full, Resources.readDisk(RESERVED_SPACE + read[i] * CLUSTER_SIZE, CLUSTER_SIZE), i * CLUSTER_SIZE);
		}
		
		String fullString = Utils.bytesToString(full);
		
		JTextArea textArea = new JTextArea(fullString, 20, 70);
		
		JDialog d = new JDialog();
		d.setTitle("Editar " + dft.getName());
		d.setSize(600, 400);
		d.setLocationRelativeTo(null);
		
		JPanel p = new JPanel(), bottomP = new JPanel();
		p.setLayout(new BorderLayout());
		
		
		
		JButton okB = new JButton("Guardar"), cancelB = new JButton("Cancelar");
		
		okB.addActionListener((ActionEvent e) -> {
			FileManagerSystem.getInstance().overwrite(path, Utils.stringToBytes(textArea.getText(), textArea.getText().length()));
			d.dispose();
			onWrite.run(null);
		});
		
		cancelB.addActionListener((ActionEvent e) -> d.dispose());
		
		bottomP.add(okB);
		bottomP.add(cancelB);
		
		p.add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
		p.add(bottomP, BorderLayout.SOUTH);
		
		d.add(p);		
		d.setVisible(true);
	}

}
