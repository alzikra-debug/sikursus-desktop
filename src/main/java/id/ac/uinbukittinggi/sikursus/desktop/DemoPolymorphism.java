package id.ac.uinbukittinggi.sikursus.desktop;

import java.util.ArrayList;
import java.util.List;
import model.Orang;
import model.Peserta;
import model.Instruktur;

public class DemoPolymorphism {

    public static void main(String[] args) {
        // 1. Membuat objek konkrit (Subclass)
        Peserta p1 = new Peserta(1, "Ahmad Fauzan", "081234567801", "252001", "Informatika");
        Instruktur i1 = new Instruktur(2, "Dina Pratama", "081211110001", "Java Desktop");

        // 2. Membuat List dengan tipe referensi Parent (Orang)
        // <-- [KOMENTAR WAJIB]: List bertipe Orang, namun mampu menampung objek Peserta dan Instruktur
        List<Orang> daftarOrang = new ArrayList<>();
        daftarOrang.add(p1);
        daftarOrang.add(i1);

        // 3. Loop dan panggil getInfo() tanpa mengecek tipe konkrit (Polymorphism)
        System.out.println("=== Output Polimorfisme (List Loop) ===");
        // <-- [KOMENTAR WAJIB]: Memanggil getInfo() secara polimorfik (Dynamic Dispatch)
        for (Orang o : daftarOrang) {
            System.out.println(o.getInfo());
        }

        // 4. Polymorphic Argument
        System.out.println("\n=== Output Polymorphic Argument ===");
        tampilkanInfo(p1);
        tampilkanInfo(i1);

        // 5. Penggunaan instanceof
        System.out.println("\n=== Eksperimen instanceof ===");
        for (Orang o : daftarOrang) {
            if (o instanceof Peserta) {
                Peserta p = (Peserta) o;
                System.out.println("Terdeteksi Peserta -> NIM: " + p.getNim());
            } else if (o instanceof Instruktur) {
                Instruktur ins = (Instruktur) o;
                System.out.println("Terdeteksi Instruktur -> Keahlian: " + ins.getKeahlian());
            }
        }
    }

    // Method dengan Polymorphic Argument
    // <-- [KOMENTAR WAJIB]: Menerima parameter tipe Orang, bisa diisi Peserta atau Instruktur
    public static void tampilkanInfo(Orang orang) {
        System.out.println("Info dari method: " + orang.getInfo());
    }
}