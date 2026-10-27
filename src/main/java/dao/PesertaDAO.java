package dao;

import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Peserta;

public class PesertaDAO {

    public boolean insert(Peserta p) throws SQLException {
        String sql = "INSERT INTO peserta (nim, nama, jenis_kelamin, prodi, no_hp, email) VALUES(?,?,?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNim());
            ps.setString(2, p.getNama());
            ps.setString(3, p.getJenisKelamin());
            ps.setString(4, p.getProdi());
            ps.setString(5, p.getNoHp());
            ps.setString(6, p.getEmail());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean update(Peserta p) throws SQLException {
        String sql = "UPDATE peserta SET nim=?, nama=?, jenis_kelamin=?, prodi=?, no_hp=?, email=? WHERE id_peserta=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNim());
            ps.setString(2, p.getNama());
            ps.setString(3, p.getJenisKelamin());
            ps.setString(4, p.getProdi());
            ps.setString(5, p.getNoHp());
            ps.setString(6, p.getEmail());
            ps.setInt(7, p.getIdPeserta());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(int idPeserta) throws SQLException {
        String sql = "DELETE FROM peserta WHERE id_peserta=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPeserta);
            return ps.executeUpdate() == 1;
        }
    }

    public List<Peserta> getAll() throws SQLException {
        List<Peserta> listPeserta = new ArrayList<>();
        String sql = "SELECT id_peserta, nim, nama, jenis_kelamin, prodi, no_hp, email FROM peserta ORDER BY id_peserta DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Peserta p = new Peserta(
                        rs.getString("nim"), rs.getString("nama"), rs.getString("jenis_kelamin"),
                        rs.getString("prodi"), rs.getString("no_hp"), rs.getString("email"));
                p.setIdPeserta(rs.getInt("id_peserta"));
                listPeserta.add(p);
            }
        }
        return listPeserta;
    }
}