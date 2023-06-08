package Login;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.eclipse.jface.internal.text.StickyHoverManager;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ResultsetRow;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;


public class UserHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final String username="root";
    private static final String password="root";
    private static final String dataConn="jdbc:mysql://localhost:3306/mid_term";
    int q,i,id,deteleItem;
    private JPanel contentPane;
    private JTextField textFieldItemName;
    private JTextField textFieldFee;
    private JTable jtable;

    
    Connection sqlConn=null;
    PreparedStatement pst=null;
    ResultSet rs=null;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserHome frame = new UserHome();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

   public void updateDB() {
	   try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		sqlConn=DriverManager.getConnection(dataConn,username,password);
		pst=sqlConn.prepareStatement("select name,phoneNumber from user1");
		
		rs=pst.executeQuery();
		java.sql.ResultSetMetaData stData=rs.getMetaData();
		
		q=stData.getColumnCount();
		DefaultTableModel RecordTable=(DefaultTableModel)jtable.getModel();
		RecordTable.setRowCount(0);
		
		
		while(rs.next()) {
			Vector ColumnData=new Vector();
			for (int i = 1; i < q; i++) {
				//ColumnData.add(rs.getString("id"));
				ColumnData.add(rs.getString("name"));
				//ColumnData.add(rs.getString("password"));
				ColumnData.add(rs.getString("phoneNumber"));	
			}
			RecordTable.addRow(ColumnData);
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		JOptionPane.showMessageDialog(null, e);
	}
	
}
   

    /**
     * Create the frame.
     */
    public UserHome() {
    	getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	
    	JLabel lblCustomList = new JLabel("Customer List");
    	lblCustomList.setBounds(313, 10, 183, 32);
    	lblCustomList.setHorizontalAlignment(SwingConstants.CENTER);
    	lblCustomList.setFont(new Font("Tahoma", Font.BOLD, 26));
    	
    	JButton btnSearch = new JButton("Search customer");
    	btnSearch.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    		String var=	JOptionPane.showInputDialog("Enter person name to search for: ");
    		if(var==null) {
    			JOptionPane.showMessageDialog(null, "Please input the person name");
    		}
    		else {
    			DefaultTableModel defaultTableModel=(DefaultTableModel)jtable.getModel();
        		TableRowSorter<DefaultTableModel> obj=new TableRowSorter<>(defaultTableModel);
        		jtable.setRowSorter(obj);
        		obj.setRowFilter(RowFilter.regexFilter(var.toString()));
        		}
			}
    		
    	});
    	btnSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
    	btnSearch.setBounds(94, 52, 183, 37);
    	
    	JButton btnAdd = new JButton("Add new customer");
    	btnAdd.setFont(new Font("Tahoma", Font.BOLD, 16));
    	btnAdd.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			JPanel fields = new JPanel(new GridLayout(2, 1));
    			JLabel label1=new JLabel("Enter person name:");
    			JTextField field1 = new JTextField(10);
    			JLabel label2=new JLabel("Enter telephone number:");
    			JTextField field2 = new JTextField(10);

    			fields.add(label1);
    			fields.add(field1);
    			fields.add(label2);
    			fields.add(field2);

    			int result = JOptionPane.showConfirmDialog(null, fields, "Add", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    			switch (result) {
    			    case JOptionPane.OK_OPTION:
    			        // Process the results...
    			    	try {
    						Class.forName("com.mysql.cj.jdbc.Driver");
    						sqlConn=DriverManager.getConnection(dataConn,username,password);
    						pst=sqlConn.prepareStatement("insert into user1(name,phoneNumber)values(?,?)");
    						
    						pst.setString(1,field1.getText());
    						pst.setString(2,field2.getText());
    						
    						
    						pst.executeUpdate();
    						JOptionPane.showMessageDialog(null, "Record added");
    						updateDB();
    						
    					} catch (ClassNotFoundException ex) {
    						// TODO: handle exception
    						java.util.logging.Logger.getLogger(UserHome.class.getName()).log(java.util.logging.Level.SEVERE,null,ex);
    					}
    					catch (SQLException ex) {
    						// TODO: handle exception
    						java.util.logging.Logger.getLogger(UserHome.class.getName()).log(java.util.logging.Level.SEVERE,null,ex);
    					}
    	    			
    			        break;
    			}
    		}
    	});
    	btnAdd.setBounds(287, 52, 183, 37);
    	
    	JButton btnSort = new JButton("Sort customer");
    	btnSort.setFont(new Font("Tahoma", Font.BOLD, 16));
    	btnSort.setBounds(480, 52, 183, 37);
    	btnSort.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			
    	
    			 try {
    					Class.forName("com.mysql.cj.jdbc.Driver");
    					sqlConn=DriverManager.getConnection(dataConn,username,password);
    					pst=sqlConn.prepareStatement("select name,phoneNumber from user1 ORDER BY name ASC");
    					
    					rs=pst.executeQuery();
    					java.sql.ResultSetMetaData stData=rs.getMetaData();
    					
    					q=stData.getColumnCount();
    					DefaultTableModel RecordTable=(DefaultTableModel)jtable.getModel();
    					RecordTable.setRowCount(0);
    					
    					
    					while(rs.next()) {
    						Vector ColumnData=new Vector();
    						for (int i = 1; i < q; i++) {
    							//ColumnData.add(rs.getString("id"));
    							ColumnData.add(rs.getString("name"));
    							//ColumnData.add(rs.getString("password"));
    							ColumnData.add(rs.getString("phoneNumber"));	
    						}
    						RecordTable.addRow(ColumnData);
    					}
    					
    				} catch (Exception ex) {
    					// TODO: handle exception
    					JOptionPane.showMessageDialog(null, ex);
    				}
    			
    		}
    	});
    	getContentPane().setLayout(null);
    	getContentPane().add(btnSearch);
    	getContentPane().add(btnAdd);
    	getContentPane().add(btnSort);
    	getContentPane().add(lblCustomList);
    	
    	textFieldItemName = new JTextField();
    	textFieldItemName.setBounds(271, 350, 379, 32);
    	getContentPane().add(textFieldItemName);
    	textFieldItemName.setColumns(10);
    	
    	textFieldFee = new JTextField();
    	textFieldFee.setBounds(271, 490, 202, 32);
    	getContentPane().add(textFieldFee);
    	textFieldFee.setColumns(10);
    	
    	JComboBox comboBoxSalePerson = new JComboBox();
    	comboBoxSalePerson.setBounds(271, 437, 202, 32);
    	getContentPane().add(comboBoxSalePerson);
    	
    	JComboBox comboBoxQuantity = new JComboBox();
    	comboBoxQuantity.setBounds(271, 535, 202, 32);
    	getContentPane().add(comboBoxQuantity);
    	
    	JButton btnSave = new JButton("Save");
    	btnSave.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			
    			
    		}
    	});
    	btnSave.setBounds(267, 592, 85, 21);
    	getContentPane().add(btnSave);
    	
    	JButton btnCancel = new JButton("Cancel");
    	btnCancel.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			JFrame frame; 
        		frame = new JFrame("Exit");
        		if(JOptionPane.showConfirmDialog(frame, "Do you want to exit ?","MYSQL Connector", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
        			System.exit(0);
        		}
    		}
    	});
    	btnCancel.setBounds(411, 592, 85, 21);
    	getContentPane().add(btnCancel);
    	
    	/*UtilDateModel model = new UtilDateModel();
    	JDatePanelImpl datePanel = new JDatePanelImpl(model, null);
    	JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, null);
    	datePicker.setBounds(271, 395, 202, 32);
    	 
    	getContentPane().add(datePicker);*/
    	
    	
    	
    	JLabel lblItemName = new JLabel("Item Name");
    	lblItemName.setFont(new Font("Tahoma", Font.PLAIN, 12));
    	lblItemName.setBounds(113, 358, 111, 13);
    	getContentPane().add(lblItemName);
    	
    	JLabel lblSaleDate = new JLabel("Sales Date");
    	lblSaleDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
    	lblSaleDate.setBounds(113, 398, 111, 13);
    	getContentPane().add(lblSaleDate);
    	
    	JLabel lblSalePerson = new JLabel("Sales Person");
    	lblSalePerson.setFont(new Font("Tahoma", Font.PLAIN, 12));
    	lblSalePerson.setBounds(113, 446, 111, 13);
    	getContentPane().add(lblSalePerson);
    	
    	JLabel lblFee = new JLabel("Fee (d)");
    	lblFee.setFont(new Font("Tahoma", Font.PLAIN, 12));
    	lblFee.setBounds(113, 498, 111, 13);
    	getContentPane().add(lblFee);
    	
    	JLabel lblQuantity = new JLabel("Quantity");
    	lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 12));
    	lblQuantity.setBounds(113, 544, 111, 13);
    	getContentPane().add(lblQuantity);
    	
    	jtable = new JTable();
    	jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	jtable.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			DefaultTableModel RecordTable=(DefaultTableModel)jtable.getModel();
    			int SelectedRows=jtable.getSelectedRow();
    			
    			textFieldItemName.setText(RecordTable.getValueAt(SelectedRows, 0).toString());
    			textFieldFee.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
    			
    			RecordTable.fireTableDataChanged();
    		}
    	});
    	jtable.setModel(new DefaultTableModel(
    		new Object[][] {
    		},
    		new String[] {
    			"name", "phoneNumber"
    		}
    		
    	));
    	jtable.setBounds(97, 132, 518, 173);
    	getContentPane().add(jtable);
    	
    	JLabel lblName = new JLabel("Name");
    	lblName.setHorizontalAlignment(SwingConstants.CENTER);
    	lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
    	lblName.setBounds(96, 103, 248, 27);
    	getContentPane().add(lblName);
    	
    	JLabel lblTelephone = new JLabel("Telephone");
    	lblTelephone.setFont(new Font("Tahoma", Font.BOLD, 16));
    	lblTelephone.setHorizontalAlignment(SwingConstants.CENTER);
    	lblTelephone.setBounds(345, 99, 270, 33);
    	getContentPane().add(lblTelephone);
    	updateDB();
    }
}