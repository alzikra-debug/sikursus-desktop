package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // Konfigurasi default XAMPP
    private static final String URL = "jdbc:mysql://localhost:3306/db_sikursus";
    private static final String USER = "root";
    private static final String PASS = ""; // Password kosong untuk XAMPP default
    
    // Constructor dikosongkan agar class ini tidak bisa di-instantiate sembarangan
    private Database() {
    }
    
    // Method untuk mendapatkan koneksi ke database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}