import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;

public class View extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
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
	public View() {
		setTitle("View Item");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 770, 620);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 222, 179));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setBackground(new Color(245, 222, 179));
		scrollPane.setBounds(62, 65, 622, 245);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name","Quantity" , "Price", "Brand"
			}
		));
		scrollPane.setViewportView(table);
		
		lblNewLabel = new JLabel("Item Information");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(257, 11, 260, 43);
		contentPane.add(lblNewLabel);
		 
		try {
			Connection con = DatabaseConnection.initializeDatabase();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM item");
			ResultSet rs = stmt.executeQuery();
			
			Object[] row = new Object[5];
			while(rs.next()) {
				row[0] = rs.getInt("id");
				row[1] = rs.getString("name");
				row[2] = rs.getInt("qty");
				row[3] = rs.getDouble("price");
				row[4] = rs.getString("brand");
				
				((DefaultTableModel)table.getModel()).addRow(row);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// <<end construct>>
	}
	
	// >>end class<<
}
