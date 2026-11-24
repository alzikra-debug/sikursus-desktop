package view;

import dao.PesertaDAO;
import model.Peserta;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import report.ReportService; // <--- TAMBAHAN 1: Import ReportService

public class FormPeserta extends JFrame {

    private final PesertaDAO dao = new PesertaDAO();
    private int selectedId = -1;

    private JTextField txtNim, txtNama, txtProdi, txtNoHp, txtEmail, txtCari;
    private JRadioButton rbLaki, rbPerempuan;
    private ButtonGroup bgJenisKelamin;
    // <--- TAMBAHAN 2: Tambahkan btnCetak di deklarasi variabel
    private JButton btnSimpan, btnUbah, btnHapus, btnReset, btnKeluar, btnCari, btnCetak;
    private JTable tblPeserta;
    private DefaultTableModel tableModel;

    private final Color WARNA_HEADER = new Color(26, 82, 118);
    private final Color WARNA_AKSEN  = new Color(46, 134, 193);
    private final Color WARNA_BORDER = new Color(189, 195, 199);
    private final Color WARNA_BG     = new Color(236, 240, 241);
    private final Color WARNA_HIJAU  = new Color(39, 174, 96);
    private final Color WARNA_MERAH  = new Color(192, 57, 43);
    private final Color WARNA_ORANYE = new Color(243, 156, 18);
    private final Color WARNA_ABU    = new Color(127, 140, 141);
    private final Color WARNA_UPDATE = new Color(41, 128, 185);

    public FormPeserta() {
        initComponents();
        configureTable();
        loadTable();
        clearForm();
    }

    private void configureTable() {
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "NIM", "Nama", "JK", "Prodi", "No. HP", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblPeserta.setModel(tableModel);
        tblPeserta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblPeserta.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tblPeserta.setRowHeight(25);
        tblPeserta.getTableHeader().setBackground(WARNA_HEADER);
        tblPeserta.getTableHeader().setForeground(Color.WHITE);
        tblPeserta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    loadSelectedRowToForm();
                }
            }
        });
    }

    private void fillTable(List<Peserta> data) {
        tableModel.setRowCount(0);
        for (Peserta p : data) {
            tableModel.addRow(new Object[]{
                p.getId(), p.getNim(), p.getNama(), p.getJenisKelamin(),
                p.getProdi(), p.getNoHp(), p.getEmail()
            });
        }
    }

    private void loadTable() {
        try {
            fillTable(dao.findAll());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshData() {
        loadTable();
        clearForm();
        txtCari.setText("");
    }

    private void loadSelectedRowToForm() {
        int row = tblPeserta.getSelectedRow();
        if (row == -1) {
            return;
        }
        selectedId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
        txtNim.setText(tableModel.getValueAt(row, 1).toString());
        txtNama.setText(tableModel.getValueAt(row, 2).toString());
        String jk = tableModel.getValueAt(row, 3).toString();
        rbLaki.setSelected("L".equals(jk));
        rbPerempuan.setSelected("P".equals(jk));
        txtProdi.setText(tableModel.getValueAt(row, 4).toString());
        txtNoHp.setText(tableModel.getValueAt(row, 5).toString());
        txtEmail.setText(tableModel.getValueAt(row, 6).toString());
        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
    }

    private void clearForm() {
        selectedId = -1;
        txtNim.setText("");
        txtNama.setText("");
        bgJenisKelamin.clearSelection();
        txtProdi.setText("");
        txtNoHp.setText("");
        txtEmail.setText("");
        tblPeserta.clearSelection();
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
        txtNim.requestFocus();
    }

    private String getJenisKelamin() {
        if (rbLaki.isSelected()) {
            return "L";
        }
        if (rbPerempuan.isSelected()) {
            return "P";
        }
        return null;
    }

    private Peserta getFormData() {
        Peserta p = new Peserta();
        p.setId(selectedId);
        p.setNim(txtNim.getText().trim());
        p.setNama(txtNama.getText().trim());
        p.setJenisKelamin(getJenisKelamin());
        p.setProdi(txtProdi.getText().trim());
        p.setNoHp(txtNoHp.getText().trim());
        p.setEmail(txtEmail.getText().trim());
        return p;
    }

    private boolean validateForm() {
        if (txtNim.getText().trim().isEmpty() || txtNama.getText().trim().isEmpty()
                || txtProdi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM, Nama, dan Prodi wajib diisi.");
            return false;
        }
        if (getJenisKelamin() == null) {
            JOptionPane.showMessageDialog(this, "Pilih jenis kelamin.");
            return false;
        }
        return true;
    }

    private void btnSimpanActionPerformed() {
        if (!validateForm()) {
            return;
        }
        try {
            if (dao.insert(getFormData())) {
                JOptionPane.showMessageDialog(this, "Data peserta berhasil disimpan.");
                refreshData();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnUbahActionPerformed() {
        if (selectedId <= 0) {
            JOptionPane.showMessageDialog(this, "Pilih data peserta pada tabel terlebih dahulu.");
            return;
        }
        if (!validateForm()) {
            return;
        }
        try {
            if (dao.update(getFormData())) {
                JOptionPane.showMessageDialog(this, "Data peserta berhasil diubah.");
                refreshData();
            } else {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan atau tidak berubah.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnHapusActionPerformed() {
        if (selectedId <= 0) {
            JOptionPane.showMessageDialog(this, "Pilih data peserta yang akan dihapus.");
            return;
        }
        int jawab = JOptionPane.showConfirmDialog(this,
                "Yakin menghapus peserta: " + txtNama.getText().trim() + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (jawab != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            if (dao.delete(selectedId)) {
                JOptionPane.showMessageDialog(this, "Data peserta berhasil dihapus.");
                refreshData();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnResetActionPerformed() {
        txtCari.setText("");
        loadTable();
        clearForm();
    }

    private void btnCariActionPerformed() {
        String keyword = txtCari.getText().trim();
        try {
            if (keyword.isEmpty()) {
                loadTable();
            } else {
                fillTable(dao.search(keyword));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Pencarian gagal: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // <--- TAMBAHAN 3: Method untuk aksi tombol Cetak
    private void btnCetakActionPerformed() {
        ReportService.tampilkanLaporanPeserta();
    }

    private void btnKeluarActionPerformed() {
        if (JOptionPane.showConfirmDialog(this, "Yakin ingin keluar?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void initComponents() {
        setTitle("FORM DATA PESERTA SIKURSUS");
        setSize(760, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        header.setBackground(WARNA_HEADER);
        header.setPreferredSize(new Dimension(760, 70));
        JLabel judul = new JLabel("FORM DATA PESERTA SIKURSUS");
        judul.setForeground(Color.WHITE);
        judul.setFont(new Font("Tahoma", Font.BOLD, 18));
        header.add(judul);
        add(header, BorderLayout.NORTH);

        JPanel in = new JPanel(new GridBagLayout());
        in.setBackground(Color.WHITE);
        in.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(8, 0, 8, 0);
        Font fl = new Font("Tahoma", Font.PLAIN, 13);
        Dimension sz = new Dimension(280, 32);

        g.gridx = 0; g.gridy = 0; g.weightx = 0.35; in.add(lbl("NIM", fl), g);
        g.gridx = 1; g.weightx = 0.65; txtNim = fld(sz); in.add(txtNim, g);
        g.gridx = 0; g.gridy = 1; g.weightx = 0.35; in.add(lbl("Nama Lengkap", fl), g);
        g.gridx = 1; g.weightx = 0.65; txtNama = fld(sz); in.add(txtNama, g);
        g.gridx = 0; g.gridy = 2; g.weightx = 0.35; in.add(lbl("Jenis Kelamin", fl), g);
        g.gridx = 1; g.weightx = 0.65;
        JPanel jk = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        jk.setBackground(Color.WHITE);
        rbLaki = new JRadioButton("Laki-laki");
        rbPerempuan = new JRadioButton("Perempuan");
        rbLaki.setFont(fl);
        rbPerempuan.setFont(fl);
        rbLaki.setBackground(Color.WHITE);
        rbPerempuan.setBackground(Color.WHITE);
        bgJenisKelamin = new ButtonGroup();
        bgJenisKelamin.add(rbLaki);
        bgJenisKelamin.add(rbPerempuan);
        jk.add(rbLaki);
        jk.add(Box.createHorizontalStrut(20));
        jk.add(rbPerempuan);
        in.add(jk, g);
        g.gridx = 0; g.gridy = 3; g.weightx = 0.35; in.add(lbl("Program Studi", fl), g);
        g.gridx = 1; g.weightx = 0.65; txtProdi = fld(sz); in.add(txtProdi, g);
        g.gridx = 0; g.gridy = 4; g.weightx = 0.35; in.add(lbl("No. HP", fl), g);
        g.gridx = 1; g.weightx = 0.65; txtNoHp = fld(sz); in.add(txtNoHp, g);
        g.gridx = 0; g.gridy = 5; g.weightx = 0.35; in.add(lbl("Email", fl), g);
        g.gridx = 1; g.weightx = 0.65; txtEmail = fld(sz); in.add(txtEmail, g);

        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        sp.setTopComponent(in);
        sp.setDividerLocation(330);
        sp.setResizeWeight(0.45);

        JPanel panelTabel = new JPanel(new BorderLayout());
        panelTabel.setBackground(Color.WHITE);
        panelTabel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(WARNA_AKSEN, 2),
                "DAFTAR PESERTA (Double-click untuk Edit)", 0, 0,
                new Font("Tahoma", Font.BOLD, 13), WARNA_AKSEN));

        JPanel panelCari = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelCari.setBackground(Color.WHITE);
        JLabel lc = new JLabel("Cari Nama / NIM:");
        lc.setFont(fl);
        txtCari = fld(new Dimension(220, 32));
        btnCari = btn("CARI", WARNA_AKSEN);
        btnCari.setPreferredSize(new Dimension(80, 32));
        btnCari.addActionListener(e -> btnCariActionPerformed());
        panelCari.add(lc);
        panelCari.add(txtCari);
        panelCari.add(btnCari);
        panelTabel.add(panelCari, BorderLayout.NORTH);

        tblPeserta = new JTable();
        panelTabel.add(new JScrollPane(tblPeserta), BorderLayout.CENTER);
        sp.setBottomComponent(panelTabel);
        add(sp, BorderLayout.CENTER);

        JPanel bawah = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        bawah.setBackground(WARNA_BG);
        bawah.setPreferredSize(new Dimension(760, 80));
        btnSimpan = btn("SIMPAN", WARNA_HIJAU);
        btnUbah   = btn("UBAH",   WARNA_UPDATE);
        btnHapus  = btn("HAPUS",  WARNA_MERAH);
        btnReset  = btn("RESET",  WARNA_ORANYE);
        btnCetak  = btn("CETAK",  WARNA_HEADER); // <--- TAMBAHAN 4: Buat tombol Cetak
        btnKeluar = btn("KELUAR", WARNA_ABU);
        
        btnSimpan.addActionListener(e -> btnSimpanActionPerformed());
        btnUbah.addActionListener(e -> btnUbahActionPerformed());
        btnHapus.addActionListener(e -> btnHapusActionPerformed());
        btnReset.addActionListener(e -> btnResetActionPerformed());
        btnCetak.addActionListener(e -> btnCetakActionPerformed()); // <--- TAMBAHAN 5: Pasang Listener
        btnKeluar.addActionListener(e -> btnKeluarActionPerformed());
        
        bawah.add(btnSimpan);
        bawah.add(btnUbah);
        bawah.add(btnHapus);
        bawah.add(btnReset);
        bawah.add(btnCetak); // <--- TAMBAHAN 6: Masukkan ke panel bawah
        bawah.add(btnKeluar);
        add(bawah, BorderLayout.SOUTH);
    }

    private JLabel lbl(String t, Font f) {
        JLabel l = new JLabel(t);
        l.setFont(f);
        return l;
    }

    private JTextField fld(Dimension s) {
        JTextField f = new JTextField();
        f.setFont(new Font("Tahoma", Font.PLAIN, 13));
        f.setPreferredSize(s);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(WARNA_BORDER, 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        return f;
    }

    private JButton btn(String t, Color c) {
        JButton b = new JButton(t);
        b.setFont(new Font("Tahoma", Font.BOLD, 13));
        b.setForeground(Color.WHITE);
        b.setBackground(c);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(120, 38));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

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