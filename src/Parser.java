import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class Parser {

	private JFrame frame;
	private JMenuBar menubar;
	private JMenu file;
	private JMenuItem open, save, exit;
	private JTextArea sourceCode;
	private JTextArea resultCode;
	private JScrollPane sourceScroll, resultScroll;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Parser window = new Parser();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Parser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menubar = new JMenuBar();
		file = new JMenu("File");
		open = new JMenuItem("Open");
		file.add(open);
		save = new JMenuItem("Save");
		file.add(save);
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(1);
            }
		});
		file.add(exit);
		menubar.add(file);
		frame.setJMenuBar(menubar);
		
		sourceCode = new JTextArea(40, 45);
		frame.getContentPane().add(sourceCode, BorderLayout.WEST);
		sourceScroll = new JScrollPane(sourceCode, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(sourceScroll, BorderLayout.WEST);
		
		resultCode = new JTextArea(40, 45);
		frame.getContentPane().add(resultCode, BorderLayout.EAST);
		resultScroll = new JScrollPane(resultCode, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(resultScroll, BorderLayout.EAST);
		
		open.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent arg0) {
				 FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
				 fd.setDirectory("C:\\VHDL_examples");
				 fd.setFile("*.vhd");
				 fd.setVisible(true);
				 try {
					 String filename = fd.getDirectory() + fd.getFile();
					 File file = new File(filename);
					 BufferedReader br;
					 br = new BufferedReader(new FileReader(file));
					 String st;
					 sourceCode.selectAll();
					 sourceCode.replaceSelection("");
					 while ((st = br.readLine()) != null) {
						 sourceCode.setText(sourceCode.getText() + System.lineSeparator() + st);
					 }
					 br.close();
				 } catch (Exception e) {
					 JOptionPane.showMessageDialog(frame, e.toString());
				 }
			}
		});
	}
}
