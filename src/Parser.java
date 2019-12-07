import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Parser {

	private JFrame frame;

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
		frame.setBounds(100, 100, 995, 538);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new ActionListener() {
			 @Override
             public void actionPerformed(ActionEvent arg0) {
				 FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
				 fd.setDirectory("C:\\VHDL_examples");
				 fd.setFile("*.vhd");
				 fd.setVisible(true);
				 String filename = fd.getFile();
				 if (filename == null)
				   System.out.println("You cancelled the choice");
				 else
				   System.out.println("You chose " + filename);
             }
		});
		file.add(open);
		
		JMenuItem save = new JMenuItem("Save");
		file.add(save);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			 @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(1);
            }
		});
		file.add(exit);
		
		menubar.add(file);
		frame.setJMenuBar(menubar);
	}
}
