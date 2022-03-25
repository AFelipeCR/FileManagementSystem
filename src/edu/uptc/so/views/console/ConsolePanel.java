package edu.uptc.so.views.console;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import edu.uptc.so.fms.utils.CustomListener;

public class ConsolePanel extends JPanel {
	private static final long serialVersionUID = 1790930200248095937L;
	private static final String TEXT_SUBMIT = "text-submit";
	private final JTextArea textArea;
	private final FMSConsoleMenu console;
	private String prompt;
	private int currentCaret;

	public ConsolePanel(CustomListener onWrite) {
		this.prompt = "fsm#/> ";
		this.textArea = new JTextArea(this.prompt, 15, 30);
		this.currentCaret = this.prompt.length();
		this.console = new FMSConsoleMenu(onWrite);
		this.init();
	}

	private void init() {
		this.setLayout(new BorderLayout());

		this.textArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), TEXT_SUBMIT);
		this.textArea.getActionMap().put(TEXT_SUBMIT, new AbstractAction() {
			private static final long serialVersionUID = 6741680472655161236L;

			@Override
			public void actionPerformed(ActionEvent e) {
				submitText();
			}
		});
		
		this.textArea.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {				
				if(currentCaret > textArea.getCaretPosition()) {
					if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
						textArea.setText(textArea.getText() + " ");
					textArea.setCaretPosition(currentCaret);
				}
					
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		this.textArea.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(currentCaret > textArea.getCaretPosition())
					textArea.setCaretPosition(currentCaret);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		

		this.add(new JScrollPane(this.textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	}

	private void submitText() {
		String[] split = this.textArea.getText().split(prompt);

		if (split.length > 0) {
			this.prompt = "fsm#" + this.console.evaluate(this.textArea, split[split.length - 1]) + "> ";
		}

		this.textArea.append("\n" + prompt);
		this.textArea.setCaretPosition(this.textArea.getText().length());
		this.currentCaret = this.textArea.getCaretPosition();
	}
}
