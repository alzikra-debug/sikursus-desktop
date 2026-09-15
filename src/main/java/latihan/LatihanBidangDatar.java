package latihan;

public class LatihanBidangDatar {

    public static void main(String[] args) {
        // Variabel bidang datar (Persegi Panjang)
        double panjang = 10.0;
        double lebar = 5.0;

        // Rumus Luas dan Keliling
        double luas = panjang * lebar;
        double keliling = 2 * (panjang + lebar);

        // Output
        System.out.println("=== Hitung Bidang Datar (Persegi Panjang) ===");
        System.out.println("Panjang  : " + panjang);
        System.out.println("Lebar    : " + lebar);
        System.out.println("Luas     : " + luas);
        System.out.println("Keliling : " + keliling);
    }
}