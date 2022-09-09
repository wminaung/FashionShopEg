import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Buy extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTable table;
	private int row;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Buy(ArrayList arr) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 572, 429);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 222, 179));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(93, 33, 362, 180);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Name", "Price" }));
		scrollPane.setViewportView(table);
		

		JLabel lblNewLabel = new JLabel("Total Price");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(133, 247, 88, 21);
		contentPane.add(lblNewLabel);

		JLabel lblTotal = new JLabel(" 0.0");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTotal.setBounds(231, 250, 71, 17);
		contentPane.add(lblTotal);

		JButton btnOk = new JButton("ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnOk.setBounds(333, 247, 71, 23);
		contentPane.add(btnOk);

		JLabel lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(55, 307, 49, 28);
		contentPane.add(lblNewLabel_2);

		txtName = new JTextField();
		txtName.setBounds(133, 312, 110, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		Connection con;
		double price = 0;
		try {
			con = DatabaseConnection.initializeDatabase();
			for (Object id : arr) {
				PreparedStatement stmt = con.prepareStatement("select * from item where id=?");
				stmt.setInt(1, (int) id);
				ResultSet rs = stmt.executeQuery();
				((DefaultTableModel)table.getModel()).addRow(new Object[] { rs.getInt("id"), rs.getString("name"), rs.getDouble("price") });
				
				price += rs.getDouble("price");
				
			}
			lblTotal.setText(String.valueOf(price));
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				////////////////////////////////////////////////////////
				arr.remove(row);
				Buy.this.setVisible(false);
				Buy buy = new Buy(arr);
				buy.setVisible(true);
			}
		});
		btnDelete.setBounds(315, 311, 89, 23);
		contentPane.add(btnDelete);
	btnDelete.setEnabled(false);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				////////////////////////////////////////////////////////
				 row = table.getSelectedRow();
				int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
				String name = table.getModel().getValueAt(row, 1).toString();
				txtName.setText(name);

			}
		});
	}
}
