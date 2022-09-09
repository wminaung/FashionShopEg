import java.awt.EventQueue;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Sell extends JFrame {
	
	private JPanel contentPane;
	private JTable table;
	private JLabel lblSelected;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Sell() {
		
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("Sell");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 947, 545);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 222, 179));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(71, 51, 789, 374);
		contentPane.add(scrollPane);
		
		table = new JTable();
		ArrayList<Integer> arr = new ArrayList<>();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// ** ** //
			int row = table.getSelectedRow();
			int value = Integer.parseInt(table.getModel().getValueAt(row,0).toString());
			arr.add(value);  ///////////////////////////////
			String selectedRow = "" ;
			for (Integer integer : arr) {
				selectedRow += integer +", ";
			}
			lblSelected.setText("You are Selected " + selectedRow );
			}
		});
		scrollPane.setViewportView(table);
		String[] col = {"Id", "Name", "Brand", "Price", "Photo"}; 
		DefaultTableModel model = new DefaultTableModel(null, col) {  ////****************/////

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				if(columnIndex == 4) 
					return ImageIcon.class;
				
				return Object.class;
			}	
		};
		
		try {
			
			Connection con = DatabaseConnection.initializeDatabase();
			
			PreparedStatement stmt = con.prepareStatement("select * from item");
			ResultSet rs = stmt.executeQuery();
		
			Object row[] = new Object[5];
			
			while(rs.next()) {
				row[0] = rs.getInt("id");
				row[1] = rs.getString("name");
				row[2] = rs.getString("brand");
				row[3] = rs.getDouble("price");
				row[4] = new ImageIcon(new ImageIcon(rs.getBytes("image")).getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
				model.addRow(row);

			}

			table.setRowHeight(120);
			table.setModel(model);
			
			JButton btnNewButton = new JButton("Buy");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { //when buy
					////////////////////////////////////////////
					Buy buy = new Buy(arr);
					buy.setVisible(true);
				}
			});
			btnNewButton.setBounds(388, 455, 89, 23);
			contentPane.add(btnNewButton);
			
			lblSelected = new JLabel("");
			lblSelected.setBounds(241, 26, 364, 14);
			contentPane.add(lblSelected);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
