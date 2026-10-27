package view;

import dao.PesertaDAO;
import model.Peserta;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class FormPeserta extends JFrame {

    private JTextField txtNim, txtNama, txtProdi, txtNoHp, txtEmail;
    private JRadioButton rbLaki, rbPerempuan;
    private ButtonGroup buttonGroupJenisKelamin;
    private JButton btnSimpan, btnHapus, btnReset, btnKeluar;

    private JTable tablePeserta;
    private DefaultTableModel tableModel;
    private int selectedId = -1;

    // Warna tema
    private final Color WARNA_HEADER = new Color(26, 82, 118);
    private final Color WARNA_AKSEN = new Color(46, 134, 193);
    private final Color WARNA_BORDER = new Color(189, 195, 199);
    private final Color WARNA_BG_BAWAH = new Color(236, 240, 241);
    private final Color WARNA_HIJAU = new Color(39, 174, 96);
    private final Color WARNA_MERAH = new Color(192, 57, 43);
    private final Color WARNA_ORANYE = new Color(243, 156, 18);
    private final Color WARNA_ABU = new Color(127, 140, 141);
    private final Color WARNA_BIRU_UPDATE = new Color(41, 128, 185);

    public FormPeserta() {
        initComponents();
        initTable();
        refreshTable();
    }

    private void initComponents() {
        setTitle("FORM DATA PESERTA SIKURSUS");
        setSize(750, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // ===== PANEL HEADER =====
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(WARNA_HEADER);
        panelHeader.setPreferredSize(new Dimension(750, 70));
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        JLabel lblJudul = new JLabel("FORM DATA PESERTA SIKURSUS");
        lblJudul.setForeground(Color.WHITE);
        lblJudul.setFont(new Font("Tahoma", Font.BOLD, 18));
        panelHeader.add(lblJudul);
        add(panelHeader, BorderLayout.NORTH);

        // ===== PANEL INPUT =====
        JPanel panelInput = new JPanel();
        panelInput.setBackground(Color.WHITE);
        panelInput.setLayout(new GridBagLayout());
        panelInput.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        Font fontLabel = new Font("Tahoma", Font.PLAIN, 13);
        Dimension sizeField = new Dimension(280, 32);

        // Baris 1: NIM
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.35;
        panelInput.add(buatLabel("NIM", fontLabel), gbc);
        gbc.gridx = 1; gbc.weightx = 0.65;
        txtNim = buatField(sizeField);
        panelInput.add(txtNim, gbc);

        // Baris 2: Nama
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.35;
        panelInput.add(buatLabel("Nama Lengkap", fontLabel), gbc);
        gbc.gridx = 1; gbc.weightx = 0.65;
        txtNama = buatField(sizeField);
        panelInput.add(txtNama, gbc);

        // Baris 3: Jenis Kelamin
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.35;
        panelInput.add(buatLabel("Jenis Kelamin", fontLabel), gbc);
        gbc.gridx = 1; gbc.weightx = 0.65;
        JPanel panelJK = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelJK.setBackground(Color.WHITE);
        rbLaki = new JRadioButton("Laki-laki");
        rbPerempuan = new JRadioButton("Perempuan");
        rbLaki.setFont(fontLabel);
        rbPerempuan.setFont(fontLabel);
        rbLaki.setBackground(Color.WHITE);
        rbPerempuan.setBackground(Color.WHITE);
        buttonGroupJenisKelamin = new ButtonGroup();
        buttonGroupJenisKelamin.add(rbLaki);
        buttonGroupJenisKelamin.add(rbPerempuan);
        panelJK.add(rbLaki);
        panelJK.add(Box.createHorizontalStrut(20));
        panelJK.add(rbPerempuan);
        panelInput.add(panelJK, gbc);

        // Baris 4: Prodi
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.35;
        panelInput.add(buatLabel("Program Studi", fontLabel), gbc);
        gbc.gridx = 1; gbc.weightx = 0.65;
        txtProdi = buatField(sizeField);
        panelInput.add(txtProdi, gbc);

        // Baris 5: No HP
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.35;
        panelInput.add(buatLabel("No. HP", fontLabel), gbc);
        gbc.gridx = 1; gbc.weightx = 0.65;
        txtNoHp = buatField(sizeField);
        panelInput.add(txtNoHp, gbc);

        // Baris 6: Email
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.35;
        panelInput.add(buatLabel("Email", fontLabel), gbc);
        gbc.gridx = 1; gbc.weightx = 0.65;
        txtEmail = buatField(sizeField);
        panelInput.add(txtEmail, gbc);

        // ===== JSplitPane =====
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(panelInput);
        splitPane.setDividerLocation(320);
        splitPane.setResizeWeight(0.45);

        JPanel panelTabel = new JPanel(new BorderLayout());
        panelTabel.setBackground(Color.WHITE);
        panelTabel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(WARNA_AKSEN, 2),
            "DAFTAR PESERTA (Double-click untuk Edit)",
            0, 0,
            new Font("Tahoma", Font.BOLD, 13),
            WARNA_AKSEN
        ));
        splitPane.setBottomComponent(panelTabel);
        add(splitPane, BorderLayout.CENTER);

        // ===== PANEL TOMBOL =====
        JPanel panelBawah = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelBawah.setBackground(WARNA_BG_BAWAH);
        panelBawah.setPreferredSize(new Dimension(750, 80));

        btnSimpan = buatTombol("SIMPAN", WARNA_HIJAU);
        btnHapus = buatTombol("HAPUS", WARNA_MERAH);
        btnReset = buatTombol("RESET", WARNA_ORANYE);
        btnKeluar = buatTombol("KELUAR", WARNA_ABU);

        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSimpanActionPerformed();
            }
        });
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnHapusActionPerformed();
            }
        });
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnResetActionPerformed();
            }
        });
        btnKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnKeluarActionPerformed();
            }
        });

        panelBawah.add(btnSimpan);
        panelBawah.add(btnHapus);
        panelBawah.add(btnReset);
        panelBawah.add(btnKeluar);
        add(panelBawah, BorderLayout.SOUTH);
    }

    // ===== HELPER METHODS (Level Class - Kompatibel Java 8) =====
    private JLabel buatLabel(String teks, Font font) {
        JLabel lbl = new JLabel(teks);
        lbl.setFont(font);
        return lbl;
    }

    private JTextField buatField(Dimension size) {
        JTextField f = new JTextField();
        f.setFont(new Font("Tahoma", Font.PLAIN, 13));
        f.setPreferredSize(size);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WARNA_BORDER, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        return f;
    }

    private JButton buatTombol(String teks, Color warna) {
        JButton btn = new JButton(teks);
        btn.setFont(new Font("Tahoma", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(warna);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(120, 38));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ===== INISIALISASI TABEL =====
    private void initTable() {
        String[] kolom = {"ID", "NIM", "Nama", "JK", "Prodi", "No HP", "Email"};
        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablePeserta = new JTable(tableModel);
        tablePeserta.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tablePeserta.setRowHeight(25);
        tablePeserta.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tablePeserta.getTableHeader().setBackground(WARNA_HEADER);
        tablePeserta.getTableHeader().setForeground(Color.WHITE);
        tablePeserta.setSelectionBackground(new Color(174, 214, 241));

        tablePeserta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    loadSelectedRowToForm();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablePeserta);
        JSplitPane splitPane = (JSplitPane) getContentPane().getComponent(1);
        splitPane.setBottomComponent(scrollPane);
    }

    // ===== LOAD DATA DARI DATABASE =====
    private void refreshTable() {
        PesertaDAO dao = new PesertaDAO();
        try {
            List<Peserta> daftar = dao.getAll();
            tableModel.setRowCount(0);
            for (Peserta p : daftar) {
                tableModel.addRow(new Object[]{
                    p.getIdPeserta(),
                    p.getNim(),
                    p.getNama(),
                    p.getJenisKelamin(),
                    p.getProdi(),
                    p.getNoHp(),
                    p.getEmail()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Gagal memuat data:\n" + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // ===== LOAD BARIS TERPILIH KE FORM =====
    private void loadSelectedRowToForm() {
        int selectedRow = tablePeserta.getSelectedRow();
        if (selectedRow == -1) return;

        selectedId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        txtNim.setText(tableModel.getValueAt(selectedRow, 1).toString());
        txtNama.setText(tableModel.getValueAt(selectedRow, 2).toString());

        String jk = tableModel.getValueAt(selectedRow, 3).toString();
        if ("L".equals(jk)) {
            rbLaki.setSelected(true);
        } else if ("P".equals(jk)) {
            rbPerempuan.setSelected(true);
        }

        txtProdi.setText(tableModel.getValueAt(selectedRow, 4).toString());
        txtNoHp.setText(tableModel.getValueAt(selectedRow, 5).toString());
        txtEmail.setText(tableModel.getValueAt(selectedRow, 6).toString());

        // Ubah mode jadi UPDATE
        btnSimpan.setText("UPDATE");
        btnSimpan.setBackground(WARNA_BIRU_UPDATE);
    }

    // ===== CLEAR FORM =====
    private void clearForm() {
        txtNim.setText("");
        txtNama.setText("");
        buttonGroupJenisKelamin.clearSelection();
        txtProdi.setText("");
        txtNoHp.setText("");
        txtEmail.setText("");
        selectedId = -1;
        btnSimpan.setText("SIMPAN");
        btnSimpan.setBackground(WARNA_HIJAU);
        txtNim.requestFocus();
    }

    // ===== EVENT HANDLER: SIMPAN/UPDATE =====
    private void btnSimpanActionPerformed() {
        String nim = txtNim.getText().trim();
        String nama = txtNama.getText().trim();
        String prodi = txtProdi.getText().trim();
        String noHp = txtNoHp.getText().trim();
        String email = txtEmail.getText().trim();

        String jenisKelamin = "";
        if (rbLaki.isSelected()) {
            jenisKelamin = "L";
        } else if (rbPerempuan.isSelected()) {
            jenisKelamin = "P";
        }

        // Validasi wajib
        if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty() || jenisKelamin.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "NIM, nama, jenis kelamin, dan prodi wajib diisi.",
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi email
        if (!email.isEmpty() && !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            JOptionPane.showMessageDialog(this,
                "Format email tidak valid. Contoh: nama@domain.com",
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi no HP
        if (!noHp.isEmpty() && !noHp.matches("^[0-9+\\-]+$")) {
            JOptionPane.showMessageDialog(this,
                "Nomor HP hanya boleh berisi angka, +, atau -.",
                "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Peserta peserta = new Peserta(nim, nama, jenisKelamin, prodi, noHp, email);
        if (selectedId != -1) {
            peserta.setIdPeserta(selectedId);
        }
        PesertaDAO dao = new PesertaDAO();

        try {
            boolean berhasil;
            if (selectedId != -1) {
                // Mode UPDATE
                berhasil = dao.update(peserta);
                JOptionPane.showMessageDialog(this, "Data peserta berhasil diperbarui.");
            } else {
                // Mode INSERT
                berhasil = dao.insert(peserta);
                JOptionPane.showMessageDialog(this, "Data peserta berhasil disimpan.");
            }

            if (berhasil) {
                clearForm();
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Operasi gagal.", "Informasi", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Gagal: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // ===== EVENT HANDLER: HAPUS =====
    private void btnHapusActionPerformed() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this,
                "Pilih data dari tabel terlebih dahulu untuk dihapus.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus data ini?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (new PesertaDAO().delete(selectedId)) {
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                    clearForm();
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Gagal menghapus data.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                    "Gagal menghapus: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    // ===== EVENT HANDLER: RESET =====
    private void btnResetActionPerformed() {
        clearForm();
    }

    // ===== EVENT HANDLER: KELUAR =====
    private void btnKeluarActionPerformed() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // ===== MAIN METHOD =====
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormPeserta().setVisible(true);
            }
        });
    }
}