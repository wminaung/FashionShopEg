import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JButton;

public class View extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JLabel lblNewLabel;
	private JTextField txtName;
	private JTextField txtQty;
	private JTextField txtPrice;
	private JTextField txtBrand;
	private JLabel lblImage;

	/**
	 * Launch the application.
	 */

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
		// scrollPane.setBackground(new Color(245, 222, 179));
		scrollPane.setBounds(62, 65, 622, 245);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Name", "Quantity", "Price", "Brand" }));
		scrollPane.setViewportView(table);

		lblNewLabel = new JLabel("Item Information");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(266, 11, 260, 43);
		contentPane.add(lblNewLabel);

		lblImage = new JLabel("");
		lblImage.setForeground(Color.BLACK);
		lblImage.setBounds(76, 350, 219, 184);
		contentPane.add(lblImage);

		JLabel lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setBounds(397, 342, 91, 25);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("Quantity");
		lblNewLabel_2_1.setBounds(397, 378, 91, 25);
		contentPane.add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_2 = new JLabel("Price");
		lblNewLabel_2_2.setBounds(397, 414, 91, 25);
		contentPane.add(lblNewLabel_2_2);

		JLabel lblNewLabel_2_3 = new JLabel("Brand");
		lblNewLabel_2_3.setBounds(397, 450, 91, 25);
		contentPane.add(lblNewLabel_2_3);

		txtName = new JTextField();
		txtName.setBounds(521, 347, 163, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);

		txtQty = new JTextField();
		txtQty.setColumns(10);
		txtQty.setBounds(521, 380, 163, 20);
		contentPane.add(txtQty);

		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(521, 416, 163, 20);
		contentPane.add(txtPrice);

		txtBrand = new JTextField();
		txtBrand.setColumns(10);
		txtBrand.setBounds(521, 452, 163, 20);
		contentPane.add(txtBrand);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(347, 511, 89, 23);
		contentPane.add(btnUpdate);

		try { 
			Connection con = DatabaseConnection.initializeDatabase();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM item");
			ResultSet rs = stmt.executeQuery();

			Object[] row = new Object[5];
			while (rs.next()) {
				row[0] = rs.getInt("id");
				row[1] = rs.getString("name");
				row[2] = rs.getInt("qty");
				row[3] = rs.getDouble("price");
				row[4] = rs.getString("brand");

				((DefaultTableModel) table.getModel()).addRow(row);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { /// *****////
				int row = table.getSelectedRow();
				int id = (int) table.getValueAt(row, 0);
				System.out.println("id is " + id);
				try {
					Connection con = DatabaseConnection.initializeDatabase();
					String sql = "select * from item where id='" + id + "'";
					PreparedStatement stmt = con.prepareStatement(sql);
					ResultSet rs = stmt.executeQuery();
					while (rs.next()) {
						txtName.setText(rs.getString("name"));
						txtQty.setText(String.valueOf(rs.getInt("qty")));
						txtPrice.setText(String.valueOf(rs.getDouble("price")));
						txtBrand.setText(rs.getString("brand"));
						
						// ************************* //
						Image img = new ImageIcon(rs.getBytes("image")).getImage().getScaledInstance(300, 290, Image.SCALE_DEFAULT);
						ImageIcon image = new ImageIcon(img);
						// ************************* //
						
						lblImage.setIcon(image);
						lblImage.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								System.out.println("Want to change Image?");
							}
						});

					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		// <<end construct>>
	}
}
