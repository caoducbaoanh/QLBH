package Login;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.management.loading.PrivateClassLoader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

public class UserLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnLogin;
    private JLabel label;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserLogin frame = new UserLogin();
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
    public UserLogin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 46));
        lblNewLabel.setBounds(423, 13, 273, 93);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textField.setBounds(481, 170, 281, 68);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(481, 286, 281, 68);
        contentPane.add(passwordField);

        JLabel lblUsername = new JLabel("User");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(250, 166, 193, 52);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblPassword.setBounds(250, 286, 193, 52);
        contentPane.add(lblPassword);

        btnLogin = new JButton("Login");
        btnLogin.setForeground(new Color(0, 0, 102));
        btnLogin.setBackground(new Color(240, 240, 240));
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 26));
        btnLogin.setBounds(281, 401, 162, 73);
        btnLogin.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String userName = textField.getText();
                String password = passwordField.getText();
                
                try {
                	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mid_term",
                            "root", "root");
                    // Retrieve the stored password and decrypt it
                    String selectQuery = "SELECT name, AES_DECRYPT(password, 'key123') AS decrypted_password FROM user1 WHERE name = ?";
                    try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                        selectStatement.setString(1, userName);
                        try (ResultSet resultSet = selectStatement.executeQuery()) {
                            if (resultSet.next()) {
                                
                                String decryptedPassword = resultSet.getString("decrypted_password");
                                

                                // Compare the decrypted password with the raw password
                                if (decryptedPassword.equals(password)) {
                                	 dispose();
                                     UserHome ah = new UserHome();
                                     ah.setTitle("Welcome");
                                     ah.setVisible(true);
                                     JOptionPane.showMessageDialog(btnLogin, "You have successfully logged in");
                                } else {
                                	JOptionPane.showMessageDialog(btnLogin, "Wrong Username & Password");
                                }
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
                
            }
        });

        contentPane.add(btnLogin);
        
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
        btnCancel.setForeground(new Color(0, 0, 102));
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 26));
        btnCancel.setBounds(610, 401, 162, 73);
        contentPane.add(btnCancel);

        
    }
}
