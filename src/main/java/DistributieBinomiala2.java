import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Generare variabila binomiala (teorema limita centrala)
 */
public class DistributieBinomiala2 {

    public static List<Integer> generareDistributieBinomiala2(int n, double p, int numarValoriGenerate) {
        List<Integer> valori = new ArrayList<>();
        NormalDistribution normala = new NormalDistribution(0, 1);
        for (int i = 0; i < numarValoriGenerate; i++) {
            double W = normala.sample();
            int X = (int) Math.round(n * p + W * Math.sqrt(n * p * (1 - p)));
            valori.add(X);
        }
        return valori;
    }

    public static double media(List<Integer> valori) {
        double media = 0.0;
        for (Integer val : valori) {
            media += val;
        }
        return media / valori.size();
    }

    public static double dispersia(List<Integer> valori, double media) {
        double dispersia = 0.0;
        for (int i = 0; i < valori.size(); i++) {
            dispersia += Math.pow(valori.get(i), 2);
        }
        return dispersia / valori.size() - Math.pow(media, 2);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Generare valori variabila binomiala (teorema limita centrala)");
        System.out.print("n = ");
        int n = in.nextInt();
        System.out.print("p = ");
        double p = in.nextDouble();
        System.out.print("Nr. valori generate = ");
        int numarValoriGenerate = in.nextInt();

        List<Integer> valori = generareDistributieBinomiala2(n, p, numarValoriGenerate);

        System.out.println("Media teoretica: " + n * p);
        double media = media(valori);
        System.out.println("Media calculata: " + media);

        System.out.println("Dispersia teoretica: " + n * p * (1 - p));
        double dispersia = dispersia(valori, media);
        System.out.println("Dispersia calculata: " + dispersia);
    }
}
