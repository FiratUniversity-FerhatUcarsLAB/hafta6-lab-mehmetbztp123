 /**
 * Ad Soyad: [Mehmet Boztepe]
 * Numara: [240541084]
 * Proje: [Öğrenci Not Değerlendirme]
 * Tarih: [27.11.2025]
 */

import java.util.Scanner;

public class OgrenciNotDegerlendirme {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== OGRENCI NOT GIRISI ===");
        
        // Girdi Alma
        // Notların ondalıklı olma ihtimaline karsi double olarak aliyoruz.
        System.out.print("Vize notunu giriniz (0-100): ");
        double vize = input.nextDouble();
        
        System.out.print("Final notunu giriniz (0-100): ");
        double finalNotu = input.nextDouble();
        
        System.out.print("Odev notunu giriniz (0-100): ");
        double odev = input.nextDouble();
        
        // Hesaplamalar
        double ortalama = calculateAverage(vize, finalNotu, odev);
        String harfNotu = getLetterGrade(ortalama);
        String durum = isPassingGrade(ortalama);
        String onurListesiDurum = isHonorList(ortalama, vize, finalNotu, odev);
        String butunlemeDurumu = hasRetakeRight(ortalama);

        // Raporu Yazdirma
        printReport(vize, finalNotu, odev, ortalama, harfNotu, durum, onurListesiDurum, butunlemeDurumu);
        
        input.close();
    }

    // Gerekli Metotlar:
    
    // Ortalama hesaplama: Vize %30 + Final %40 + Odev %30
    public static double calculateAverage(double vize, double finalNotu, double odev) {
        // Double hassasiyetini korumak icin carpimlar double yapilir.
        return (vize * 0.30) + (finalNotu * 0.40) + (odev * 0.30);
    }

    // Gecme durumu: >= 50 GECTI, < 50 KALDI
    public static String isPassingGrade(double ortalama) {
        if (ortalama >= 50) {
            return "GECTI";
        } else {
            return "KALDI";
        }
    }

    // Harf notu atamasi: A (90-100), B (80-89), C (70-79), D (60-69), F (< 60)
    public static String getLetterGrade(double ortalama) {
        if (ortalama >= 90) {
            return "A";
        } else if (ortalama >= 80) { // 80 - 89
            return "B";
        } else if (ortalama >= 70) { // 70 - 79
            return "C";
        } else if (ortalama >= 60) { // 60 - 69
            return "D";
        } else { // 0 - 59.99
            return "F";
        }
    }

    // Onur listesi: Ortalama >= 85 VE hicbir not < 70 olmamali
    public static String isHonorList(double ortalama, double vize, double finalNotu, double odev) {
        // Kontrol 1: Ortalama 85 ve ustu mu?
        // Kontrol 2: Hicbir not 70'in altinda mi?
        if (ortalama >= 85 && vize >= 70 && finalNotu >= 70 && odev >= 70) {
            return "EVET";
        } else {
            return "HAYIR";
        }
    }

    // Butunleme hakki: 40 <= ort < 50
    public static String hasRetakeRight(double ortalama) {
        if (ortalama >= 40 && ortalama < 50) {
            return "VAR";
        } else {
            return "YOK";
        }
    }
    
    // Raporu konsola duzenli bir sekilde yazdiran metot
    public static void printReport(double vize, double finalNotu, double odev, double ortalama, 
                                   String harfNotu, String durum, String onurListesiDurum, String butunlemeDurumu) {
        
        System.out.println("\n=== OGRENCI NOT RAPORU ===");
        System.out.printf("Vize Notu\t: %.1f%n", vize);
        System.out.printf("Final Notu\t: %.1f%n", finalNotu);
        System.out.printf("Odev Notu\t: %.1f%n", odev);
        System.out.println("-----------------------------");
        
        // Ortalama icin ondalik kismi 1 basamakli (%.1f) yazdiriyoruz.
        System.out.printf("Ortalama\t: %.1f%n", ortalama); 
        System.out.println("Harf Notu\t: " + harfNotu);
        System.out.println("Durum\t\t: " + durum);
        System.out.println("Onur Listesi\t: " + onurListesiDurum);
        System.out.println("Butunleme\t: " + butunlemeDurumu);
    }
}

