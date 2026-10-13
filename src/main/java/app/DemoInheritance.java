package app;

import java.util.ArrayList;
import model.Orang;
import model.Peserta;
import model.Instruktur;

public class DemoInheritance {
    public static void main(String[] args) {
        // Membuat list dengan tipe data parent (Orang)
        ArrayList<Orang> daftarOrang = new ArrayList<>();

        // 1. Menambahkan 3 object Peserta (Child class)
        daftarOrang.add(new Peserta(1, "Alya Rahma", "081234567890", "252001", "Informatika"));
        daftarOrang.add(new Peserta(2, "Rafi Akbar", "081298765432", "252002", "Informatika"));
        daftarOrang.add(new Peserta(3, "Budi Santoso", "081255556666", "252003", "Sistem Informasi"));

        // 2. Menambahkan 2 object Instruktur (Child class)
        daftarOrang.add(new Instruktur(101, "Dina Pratama", "081211110001", "Java Desktop"));
        daftarOrang.add(new Instruktur(102, "Rizal Maulana", "081211110002", "Data Science"));

        // 3. Menampilkan semua data menggunakan loop (Polymorphism sederhana)
        System.out.println("=== DATA SIKURSUS ===");
        for (Orang orang : daftarOrang) {
            System.out.println(orang.getInfo());
        }

        // 4. Tes Setter Parent (Mengubah data menggunakan method warisan dari class Orang)
        System.out.println("\n--- Tes Perubahan Data (Setter Parent) ---");
        Peserta p1 = (Peserta) daftarOrang.get(0); // Casting ke Peserta
        p1.setNama("Alya Rahma Putri"); // Mengubah nama via method parent
        System.out.println("Nama Peserta 1 setelah diubah: " + p1.getNama());
        System.out.println("Info lengkap setelah diubah: " + p1.getInfo());
    }
}