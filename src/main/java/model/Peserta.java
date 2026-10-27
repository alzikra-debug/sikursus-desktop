package model;

public class Peserta {
    private int idPeserta;
    private String nim;
    private String nama;
    private String jenisKelamin;
    private String prodi;
    private String noHp;
    private String email;

    // Constructor
    public Peserta(String nim, String nama, String jenisKelamin, String prodi, String noHp, String email) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.prodi = prodi;
        this.noHp = noHp;
        this.email = email;
    }

    // --- GETTER & SETTER ---
    public int getIdPeserta() { return idPeserta; }
    public void setIdPeserta(int idPeserta) { this.idPeserta = idPeserta; }
    
    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }
    
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    
    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    
    public String getProdi() { return prodi; }
    public void setProdi(String prodi) { this.prodi = prodi; }
    
    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}