package view;

import dao.UserDAO;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.*;
import model.User;
import util.UserSession;

public class LoginFrame extends JFrame {
    private final UserDAO userDAO = new UserDAO();
    private JTextField txtUsername;
    private JPasswordField pwdPassword;
    private JButton btnLogin, btnKeluar;

    public LoginFrame() {
        initComponents();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnLogin);
    }

    private void initComponents() {
        setTitle("Login - SIKURSUS Desktop");
        setSize(420, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(26, 82, 118));
        header.setPreferredSize(new Dimension(420, 80));
        JLabel lblTitle = new JLabel("SIKURSUS Desktop");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        header.add(lblTitle);
        add(header, BorderLayout.NORTH);

        // Form Input
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        Font font = new Font("Tahoma", Font.PLAIN, 14);

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblUser = new JLabel("Username:"); lblUser.setFont(font); formPanel.add(lblUser, gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(); txtUsername.setFont(font); txtUsername.setPreferredSize(new Dimension(200, 30)); formPanel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblPass = new JLabel("Password:"); lblPass.setFont(font); formPanel.add(lblPass, gbc);
        gbc.gridx = 1;
        pwdPassword = new JPasswordField(); pwdPassword.setFont(font); pwdPassword.setPreferredSize(new Dimension(200, 30)); formPanel.add(pwdPassword, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Tombol
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        btnPanel.setBackground(Color.WHITE);
        
        btnLogin = new JButton("LOGIN"); 
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14)); 
        btnLogin.setBackground(new Color(39, 174, 96)); 
        btnLogin.setForeground(Color.WHITE); 
        btnLogin.setPreferredSize(new Dimension(120, 40));
        btnLogin.setFocusPainted(false);
        
        btnKeluar = new JButton("KELUAR"); 
        btnKeluar.setFont(new Font("Tahoma", Font.BOLD, 14)); 
        btnKeluar.setBackground(new Color(192, 57, 43)); 
        btnKeluar.setForeground(Color.WHITE); 
        btnKeluar.setPreferredSize(new Dimension(120, 40));
        btnKeluar.setFocusPainted(false);

        btnLogin.addActionListener(e -> btnLoginActionPerformed());
        btnKeluar.addActionListener(e -> System.exit(0));

        btnPanel.add(btnLogin);
        btnPanel.add(btnKeluar);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void btnLoginActionPerformed() {
        String username = txtUsername.getText().trim();
        char[] password = pwdPassword.getPassword();

        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnLogin.setEnabled(false);

        try {
            User user = userDAO.authenticate(username, password);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Login gagal. Periksa username, password, atau status user.", "Login Gagal", JOptionPane.WARNING_MESSAGE);
                pwdPassword.setText("");
                pwdPassword.requestFocus();
                return;
            }

            UserSession.setCurrentUser(user);
            dispose(); // Tutup LoginFrame
            
            // Buka Dashboard
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new DashboardFrame().setVisible(true);
                }
            });

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Tidak dapat mengakses database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(password, '\0'); // Hapus password dari memori
            btnLogin.setEnabled(true);
            setCursor(Cursor.getDefaultCursor());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}