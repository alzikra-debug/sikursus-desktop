package model; // Sesuaikan dengan package model Anda

public class Orang {
    
    // 1. Ubah field menjadi private (Information Hiding)
    private int id;
    private String nama;
    private String noHp;

    // Constructor: Memanggil setter agar validasi tidak diduplikasi
    public Orang(int id, String nama, String noHp) {
        setId(id);
        setNama(nama);
        setNoHp(noHp);
    }

    // --- GETTER (Hanya membuka data yang perlu dibaca) ---
    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getNoHp() { return noHp; }

    // --- SETTER (Gerbang validasi state) ---
    
    // <-- [KOMENTAR]: Validasi ID tidak boleh negatif
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID tidak boleh negatif");
        }
        this.id = id;
    }

    // <-- [KOMENTAR]: Validasi nama wajib diisi
    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama wajib diisi");
        }
        this.nama = nama.trim();
    }

    // <-- [KOMENTAR]: Validasi format No HP menggunakan Regex sederhana
    public void setNoHp(String noHp) {
        String nilai = noHp == null ? "" : noHp.trim();
        if (!nilai.isEmpty() && !nilai.matches("[0-9+\\-]{8,20}")) {
            throw new IllegalArgumentException("Format nomor HP tidak valid");
        }
        this.noHp = nilai;
    }

    // Method getInfo() dasar (Nanti akan di-override oleh subclass)
    public String getInfo() {
        return getNama() + " | HP:" + (getNoHp().isEmpty() ? "-" : getNoHp());
    }
}