package latihan;

public class LatihanBiayaKursus {    //class
    public static void main(String[] args) {    //main method
        
        //variabel
        String kode = "JAVA-BSC";
        String nama = "Java Desktop Fundamental";
        
        double biaya = 3_828_000; // Menggunakan biaya UKT kamu
        double regis = 500_000;         // Jika regis sudah termasuk di biaya UKT
        double diskon;
        
        boolean aktif = true;
        
        double totalSebelumDiskon = biaya + regis;
        
        // Logika diskon bertingkat dari kode temanmu
        if (totalSebelumDiskon > 3000000) {
            diskon = 0.15; // Mendapat diskon 15% karena > 3.000.000
        } else if (totalSebelumDiskon >= 1500000) {
            diskon = 0.10;
        } else {
            diskon = 0.05;
        }
        
        double potongan = totalSebelumDiskon * diskon;
        double total = totalSebelumDiskon - potongan;
        
        String status;
        if (total > 2500000) {
            status = "MAHAL";
        } else {
            status = "TERJANGKAU";
        }
        
        //deklarasi /output
        System.out.println("Kode     : " + kode);
        System.out.println("Kursus   : " + nama);
        System.out.println("Aktif    : " + aktif);
        System.out.printf("Diskon   : %.0f%%\n", diskon * 100);
        System.out.printf("Potongan : Rp%,.0f\n", potongan);
        System.out.printf("Total    : Rp%,.0f\n", total);
        System.out.println("Status   : " + status);
    }
}