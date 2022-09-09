import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class View extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JLabel lblNewLabel;
	private JTextField txtName;
	private JTextField txtQty;
	private JTextField txtPrice;
	private JTextField txtBrand;
	private JLabel lblImage;
	FileInputStream fis;
	byte[] insert_image = null;
	int ID = 0;
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
		lblImage.setBounds(75, 350, 219, 184);
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
		
		btnUpdate.setBounds(384, 511, 89, 23);
		contentPane.add(btnUpdate);
		
		JButton btnEditImg = new JButton("Edit Img");
		
		
		btnEditImg.setBounds(126, 321, 89, 23);
		contentPane.add(btnEditImg);

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
				ID = id;
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
						Image img = new ImageIcon(rs.getBytes("image")).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
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

		btnEditImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Image Edit browseing
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.images", "jpg", "jpeg", "png", "gif");
				chooser.addChoosableFileFilter(filter);
			int result =	chooser.showSaveDialog(null);
				
			
			if (result == JFileChooser.APPROVE_OPTION) {
				String path = chooser.getSelectedFile().getAbsolutePath();
				File image = new File(path);
				try {
					fis = new FileInputStream(image);
					// important !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					try {
						for (int len; (len = fis.read(buffer)) != -1;) {
						    bos.write(buffer, 0, len);
						}
						insert_image = bos.toByteArray();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else if (result == JFileChooser.CANCEL_OPTION) {
				JOptionPane.showMessageDialog(null, "Please Choose Image");
			}
				
			}
		});
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//update data
				
				if(ID != 0) {
					System.out.println(ID);
					Connection con;
					try {
						con = DatabaseConnection.initializeDatabase();
				
						
						String sql = "UPDATE item SET name=?, brand=?, price=?, qty=?, image=? WHERE id=?";
						PreparedStatement stmt = con.prepareStatement(sql);
						
						stmt.setString(1, txtName.getText());
						stmt.setString(2, txtBrand.getText());
						stmt.setDouble(3, Double.parseDouble(txtPrice.getText()));
						stmt.setInt(4, Integer.parseInt(txtQty.getText()));
						//stmt.setBinaryStream(5, fis); >>> not work
						//stmt.setBlob(5, fis); >>>  not work
						stmt.setBytes(5, insert_image);
						stmt.setInt(6, ID);
						stmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Successfully Updated");
			
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "You need to select item");
				}
				
			}
		});
		
	
		
		// <<end construct>>
	}
}
