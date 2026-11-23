package model;

public class Peserta extends Orang {
    private String nim;
    private String jenisKelamin;
    private String prodi;
    private String email;

    // 1. CONSTRUCTOR KOSONG (WAJIB ADA agar DAO bisa instantiate)
    public Peserta() {
        super(); // Memanggil constructor kosong Orang
    }

    // 2. CONSTRUCTOR BERPARAMETER (DIPERTAHANKAN agar file Demo lama TIDAK ERROR)
    public Peserta(String nim, String nama, String jenisKelamin, String prodi, String noHp, String email) {
        super(0, nama, noHp); // Memanggil constructor Orang (id=0 sementara)
        this.nim = nim;
        this.jenisKelamin = jenisKelamin;
        this.prodi = prodi;
        this.email = email;
    }

    // --- GETTER & SETTER KHUSUS PESERTA ---
    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }
    
    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    
    public String getProdi() { return prodi; }
    public void setProdi(String prodi) { this.prodi = prodi; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}