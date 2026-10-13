package model;

public class Peserta extends Orang {
    
    // 1. Ubah field khusus Peserta menjadi private
    private String nim;
    private String prodi;

    // 2. Constructor: Panggil constructor parent (Orang), lalu pakai setter sendiri
    public Peserta(int id, String nama, String noHp, String nim, String prodi) {
        super(id, nama, noHp); // <-- [WAJIB KOMENTAR]: Memanggil constructor parent untuk validasi id, nama, noHp
        setNim(nim);
        setProdi(prodi);
    }

    // --- GETTER ---
    public String getNim() { return nim; }
    public String getProdi() { return prodi; }

    // --- SETTER DENGAN VALIDASI ---
    
    // <-- [KOMENTAR]: Validasi NIM tidak boleh kosong
    public void setNim(String nim) {
        if (nim == null || nim.trim().isEmpty()) {
            throw new IllegalArgumentException("NIM wajib diisi");
        }
        this.nim = nim.trim();
    }

    // <-- [KOMENTAR]: Validasi Prodi tidak boleh kosong
    public void setProdi(String prodi) {
        if (prodi == null || prodi.trim().isEmpty()) {
            throw new IllegalArgumentException("Prodi wajib diisi");
        }
        this.prodi = prodi.trim();
    }
        // <-- [KOMENTAR]: Override getInfo() untuk menambahkan info khusus Peserta
    @Override
    
    public String getInfo() {
        // super.getInfo() memanggil method getInfo() milik parent (Orang) agar tidak perlu menulis ulang
        return "PESERTA | " + super.getInfo() + " | NIM: " + getNim() + " | Prodi: " + getProdi();
    }
}