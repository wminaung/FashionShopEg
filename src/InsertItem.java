import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class InsertItem extends JFrame {

	private JPanel contentPane;
	private JTextField txt1;
	private JTextField txt2;
	private JTextField txt3;
	private JTextField txt4;
	FileInputStream fis;
	byte[] insert_image = null;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public InsertItem() {
		setTitle("Insert Item");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 569, 556);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 222, 179));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Fashion Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setBounds(156, 34, 179, 41);
		contentPane.add(lblNewLabel);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(65, 118, 83, 41);
		contentPane.add(lblName);

		JLabel lblPrice = new JLabel("Brand");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrice.setBounds(65, 174, 83, 41);
		contentPane.add(lblPrice);

		JLabel lblPrice_1 = new JLabel("Price");
		lblPrice_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrice_1.setBounds(65, 240, 83, 41);
		contentPane.add(lblPrice_1);

		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantity.setBounds(65, 304, 83, 41);
		contentPane.add(lblQuantity);

		JLabel lblImage = new JLabel("Image");
		lblImage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblImage.setBounds(102, 369, 130, 41);
		contentPane.add(lblImage);

		JButton btnBrowse = new JButton("Browse");

		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// image Browse
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.images", "png", "jpg", "gif");
				chooser.addChoosableFileFilter(filter);
				int result = chooser.showSaveDialog(null);

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
		btnBrowse.setBounds(287, 380, 98, 30);
		contentPane.add(btnBrowse);

		txt1 = new JTextField();
		txt1.setBounds(224, 122, 252, 29);
		contentPane.add(txt1);
		txt1.setColumns(10);

		txt2 = new JTextField();
		txt2.setColumns(10);
		txt2.setBounds(224, 182, 252, 29);
		contentPane.add(txt2);

		txt3 = new JTextField();
		txt3.setColumns(10);
		txt3.setBounds(224, 248, 252, 29);
		contentPane.add(txt3);

		txt4 = new JTextField();
		txt4.setColumns(10);
		txt4.setBounds(224, 312, 252, 29);
		contentPane.add(txt4);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// save item
				String name = txt1.getText()	;
				String brand = txt2.getText();
				double price = Double.parseDouble(txt3.getText());
				int qty = Integer.parseInt(txt4.getText());
				
				Connection con;
				try {
					con = DatabaseConnection.initializeDatabase();
					String sql = "INSERT INTO item( name, brand, price, qty, image) VALUES(?, ?, ?, ?, ?)";
					PreparedStatement stmt = con.prepareStatement(sql);
					stmt.setString(1, name);
					stmt.setString(2, brand);
					stmt.setDouble(3, price);
					stmt.setInt(4, qty);
					//stmt.setBinaryStream(5, fis); >>> not work
					//stmt.setBlob(5, fis); >>>  not work
					stmt.setBytes(5, insert_image);
					stmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "Successfully Saved");
					txt1.setText("");
					txt2.setText("");
					txt3.setText("");
					txt4.setText("");
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnSave.setBounds(213, 461, 111, 30);
		contentPane.add(btnSave);
	}
//	private byte[] readFile(String file) {
//	    ByteArrayOutputStream bos = null;
//	    try {
//	        File f = new File(file);
//	        FileInputStream fis = new FileInputStream(f);
//	        byte[] buffer = new byte[1024];
//	        bos = new ByteArrayOutputStream();
//	        for (int len; (len = fis.read(buffer)) != -1;) {
//	            bos.write(buffer, 0, len);
//	        }
//	    } catch (FileNotFoundException e) {
//	        System.err.println(e.getMessage());
//	    } catch (IOException e2) {
//	        System.err.println(e2.getMessage());
//	    }
//	    return bos != null ? bos.toByteArray() : null;
//	}
	// <<end class>>
}
