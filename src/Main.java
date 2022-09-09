import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Home");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 830, 599);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 222, 179));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 169, 29);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Stock");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Insert Item");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// insert item event
				InsertItem insertItem = new InsertItem();
				insertItem.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("View");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View view = new View();
				view.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu("Order");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem sellMenuItem = new JMenuItem("Sell");
		sellMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // on click sell menu
				Sell sell = new Sell();
				sell.setVisible(true);
			}
		});
		mnNewMenu_1.add(sellMenuItem);
		
		JMenuItem mntmNewMenuItem_2_1 = new JMenuItem("Exit");
		mntmNewMenuItem_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2_1);
		
		JLabel lblNewLabel = new JLabel("Welcome Fashion Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(260, 11, 326, 61);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\wtm\\Desktop\\win-WorkSpace\\FashionShop\\img\\fashionShop.jpg"));
		lblNewLabel_1.setBounds(54, 78, 708, 462);
		contentPane.add(lblNewLabel_1);
	}
}
