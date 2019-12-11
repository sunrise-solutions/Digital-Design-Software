import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import Models.Entity;
import Models.FileParser;
import Models.Item;

public class Parser {

	private JFrame frame;
	private JMenuBar menubar;
	private JMenu file;
	private JMenuItem open, save, exit;
	private JTextArea sourceCode;
	private JTextArea resultCode;
	private JScrollPane sourceScroll, resultScroll;
	private JLabel lblSourceCode;
	private JLabel lblResultCode;
	private JTable identTable;
	private JScrollPane identScroll;
	
	private TableModel model;
	private String sourceFile;
	private Entity entity;

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
		frame.setBounds(50, 50, 1357, 732);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menubar = new JMenuBar();
		file = new JMenu("File");
		open = new JMenuItem("Open");
		open.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent arg0) {
				 openFile();
			}
		});
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
		
		sourceCode = new JTextArea(20, 40);
		frame.getContentPane().add(sourceCode, BorderLayout.WEST);
		sourceScroll = new JScrollPane(sourceCode, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		lblSourceCode = new JLabel("Source code");
		lblSourceCode.setHorizontalAlignment(SwingConstants.CENTER);
		sourceScroll.setColumnHeaderView(lblSourceCode);
		
		resultCode = new JTextArea(20, 40);
		frame.getContentPane().add(resultCode, BorderLayout.EAST);
		resultScroll = new JScrollPane(resultCode, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		lblResultCode = new JLabel("Result code");
		lblResultCode.setHorizontalAlignment(SwingConstants.CENTER);
		resultScroll.setColumnHeaderView(lblResultCode);
		
		model = new TableModel();
		identTable = new JTable(model);
		identScroll = new JScrollPane(identTable);
		frame.getContentPane().add(identScroll, SwingConstants.CENTER);
		
		JButton btnFindIdentificators = new JButton("Find identificators");
		btnFindIdentificators.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.deleteData();
				identTable.revalidate();
				parseFile(sourceCode.getText());
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(sourceScroll, GroupLayout.PREFERRED_SIZE, 462, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(identScroll, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
							.addGap(18))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnFindIdentificators)
							.addGap(123)))
					.addComponent(resultScroll, GroupLayout.PREFERRED_SIZE, 447, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(sourceScroll, GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
					.addComponent(resultScroll, GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(34)
					.addComponent(btnFindIdentificators)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(identScroll, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(297, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	private void openFile() {
		FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
		fd.setDirectory("C:\\VHDL_examples");
		fd.setFile("*.vhd");
		fd.setVisible(true);
		try {
			sourceFile = fd.getDirectory() + fd.getFile();
			File file = new File(sourceFile);
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
	
	private void parseFile(String content) {
		try
        {
			content = content.toLowerCase();
			entity = new Entity();
            FileParser parser = new FileParser(content);
            parser.ParseFile(entity);
            
            ArrayList<Item> obfuscatedItems = new ArrayList<Item>();
            content = AddSpacesToCode(content);
            for (Item i : parser.entity.items) {
            	Item newIdentificator = GenerateObfuscatedIdentificator(i);
            	obfuscatedItems.add(newIdentificator);
            	boolean check = content.contains(" " + i.Name + " ");
            	while (content.contains(" " + i.Name + " ")) {
            		content = content.replace(" " + i.Name + " ", " " + newIdentificator.Name + " ");
    			}
            }
            
            content = DeleteUncompiledSpaces(content);
            parser.entity.AddObfuscatedItems(obfuscatedItems);
            
            for (int i = 0; i < parser.entity.items.size(); i++) {
            	model.addData(parser.entity.items.get(i).getName(), parser.entity.obfuscatedItems.get(i).getName());
            	identTable.revalidate();
            }
            
            resultCode.setText(content);
        }
        catch (Exception ex)
        {
        	JOptionPane.showMessageDialog(frame, ex.toString());
        }
	}
	
    private Item GenerateObfuscatedIdentificator(Item identificator) {
		Item i = new Item(identificator.Name + "TopSecret", identificator.Type);
		return i;
	}
    
    
    private String AddSpacesToCode(String code) {
		
		String[] operators = {"(", ")", ":", ",", ";", "*", "/", "+", "-", "&", "=", "<", ">",       "'", "\"", "."};
		String temp = "";
		String strToReplace = "";
		for (String o : operators) {
			temp = "";
			while (code.contains(o)) {
				strToReplace = " " + o + " ";
				code = code.replace(o, strToReplace);
				temp += code.substring(0, code.indexOf(strToReplace) + strToReplace.length() + 1);
				code = code.substring(code.indexOf(strToReplace) + strToReplace.length());
			}
			temp += code;
			code = temp;
		}
		return temp;		
		
	}
    
    private String DeleteUncompiledSpaces(String code) {
    	String[] compoundOperatorsForSearch = {"< =", ": =", "/ =", "> =", "* *",       " ' ", " \" ", " . "};
		String[] compoundOperatorsForReplace = {"<=", ":=", "/=", ">=", "**",       "'", "\"", "."};
		
		for (int i = 0; i < compoundOperatorsForSearch.length; i++) {
			while (code.contains(compoundOperatorsForSearch[i])) {
				code = code.replace(compoundOperatorsForSearch[i], compoundOperatorsForReplace[i]);
			}
		}
		
    	return code;
    }
}
