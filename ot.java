import java.io.Console;
class ot {
    public static char[][] pelitaulu = new char[8][8];
    public static char valkoinen = (char)219;
    public static char musta = (char)176;
    public static char tyhja = ' ';
    public static boolean mVuoro = true;
    public static byte[] byteTilanne = { 2, 2 };
    public static void main(String[] args) {
        alusta();
        tulosta();
        boolean tulos;
        tulos = siirto();
        System.out.println(tulos);
    }
    public static boolean siirto() {
        Console koso = System.console();
        if (mVuoro) {
            System.out.print("Mustan");
        } else {
            System.out.print("Valkoisen");
        }
        System.out.println(" vuoro");
        System.out.print("Anna koordinaatit: ");
        String syote = koso.readLine();
        if (syote.length() == 2) {  } else {
            return false;
        }
        int[] k = { syote.codePointAt(1) , syote.codePointAt(0) };
        if (k[1] <= 72 && k[1] >= 65 || k[1] <= 104 && k[1] >= 97) {  } else {
            return false;
        }
        if (k[0] >= 49 && k[0] <= 56) {  } else {
            return false;
        }
        if (k[1] < 80) {
            k[1] = k[1] - 65;
        } else {
            k[1] = k[1] - 97;
        }
        k[0] -= 49;
        if (pelitaulu[k[0]][k[1]] == tyhja) {  } else {
            return false;
        }
        boolean a = true;
        char etsittava;
        char oma;
        if (mVuoro) {
            etsittava = valkoinen;
            oma = musta;

        } else {
            etsittava = musta;
            oma = valkoinen;
        }
        for (byte i = -1; i <= 1; i++) {
            for (byte j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                boolean r = false; //onko eka viereinen vastustajan väriä
                boolean s = false;//löytyykö tästä suunnasta oman värinen nappi
                if (pelitaulu[k[0] + i][k[1] + j] == etsittava) {
                    r = true;
                }
                int[] loydOma = new int[2]; //löydetty oma merkki
                if (r) {
                    for (byte l = 2; l < 8; l++) {
                        int x = k[0] + (l * i);
                        int y = k[1] + (l * j);
                        if (x < 0 || x > 7 || y < 0 || y > 7) {
                            break;
                        }
                        if (pelitaulu[x][y] == oma) {
                            s = true;
                            loydOma[0] = x;
                            loydOma[1] = y;
                            break;
                        }
                        if (pelitaulu[x][y] == etsittava) {
                            continue;
                        }
                        if (pelitaulu[x][y] == tyhja) {
                            break;
                        }
                    }
                }
                if (r && s) {

                }
            }
        }
        if (a) {
            return false;
        }

        return true;
    }
    public static void alusta() {
        for (byte i = 0; i < pelitaulu.length; i++) {
            for (byte j = 0; j < pelitaulu.length; j++) {
                if (i == 3 && j == 3) {
                    pelitaulu[i][j] = musta;
                } else if (i == 3 && j == 4) {
                    pelitaulu[i][j] = valkoinen;
                } else if (i == 4 && j == 3) {
                    pelitaulu[i][j] = valkoinen;
                } else if (i == 4 && j == 4) {
                    pelitaulu[i][j] = musta;
                } else {
                    pelitaulu[i][j] = tyhja;
                }
            }
        }
    }
    public static void tulosta() {
        tilanne();
        System.out.println("Musta ( " + musta + " ) .. " + byteTilanne[0]);
        System.out.println("Valkoinen ( " + valkoinen + " ) .. " + byteTilanne[1]);
        System.out.println("  A B C D E F G H");
        System.out.println(" " + (char)201 + (char)205 + (char)206 + (char)205 + (char)206 + (char)205 + (char)206 + (char)205 + (char)206 + (char)205 + (char)206 + (char)205 + (char)206 + (char)205 + (char)206 + (char)205 + (char)187);
        for (byte i = 0; i < pelitaulu.length; i++) {
            System.out.print(i + 1);
            System.out.print((char)186);
            for (byte j = 0; j < pelitaulu.length; j++) {
                System.out.print(pelitaulu[i][j]);
                System.out.print((char)186);
            }
            System.out.println();
            if (i == (pelitaulu.length - 1)) {
                System.out.println(" " + (char)200 + (char)205 + (char)202 + (char)205 + (char)202 + (char)205 + (char)202 + (char)205 + (char)202 + (char)205 + (char)202 + (char)205 + (char)202 + (char)205 + (char)202 + (char)205 + (char)188);
            } else {
                System.out.println(" " + (char)206 + (char)205 + (char)206 + (char)205 + (char)206 + (char)205 + (char)206 + (char)205 + (char)206 + (char)205 + (char)206 + (char)205 + (char)206 + (char)205 + (char)206 + (char)205 + (char)185);
            }
        }
    }
    public static void tilanne() {
        byte mustia = 0;
        byte valkoisia = 0;
        for (byte i = 0; i < pelitaulu.length; i++) {
            for (byte j = 0; j < pelitaulu.length; j++) {
                if (pelitaulu[i][j] == musta) {
                    mustia++;
                } else if (pelitaulu[i][j] == valkoinen) {
                    valkoisia++;
                } else {

                }
            }
        }
        byteTilanne[0] = mustia;
        byteTilanne[1] = valkoisia;
    }
}
