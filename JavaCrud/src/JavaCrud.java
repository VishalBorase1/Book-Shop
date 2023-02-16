import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.SystemColor;

public class JavaCrud {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
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
	public JavaCrud() {
		initialize();
		Connect();
		tableLoad();
	}
	
	private String url = "jdbc:mysql://localhost:3306/bookshop?autoReconnect=true&useSSL=false";
	private String user = "root";
	private String password = "root";
	private Connection con;
	private PreparedStatement pat;
	private ResultSet res;


	public void Connect() 
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection Established");
			
		}
		catch(ClassNotFoundException e) {
			
		}
		catch(SQLException e) {
			
		}
	}
	
	public void tableLoad() {
		try {
			pat = con.prepareStatement("select * from book");
			res = pat.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(res));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 615, 413);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(216, 11, 191, 57);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBorder(new TitledBorder(null, "Registation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(20, 79, 306, 151);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Edition");
		lblNewLabel_1.setBackground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(20, 68, 86, 17);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Book Name");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(20, 27, 86, 17);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(20, 107, 86, 17);
		panel.add(lblNewLabel_1_2);
		
		txtbname = new JTextField();
		txtbname.setBounds(119, 27, 159, 17);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(119, 68, 159, 17);
		panel.add(txtedition);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(119, 107, 159, 17);
		panel.add(txtprice);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bname,edition,price;
				
				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				
				
				try {
					pat = con.prepareStatement("insert into book(name,edition,price)values(?,?,?)");
					pat.setString(1, bname);
					pat.setString(2, edition);
					pat.setString(3, price);
					pat.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!!");
					tableLoad();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
				}
				
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton.setBounds(20, 241, 85, 35);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(Color.LIGHT_GRAY);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});
		btnExit.setBounds(123, 241, 85, 35);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBackground(Color.LIGHT_GRAY);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtbname.setText("");
				txtedition.setText("");
				txtprice.setText("");
				txtbname.requestFocus();
			}
		});
		btnClear.setBounds(229, 241, 78, 35);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(331, 79, 256, 215);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(20, 309, 306, 54);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_3 = new JLabel("Book ID");
		lblNewLabel_1_3.setBounds(10, 24, 64, 17);
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_1.add(lblNewLabel_1_3);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					
					String id = txtbid.getText();
					
						pat = con.prepareStatement("select name,edition,price from book where id = ?");
						pat.setString(1, id);
						res = pat.executeQuery();
						
					if(res.next()== true) 
					{
						
						String name = res.getString(1);
						String edition = res.getString(2);
						String price = res.getString(3);
						
						txtbname.setText(name);
						txtedition.setText(edition);
						txtprice.setText(price);
					}
					else
					{
						txtbname.setText("");
						txtedition.setText("");
						txtprice.setText("");
					}
				}
				catch(Exception e1) {
					
				}
				
				
				
			}
		});
		txtbid.setBounds(84, 24, 170, 18);
		txtbid.setColumns(10);
		panel_1.add(txtbid);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBackground(Color.LIGHT_GRAY);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name, edition, price, bid;
				
				name = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				bid = txtbid.getText();
				
				try {
					pat = con.prepareStatement("update book set name = ?, edition = ?, price = ? where id = ?");
					pat.setString(1, name);
					pat.setString(2, edition);
					pat.setString(3, price);
					pat.setString(4, bid);
					pat.executeUpdate();
					JOptionPane.showMessageDialog(txtbname, "Record Updated");;
					tableLoad();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(373, 305, 78, 35);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnClear_1_1 = new JButton("Delete");
		btnClear_1_1.setBackground(Color.LIGHT_GRAY);
		btnClear_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bid;
				
				bid = txtbid.getText();
				
				try {
					pat = con.prepareStatement("delete from book where id = ?");
					pat.setString(1, bid);
					pat.executeUpdate();
					JOptionPane.showMessageDialog(txtbname, "Record Deleted!!");;
					tableLoad();
					txtbname.setText("");
					txtedition.setText("");
					txtprice.setText("");
					txtbname.requestFocus();
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnClear_1_1.setBounds(484, 305, 73, 35);
		frame.getContentPane().add(btnClear_1_1);
	}
}
