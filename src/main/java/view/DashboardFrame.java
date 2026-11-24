package view;

import java.awt.*;
import java.net.URL;
import javax.swing.*;
import model.User;
import util.UserSession;
import util.WindowManager;

public class DashboardFrame extends JFrame {

    // ============================================================
    // BAGIAN INI WAJIB ADA - JANGAN DIHAPUS/DILEWATKAN
    // Semua field class dideklarasikan di sini
    // ============================================================
    private JLabel lblUser, lblRole;
    private JMenu menuMaster, menuTransaksi, menuLaporan, menuKeluar;
    private JMenuItem miPeserta, miInstruktur, miKursus, miKelas;
    private JMenuItem miPendaftaran, miPembayaran;
    private JMenuItem miLaporanPeserta, miLaporanPendaftaran;
    private JMenuItem miLogout, miTutup;
    
    // Field untuk Popup Menu (WAJIB - ini yang sering terpotong)
    private JPopupMenu popupDashboard;
    private JMenuItem miTentang, miRefresh;

    // Theme Colors
    private final Color WARNA_HEADER = new Color(26, 82, 118);
    private final Color WARNA_AKSEN  = new Color(46, 134, 193);
    private final Color WARNA_BG     = new Color(236, 240, 241);

    public DashboardFrame() {
        initComponents();
        setLocationRelativeTo(null);
        applySession();
        initPopupMenu();
    }

    // ===== 1. INIT COMPONENTS =====
    private void initComponents() {
        setTitle("Dashboard - SIKURSUS Desktop");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- JMenuBar ---
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(WARNA_HEADER);

        menuMaster = new JMenu("Master");
        menuMaster.setForeground(Color.WHITE);
        miPeserta     = new JMenuItem("Peserta");
        miInstruktur  = new JMenuItem("Instruktur");
        miKursus      = new JMenuItem("Kursus");
        miKelas       = new JMenuItem("Kelas");
        menuMaster.add(miPeserta);
        menuMaster.add(miInstruktur);
        menuMaster.add(miKursus);
        menuMaster.add(miKelas);
        menuBar.add(menuMaster);

        menuTransaksi = new JMenu("Transaksi");
        menuTransaksi.setForeground(Color.WHITE);
        miPendaftaran = new JMenuItem("Pendaftaran");
        miPembayaran  = new JMenuItem("Pembayaran");
        menuTransaksi.add(miPendaftaran);
        menuTransaksi.add(miPembayaran);
        menuBar.add(menuTransaksi);

        menuLaporan = new JMenu("Laporan");
        menuLaporan.setForeground(Color.WHITE);
        miLaporanPeserta     = new JMenuItem("Laporan Peserta");
        miLaporanPendaftaran = new JMenuItem("Laporan Pendaftaran");
        menuLaporan.add(miLaporanPeserta);
        menuLaporan.add(miLaporanPendaftaran);
        menuBar.add(menuLaporan);

        menuKeluar = new JMenu("Keluar");
        menuKeluar.setForeground(Color.WHITE);
        miLogout = new JMenuItem("Logout");
        miTutup  = new JMenuItem("Tutup Aplikasi");
        menuKeluar.add(miLogout);
        menuKeluar.addSeparator();
        menuKeluar.add(miTutup);
        menuBar.add(menuKeluar);

        setJMenuBar(menuBar);

        // --- Load Icon dari Resource ---
        miPeserta.setIcon(loadIcon("/icons/peserta.png"));
        miInstruktur.setIcon(loadIcon("/icons/instruktur.png"));
        miKursus.setIcon(loadIcon("/icons/kursus.png"));
        miKelas.setIcon(loadIcon("/icons/kelas.png"));

        // --- Event Handler Menu ---
        miPeserta.addActionListener(e ->
            WindowManager.open(FormPeserta.class, FormPeserta::new));
        miInstruktur.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Modul Instruktur akan dikembangkan pada tahap project berikutnya.",
                "Info Modul", JOptionPane.INFORMATION_MESSAGE));
        miKursus.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Modul Kursus akan dikembangkan pada tahap project berikutnya.",
                "Info Modul", JOptionPane.INFORMATION_MESSAGE));
        miKelas.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Modul Kelas akan dikembangkan pada tahap project berikutnya.",
                "Info Modul", JOptionPane.INFORMATION_MESSAGE));
        miPendaftaran.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Modul Pendaftaran sedang dikembangkan pada tahap project berikutnya.",
                "Info Modul", JOptionPane.INFORMATION_MESSAGE));
        miPembayaran.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Modul Pembayaran sedang dikembangkan pada tahap project berikutnya.",
                "Info Modul", JOptionPane.INFORMATION_MESSAGE));
        miLaporanPeserta.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Modul laporan diaktifkan pada Pertemuan 12 dengan Jaspersoft Studio.",
                "Info Laporan", JOptionPane.INFORMATION_MESSAGE));
        miLaporanPendaftaran.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Modul laporan diaktifkan pada Pertemuan 12 dengan Jaspersoft Studio.",
                "Info Laporan", JOptionPane.INFORMATION_MESSAGE));
        miLogout.addActionListener(e -> miLogoutActionPerformed());
        miTutup.addActionListener(e -> miTutupActionPerformed());

        // --- Center Panel ---
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 40));
        headerPanel.setBackground(WARNA_BG);
        JLabel lblTitle = new JLabel("Selamat Datang di SIKURSUS Desktop");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setForeground(WARNA_HEADER);
        headerPanel.add(lblTitle);
        centerPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        infoPanel.setBackground(Color.WHITE);
        lblUser = new JLabel("User: -");
        lblRole = new JLabel("Role: -");
        lblUser.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblRole.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblUser.setForeground(WARNA_AKSEN);
        lblRole.setForeground(WARNA_AKSEN);
        infoPanel.add(lblUser);
        infoPanel.add(Box.createHorizontalStrut(30));
        infoPanel.add(lblRole);
        centerPanel.add(infoPanel, BorderLayout.CENTER);

        // --- Panel Hint Popup ---
        JPanel hintPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        hintPanel.setBackground(WARNA_BG);
        JLabel lblHint = new JLabel("Tip: Klik kanan di area dashboard untuk Popup Menu");
        lblHint.setFont(new Font("Tahoma", Font.ITALIC, 13));
        lblHint.setForeground(new Color(100, 100, 100));
        hintPanel.add(lblHint);
        centerPanel.add(hintPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    // ===== 2. APPLY SESSION - Role Based Access =====
    private void applySession() {
        User user = UserSession.getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this,
                "Session tidak ditemukan. Silakan login ulang.",
                "Session Expired", JOptionPane.WARNING_MESSAGE);
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginFrame().setVisible(true);
                }
            });
            return;
        }

        lblUser.setText("User: " + user.getUsername());
        lblRole.setText("Role: " + user.getRole());

        boolean admin = user.isAdmin();
        miInstruktur.setEnabled(admin);
        miKursus.setEnabled(admin);
        miKelas.setEnabled(admin);
        miPeserta.setEnabled(true);
        menuTransaksi.setEnabled(true);
        menuLaporan.setEnabled(true);
    }

    // ===== 3. LOAD ICON PORTABLE =====
    private ImageIcon loadIcon(String path) {
        URL url = getClass().getResource(path);
        if (url == null) {
            return null;
        }
        ImageIcon original = new ImageIcon(url);
        Image scaled = original.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    // ===== 4. POPUP MENU =====
    private void initPopupMenu() {
        popupDashboard = new JPopupMenu();
        miTentang = new JMenuItem("Tentang Aplikasi");
        miRefresh = new JMenuItem("Refresh Dashboard");

        miTentang.addActionListener(e -> miTentangActionPerformed());
        miRefresh.addActionListener(e -> miRefreshActionPerformed());

        popupDashboard.add(miTentang);
        popupDashboard.add(miRefresh);

           ((JComponent) getContentPane()).setComponentPopupMenu(popupDashboard);
    }

    // ===== 5. EVENT HANDLER POPUP =====
    private void miTentangActionPerformed() {
        JOptionPane.showMessageDialog(this,
            "SIKURSUS Desktop\n" +
            "Project PBO - Program Studi Informatika\n" +
            "UIN Sjech M. Djamil Djambek Bukittinggi\n\n" +
            "Oleh: Mhd. Abdi Al Zikra (2924037)",
            "Tentang Aplikasi",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void miRefreshActionPerformed() {
        applySession();
        JOptionPane.showMessageDialog(this,
            "Dashboard berhasil di-refresh.",
            "Refresh",
            JOptionPane.INFORMATION_MESSAGE);
    }

    // ===== 6. LOGOUT =====
    private void miLogoutActionPerformed() {
        int pilihan = JOptionPane.showConfirmDialog(this,
            "Logout dari aplikasi?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            WindowManager.closeAll();
            UserSession.clear();
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginFrame().setVisible(true);
                }
            });
        }
    }

    // ===== 7. TUTUP APLIKASI =====
    private void miTutupActionPerformed() {
        int pilihan = JOptionPane.showConfirmDialog(this,
            "Tutup aplikasi SIKURSUS?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}