/*
DWIE APLIKACJE I ICH ZADANIA

APLIKACJA 1:
- dodawanie  kolejnych wpisów (kolejne wpisy do pliku szyfrować, 3 pozycje, 3 różne teksty jeden pod drugim; imię, nazwisko, pesel. Każda w nowej linii oddzielane średnikami, które kończą linię tak samo szyfrować do nowych linii
- szyfrowanie za pomocą RSA i klucza jawnego z aplikacji 2 (zrobione od 71 linijki,ale trzeba pobrać klucz jawny z apki drugiej oddzielić do osobnej aplikacji)

APLIKACJA 2:
- generowanie klucza prywatnego i publicznego (E i N) i  przekazanie go do aplikacji 1 w dowolny sposób przez plik (zrobione, zapis do pliku i w lokalizacji z której odczyta apka 1)
- ODCZYT WPISÓW
- deszyfrowanie za pomocą klucza prywatnego (zrobione d w kodzie) z pliku i wyświetlenie
- usuwanie wpisów

*/

package rsa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Rsa {

    public static boolean CzyPierwsza(int liczba) {
        for (int i = 2; i <= liczba / 2; i++)
            if (liczba % i == 0) return false;

        return true;
    }
    public static ArrayList<Integer> sitoEratostenesa(int N) {
        boolean[] tablica = new boolean[N - 1];
        for (int i = 0; i < N - 1; i++) tablica[i] = true;
        ArrayList < Integer > pierwsze = new ArrayList < Integer > ();
        for (int i = 2; i <= N; i++) {
            if (tablica[i - 2]) {
                pierwsze.add(i);
                for (int j = 2 * i; j <= N; j += i) tablica[j - 2] = false;
            }
        }
        return pierwsze;
    }
    public static int NWD(int A, int B) {
        for (int i = Math.min(A, B); i > 0; i--)
            if (A % i == 0 && B % i == 0) return i;
        return -1;
    }
    public static int wyznacz_e(int phi, int n) {
        int e = 3;
        while (NWD(e, phi) != 1) e += 2;
        return e;
    }
    public static int wyznacz_d(int e, int phi) {
        int d = 1;
        while ((d * e) % phi != 1) d++;
        return d;
    }
    public static int[] ZamianaNaAscii(String tekst) {
        int[] slownik = new int[tekst.length()];
        for (int i = 0; i < tekst.length(); i++) slownik[i] += (int) tekst.charAt(i) + 1;
        return slownik;
    }
    
    
    
    
    
    
    
    public static String Szyfrowanie(String tekst, int n, int e) {
        int[] liczby = ZamianaNaAscii(tekst);
        StringBuilder zaszyfrowanyTekst = new StringBuilder();
        for (int i = 0; i < tekst.length(); i++) {
            zaszyfrowanyTekst.append((int)(Math.pow(liczby[i], e) % n));
        }
        return zaszyfrowanyTekst.toString();
    }

    public static void main(String[] args) throws IOException {
        int p, q, n, phi;
        ArrayList <Integer> pierwsze = sitoEratostenesa(200);
        Random rand = new Random();
        p = pierwsze.get(rand.nextInt(pierwsze.size()));
        q = pierwsze.get(rand.nextInt(pierwsze.size()));
        while (p == q) q = pierwsze.get(rand.nextInt(pierwsze.size()));
        p = 13;
        q = 11;
        n = p * q;
        phi = (p - 1) * (q - 1);

        System.out.println("n: " + n);
        int e = wyznacz_e(phi, n);
        System.out.println("e: " + e);
        int d = wyznacz_d(e, phi);
        System.out.println("d: " + d);
        String tekst = "A";
        String tekstZaszyfrowany = Szyfrowanie(tekst, n, e);
        System.out.println("Zaszyfrowany tekst: " + tekstZaszyfrowany);

        String filePath = "C:\\projekt\\klucze.txt";

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(filePath);
            fileWriter.write("E: ");
            fileWriter.write(Integer.toString(e));
            fileWriter.write("\nN: "); // Notatnik nie ogarnia, Ĺźe to jest nowa linia i skleja, polecamy notepad++
            fileWriter.write(Integer.toString(n));

        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }
}