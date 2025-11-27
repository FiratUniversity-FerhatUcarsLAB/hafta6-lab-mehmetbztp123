import java.util.Scanner;

public class SinemaBiletiFiyatlandirma {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Kullanicidan Gerekli Girdileri Alma
        System.out.println("=== SINEMA BILETI SISTEMI ===");
        
        System.out.println("Gun kodlari: 1=Pzt, 2=Sal, 3=Car, ..., 7=Paz");
        System.out.print("Gunu seciniz (1-7): ");
        int gun = input.nextInt();
        
        System.out.print("Seans saatini giriniz (Orn: 11, 14): ");
        int saat = input.nextInt();
        
        System.out.print("Yasinizi giriniz: ");
        int yas = input.nextInt();

        System.out.println("Meslek kodlari: 1=Ogrenci, 2=Ogretmen, 3=Diger");
        System.out.print("Meslek seciminizi giriniz (1-3): ");
        int meslek = input.nextInt();
        
        System.out.println("Film Turu kodlari: 1=2D, 2=3D, 3=IMAX, 4=4DX");
        System.out.print("Film turu seciminizi giriniz (1-4): ");
        int filmTuru = input.nextInt();
        
        // Temel Hesaplamalar
        double temelFiyat = calculateBasePrice(gun, saat);
        double indirimOrani = calculateDiscount(yas, meslek, gun);
        double formatEkstra = getFormatExtra(filmTuru);
        
        // Nihai Fiyat Hesaplama
        double nihaiFiyat = calculateFinalPrice(temelFiyat, indirimOrani, formatEkstra);

        // Raporu Yazdirma
        String biletBilgisi = generateTicketInfo(temelFiyat, indirimOrani, formatEkstra, nihaiFiyat, gun, saat, yas, meslek, filmTuru);
        System.out.println(biletBilgisi);
        
        input.close();
    }

    // Zorunlu 7 Metot:
    
    // 1. isWeekend(gun): Hafta sonu mu kontrol et (6=Cmt, 7=Paz)
    public static boolean isWeekend(int gun) {
        // Switch-case kullanımı: Gün seçimi
        switch (gun) {
            case 6: // Cumartesi
            case 7: // Pazar
                return true;
            default:
                return false;
        }
    }

    // 2. isMatinee(saat): Matine mi kontrol et (12:00 öncesi)
    public static boolean isMatinee(int saat) {
        // Saat 12'den kucukse matinedir (Ornegin 11:59 matine, 12:00 normal)
        return saat < 12;
    }

    // 3. calculateBasePrice(gun, saat): Temel fiyat hesapla
    public static double calculateBasePrice(int gun, int saat) {
        boolean isWknd = isWeekend(gun);
        boolean isMatin = isMatinee(saat);
        
        if (!isWknd) { // Hafta Ici (Pzt-Cuma: 1-5)
            if (isMatin) {
                return 45.0; // Hafta İçi Matine
            } else {
                return 65.0; // Hafta İçi Normal
            }
        } else { // Hafta Sonu (Cmt-Paz: 6-7)
            if (isMatin) {
                return 55.0; // Hafta Sonu Matine
            } else {
                return 85.0; // Hafta Sonu Normal
            }
        }
    }

    // 4. calculateDiscount(yas, meslek, gun): Uygulanacak en yuksek indirim oranini hesapla
    public static double calculateDiscount(int yas, int meslek, int gun) {
        double maxDiscount = 0.0; // Maksimum indirim orani (0.0 - 1.0 arasi)
        
        // A. Yasa Bagli Indirimler (Her Gun Gecerli)
        if (yas < 12) {
            maxDiscount = Math.max(maxDiscount, 0.25); // 12 yaş altı %25
        }
        if (yas >= 65) {
            maxDiscount = Math.max(maxDiscount, 0.30); // 65+ yaş %30
        }
        
        // B. Meslege Bagli Indirimler (Switch-case kullanimi)
        switch (meslek) {
            case 1: // Ogrenci
                if (gun >= 1 && gun <= 4) { // Pzt-Persembe
                    maxDiscount = Math.max(maxDiscount, 0.20); // %20
                } else if (gun >= 5 && gun <= 7) { // Cuma-Pazar
                    maxDiscount = Math.max(maxDiscount, 0.15); // %15
                }
                break;
            case 2: // Ogretmen
                if (gun == 3) { // Sadece Carsamba
                    maxDiscount = Math.max(maxDiscount, 0.35); // %35
                }
                break;
            // case 3: Diger (Ek indirim yok)
        }
        
        return maxDiscount; // Orani dondurur (Orn: 0.35)
    }

    // 5. getFormatExtra(filmTuru): Format ekstra ucreti
    public static double getFormatExtra(int filmTuru) {
        // Switch-case kullanimi: Film turu secimi (1=2D, 2=3D, 3=IMAX, 4=4DX)
        switch (filmTuru) {
            case 2: // 3D film
                return 25.0;
            case 3: // IMAX
                return 35.0;
            case 4: // 4DX
                return 50.0;
            case 1: // 2D film (Ekstra ücret yok)
            default:
                return 0.0;
        }
    }

    // 6. calculateFinalPrice(...): Toplam fiyat (Temel - Indirim Tutari + Ekstra)
    public static double calculateFinalPrice(double basePrice, double discountRate, double formatExtra) {
        double discountAmount = basePrice * discountRate;
        double priceAfterDiscount = basePrice - discountAmount;
        
        // Fiyat negatif cikmasin diye kontrol eklenebilir, ancak projede istenmemis.
        return priceAfterDiscount + formatExtra;
    }

    // 7. generateTicketInfo(...): Bilet bilgisi olustur
    public static String generateTicketInfo(double basePrice, double discountRate, double formatExtra, double finalPrice, int gun, int saat, int yas, int meslek, int filmTuru) {
        double discountAmount = basePrice * discountRate;
        
        // Gun, meslek ve film turu kodlarini okunur metne cevirme (rapor icin)
        String gunAdi = new String[]{"Pzt", "Sal", "Car", "Per", "Cum", "Cmt", "Paz"}[gun - 1];
        String meslekAdi = meslek == 1 ? "Ogrenci" : (meslek == 2 ? "Ogretmen" : "Diger");
        String filmTuruAdi = new String[]{"2D", "3D", "IMAX", "4DX"}[filmTuru - 1];
        
        String info = "\n=== SINEMA BILETI RAPORU ===\n";
        info += String.format("Seans Bilgisi\t\t: %s, %d:00%n", gunAdi, saat);
        info += String.format("Kullanici Bilgisi\t: %d Yas, %s%n", yas, meslekAdi);
        info += String.format("Film Formati\t\t: %s%n", filmTuruAdi);
        info += "\n-----------------------------------";
        
        info += String.format("%nTemel Fiyat\t\t: %.2f TL", basePrice);
        info += String.format("%nUygulanan Indirim\t: %d%% (%.2f TL)", (int)(discountRate * 100), discountAmount);
        info += String.format("%nFormat Ekstrasi\t\t: +%.2f TL", formatExtra);
        info += "\n-----------------------------------";
        info += String.format("%nODENECEK TOPLAM FIYAT\t: %.2f TL", finalPrice);
        info += "\n===================================";
        
        return info;
    }
}
