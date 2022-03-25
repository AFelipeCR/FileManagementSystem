package edu.uptc.so.views.console;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class ConsoleOutputStream extends OutputStream  {
	private JTextArea textArea;
    private final StringBuilder sb;
	
	public ConsoleOutputStream(JTextArea textArea) {
		this.sb = new StringBuilder();
		this.textArea = textArea;
	}

	@Override
	public void write(int b) throws IOException {
		 if (b == '\r')
	            return;

	        if (b == '\n') {
	            final String text = "\n" + sb.toString();
	            this.textArea.append(text);
	            this.sb.setLength(0);
	            return;
	        }

	        sb.append((char) b);
	}
}
