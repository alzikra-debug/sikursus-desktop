package model;

public class Instruktur extends Orang {
    
    // 1. Field khusus Instruktur
    private String keahlian;

    // 2. Constructor
    public Instruktur(int id, String nama, String noHp, String keahlian) {
        super(id, nama, noHp); // <-- [KOMENTAR]: Memanggil constructor parent
        setKeahlian(keahlian);
    }

    // --- GETTER ---
    public String getKeahlian() { return keahlian; }

    // --- SETTER DENGAN VALIDASI ---
    
    // <-- [KOMENTAR]: Validasi Keahlian tidak boleh kosong
    public void setKeahlian(String keahlian) {
        if (keahlian == null || keahlian.trim().isEmpty()) {
            throw new IllegalArgumentException("Keahlian wajib diisi");
        }
        this.keahlian = keahlian.trim();
    }
    
        // <-- [KOMENTAR]: Override getInfo() untuk menambahkan info khusus Instruktur
    @Override
    public String getInfo() {
        return "INSTRUKTUR | " + super.getInfo() + " | Keahlian: " + getKeahlian();
    }
}