package id.ac.uinbukittinggi.sikursus.desktop;

import model.Peserta;

public class UjiValidasi {
    public static void main(String[] args) {
        System.out.println("--- Uji Validasi ---");
        
        // Uji 1: Nama kosong (harus ditolak)
        try {
            Peserta p = new Peserta(1, "", "081234567890", "252001", "Informatika");
            System.out.println("Nama kosong: DITERIMA (SALAH!)");
        } catch (IllegalArgumentException e) {
            System.out.println("Nama kosong: DITOLAK - " + e.getMessage());
        }
        
        // Uji 2: ID negatif (harus ditolak)
        try {
            Peserta p2 = new Peserta(-1, "Ahmad", "081234567890", "252001", "Informatika");
            System.out.println("ID negatif: DITERIMA (SALAH!)");
        } catch (IllegalArgumentException e) {
            System.out.println("ID negatif: DITOLAK - " + e.getMessage());
        }
        
        // Uji 3: No HP invalid (harus ditolak)
        try {
            Peserta p3 = new Peserta(1, "Ahmad", "abc", "252001", "Informatika");
            System.out.println("No HP invalid: DITERIMA (SALAH!)");
        } catch (IllegalArgumentException e) {
            System.out.println("No HP invalid: DITOLAK - " + e.getMessage());
        }
    }
}