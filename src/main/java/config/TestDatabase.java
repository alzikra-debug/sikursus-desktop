package config;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDatabase {
    public static void main(String[] args) {
        // Menggunakan try-with-resources agar koneksi otomatis tertutup
        try (Connection conn = Database.getConnection()) {
            if (conn != null && conn.isValid(2)) {
                System.out.println("✅ KONEKSI BERHASIL ke db_sikursus");
            } else {
                System.out.println("❌ Koneksi terbentuk tetapi tidak valid.");
            }
        } catch (SQLException e) {
            System.out.println("❌ KONEKSI GAGAL!");
            System.out.println("Pesan Error: " + e.getMessage());
        }
    }
}