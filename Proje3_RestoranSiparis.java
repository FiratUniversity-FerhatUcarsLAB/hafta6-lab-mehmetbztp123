 /**
 * Ad Soyad: [Mehmet Boztepe]
 * Numara: [240541084]
 * Proje: [Akıllı Restoran Sipariş]
 * Tarih: [27.11.2025]
 */



import java.util.Scanner;

public class AkilliRestoranSiparis {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Kullanicidan Gerekli Girdileri Alma
        System.out.println("=== AKILLI RESTORAN SIPARIS SISTEMI ===");
        
        // 1. Menü Seçimleri (Sipariş var mı boolean ile takip ediliyor)
        System.out.print("Ana Yemek secimi (1-4, Yoksa 0): ");
        int anaYemekSecim = input.nextInt();
        boolean anaVar = anaYemekSecim > 0;
        
        System.out.print("Baslangic secimi (1-3, Yoksa 0): ");
        int baslangicSecim = input.nextInt();
        
        System.out.print("Icecek secimi (1-4, Yoksa 0): ");
        int icecekSecim = input.nextInt();
        boolean icecekVar = icecekSecim > 0;
        
        System.out.print("Tatli secimi (1-3, Yoksa 0): ");
        int tatliSecim = input.nextInt();
        boolean tatliVar = tatliSecim > 0;
        
        // 2. Ozel Durum Girdileri
        System.out.print("Saat (8-23 arasi): ");
        int saat = input.nextInt();
        
        System.out.print("Ogrenci misiniz? (E/H): ");
        String ogrenciStr = input.next();
        boolean ogrenci = ogrenciStr.equalsIgnoreCase("E");

        System.out.print("Hangi gun? (1=Pzt.. 7=Paz): ");
        int gun = input.nextInt();
        
        // 3. Hesaplamalar
        double anaTutar = getMainDishPrice(anaYemekSecim);
        double baslangicTutar = getAppetizerPrice(baslangicSecim);
        double icecekTutar = getDrinkPrice(icecekSecim);
        double tatliTutar = getDessertPrice(tatliSecim);
        
        double araToplam = anaTutar + baslangicTutar + icecekTutar + tatliTutar;
        
        boolean combo = isComboOrder(anaVar, icecekVar, tatliVar);
        boolean happyHour = isHappyHour(saat);
        
        double nihaiTutar = calculateDiscount(araToplam, combo, happyHour, ogrenci, gun, icecekTutar);
        double bahsisOnerisi = calculateServiceTip(nihaiTutar);
        
        // 4. Raporu Yazdirma
        generateTicketInfo(anaTutar, baslangicTutar, icecekTutar, tatliTutar, araToplam, nihaiTutar, bahsisOnerisi, combo, happyHour, ogrenci, gun);
        
        input.close();
    }

    // Gerekli 8 Metot:
    
    // 1. getMainDishPrice(secim): Ana yemek fiyatı (Switch-case kullanimi)
    public static double getMainDishPrice(int secim) {
        switch (secim) {
            case 1: return 85.0;  // Izgara Tavuk
            case 2: return 120.0; // Adana Kebap
            case 3: return 110.0; // Levrek
            case 4: return 65.0;  // Mantı
            default: return 0.0;
        }
    }

    // 2. getAppetizerPrice(secim): Baslangic fiyati (Switch-case kullanimi)
    public static double getAppetizerPrice(int secim) {
        switch (secim) {
            case 1: return 25.0;  // Çorba
            case 2: return 45.0;  // Humus
            case 3: return 55.0;  // Sigara Boregi
            default: return 0.0;
        }
    }

    // 3. getDrinkPrice(secim): Icecek fiyati (Switch-case kullanimi)
    public static double getDrinkPrice(int secim) {
        switch (secim) {
            case 1: return 15.0;  // Kola
            case 2: return 12.0;  // Ayran
            case 3: return 35.0;  // Taze Meyve Suyu
            case 4: return 25.0;  // Limonata
            default: return 0.0;
        }
    }

    // 4. getDessertPrice(secim): Tatli fiyati (Switch-case kullanimi)
    public static double getDessertPrice(int secim) {
        switch (secim) {
            case 1: return 65.0;  // Kunefe
            case 2: return 55.0;  // Baklava
            case 3: return 35.0;  // Sutlac
            default: return 0.0;
        }
    }

    // 5. isComboOrder(anaVar, icecekVar, tatliVar): Combo menü: Ana yemek + Icecek + Tatli = %15 indirim
    // Boolean değişkenlerle durum takibi
    public static boolean isComboOrder(boolean anaVar, boolean icecekVar, boolean tatliVar) {
        return anaVar && icecekVar && tatliVar;
    }

    // 6. isHappyHour(saat): Happy Hour (14:00-17:00) kontrolu
    public static boolean isHappyHour(int saat) {
        return saat >= 14 && saat < 17; // 14, 15, 16 saatlerini kapsar
    }

    // 7. calculateDiscount(tutar, combo, ogrenci, saat, gun, icecekTutar): Indirimleri uygula
    // Içiçe if yapıları ile indirim kontrolü
    public static double calculateDiscount(double araToplam, boolean combo, boolean happyHour, boolean ogrenci, int gun, double icecekTutar) {
        double nihaiTutar = araToplam;
        double indirimTutari = 0.0;
        
        // 1. Combo İndirimi (%15)
        if (combo) {
            double indirim = nihaiTutar * 0.15;
            nihaiTutar -= indirim;
            indirimTutari += indirim;
            System.out.printf("Combo İndirimi: -%.2f TL (%s)%n", indirim, "%%15");
        }
        
        // 2. Happy Hour İndirimi (%20 Sadece Iceceklerde)
        if (happyHour && icecekTutar > 0) {
            double indirim = icecekTutar * 0.20;
            // Indirim, sadece icecek tutarinin %20'si kadardir
            nihaiTutar -= indirim; 
            indirimTutari += indirim;
            System.out.printf("Happy Hour (Icecek) Indirimi: -%.2f TL (%s)%n", indirim, "%%20");
        }

        // 3. 200 TL Üzeri İndirim (%10) - (Diger indirimler dusuldukten sonraki tutara uygulanmali)
        if (nihaiTutar > 200) {
            double indirim = nihaiTutar * 0.10;
            nihaiTutar -= indirim;
            indirimTutari += indirim;
            System.out.printf("200 TL Uzeri Indirim: -%.2f TL (%s)%n", indirim, "%%10");
        }

        // 4. Öğrenci İndirimi (Hafta İçi %10 Ekstra) - (En son uygulanir)
        if (ogrenci && (gun >= 1 && gun <= 5)) { // Pzt-Cuma
            double indirim = nihaiTutar * 0.10;
            nihaiTutar -= indirim;
            indirimTutari += indirim;
            System.out.printf("Ogrenci (Hafta İçi) İndirimi: -%.2f TL (%s)%n", indirim, "%%10");
        }

        return nihaiTutar;
    }

    // 8. calculateServiceTip(tutar): Garson servisi: +%10 bahşiş önerisi
    public static double calculateServiceTip(double tutar) {
        return tutar * 0.10;
    }
    
    // Raporu konsola duzenli bir sekilde yazdiran metot
    public static void generateTicketInfo(double anaTutar, double baslangicTutar, double icecekTutar, double tatliTutar, 
                                           double araToplam, double nihaiTutar, double bahsisOnerisi, 
                                           boolean combo, boolean happyHour, boolean ogrenci, int gun) {
        
        System.out.println("\n=== SIPARIS DETAYLARI ===");
        System.out.printf("Ana Yemek Toplam: %.2f TL%n", anaTutar);
        System.out.printf("Baslangic Toplam: %.2f TL%n", baslangicTutar);
        System.out.printf("Icecek Toplam:    %.2f TL%n", icecekTutar);
        System.out.printf("Tatli Toplam:     %.2f TL%n", tatliTutar);
        System.out.println("-----------------------------");
        System.out.printf("ARA TOPLAM:       %.2f TL%n", araToplam);
        System.out.println("-----------------------------");

        System.out.println("--- UYGULANAN INDIRIMLER ---");
        // İndirim metodu içindeki print'ler buraya gelecektir.

        System.out.println("-----------------------------");
        System.out.printf("NIHAI ODENECEK TUTAR: %.2f TL%n", nihaiTutar);
        System.out.printf("Bahsis Onerisi (%%10): %.2f TL%n", bahsisOnerisi);
        System.out.println("=============================");
    }
}
