package model;

public class Orang {
    private int id;
    private String nama;
    private String noHp;

    // 1. CONSTRUCTOR KOSONG (WAJIB ADA untuk Praktikum 10 / DAO)
    public Orang() {
    }

    // 2. Constructor Berparameter (Untuk kompatibilitas kode lama)
    public Orang(int id, String nama, String noHp) {
        setId(id);
        setNama(nama);
        setNoHp(noHp);
    }

    // --- GETTER ---
    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getNoHp() { return noHp; }

    // --- SETTER (dengan validasi) ---
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID tidak boleh negatif");
        }
        this.id = id;
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama wajib diisi");
        }
        this.nama = nama.trim();
    }

    public void setNoHp(String noHp) {
        String nilai = noHp == null ? "" : noHp.trim();
        if (!nilai.isEmpty() && !nilai.matches("[0-9+\\-]{8,20}")) {
            throw new IllegalArgumentException("Format nomor HP tidak valid");
        }
        this.noHp = nilai;
    }

    public String getInfo() {
        return getNama() + " | HP:" + (getNoHp().isEmpty() ? "-" : getNoHp());
    }
}