import org.apache.commons.math3.distribution.GammaDistribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DistributiaBeta {

    public static List<Double> generareDistributieBeta(double a, double b, int numarValoriGenerate) {
        List<Double> valori = new ArrayList<>();
        for (int i = 0; i < numarValoriGenerate; i++) {
            valori.add(beta(a,b));
        }
        return valori;
    }

    public static double beta(double a, double b) {
        double U1, U2, V, T;
        do {
            U1 = Math.random();
            U2 = Math.random();
            V = Math.pow(U1, 1 / a);
            T = Math.pow(U2, 1 / b);
        } while (V + T >= 1);
        return V / (V + T);
    }

    public static double media(List<Double> valori) {
        double media = 0.0;
        for (Double val : valori) {
            media += val;
        }
        return media / valori.size();
    }

    public static double dispersia(List<Double> valori, double media) {
        double dispersia = 0.0;
        for (int i = 0; i < valori.size(); i++) {
            dispersia += Math.pow(valori.get(i), 2);
        }
        return dispersia / valori.size() - Math.pow(media, 2);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Generare variabila beta");
        System.out.print("a = ");
        double a = in.nextDouble();
        System.out.print("b = ");
        double b = in.nextDouble();
        System.out.print("Nr. valori generate = ");
        int numarValoriGenerate = in.nextInt();

        List<Double> valori = generareDistributieBeta(a, b, numarValoriGenerate);

        double mediaTeoretica = a / (a + b);
        System.out.println("Media teoretica: " + mediaTeoretica);
        double mediaCalculata = media(valori);
        System.out.println("Media calculata: " + media(valori));

        double dispersiaTeoretica = (a * b) / (Math.pow(a + b, 2) * (a + b + 1));
        System.out.println("Dispersia teoretica: " + dispersiaTeoretica);
        double dispersiaCalculata = dispersia(valori, mediaCalculata);
        System.out.println("Dispersia calculata: " + dispersiaCalculata);
    }

}
