package edu.uptc.so.views;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import edu.uptc.so.fms.FileManagerSystem;
import edu.uptc.so.fms.console.FMSConsole;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

public class ConsoleView extends OutputStream {

    private static final String TEXT_SUBMIT = "text-submit";

    private String actual_dir = "fsm#/> ";

    private final StringBuilder sb = new StringBuilder();
    private final JTextArea textArea = new JTextArea(actual_dir, 15, 30);
    private final FMSConsole console = new FMSConsole();

    public ConsoleView() {
        System.setOut(new PrintStream(this));
        initialize();
    }

    public JPanel getPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        return p;
    }

    @Override
    public void flush() { }

    @Override
    public void close() {}

    @Override
    public void write(int b) throws IOException {
        if (b == '\r')
            return;

        if (b == '\n') {
            final String text = "\n" + sb.toString();
            textArea.append(text);
            sb.setLength(0);
            return;
        }

        sb.append((char) b);
    }

    private void initialize() {
        InputMap input = this.textArea.getInputMap();
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        input.put(enter, TEXT_SUBMIT);

        ActionMap actions = textArea.getActionMap();
        actions.put(TEXT_SUBMIT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitText();
            }
        });
    }

    private void submitText() {
        String[] split = textArea.getText().split(actual_dir);

        if(split.length > 0) {
            actual_dir = "fsm#" + console.evaluate(split[split.length-1]) + "> ";
        } 
        this.textArea.append("\n" + actual_dir);
    }

    private static void createAndShowGui() {
        FileManagerSystem.getInstance().format();
        FileManagerSystem.getInstance().init();

        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ConsoleView().getPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }
}
