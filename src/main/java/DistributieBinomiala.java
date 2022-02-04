import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Generare variabila binomiala (metoda transformatei inverse)
 */
public class DistributieBinomiala {

    public static List<Double> generareDistributieBinomiala(int n, double p, int numarValoriGenerate) {
        double[] valoriGenerate = new double[n + 1];
        double[] distributieBinomiala = distributieBinomiala(n, p);

        for (int j = 0; j < numarValoriGenerate; j++) {
            double random = Math.random();
            double probabilitate = 0.0;
            for (int i = 0; i < distributieBinomiala.length; i++) {
                probabilitate = probabilitate + distributieBinomiala[i];
                if (random < probabilitate) {
                    valoriGenerate[i]++;
                    break;
                }
            }
        }
        return Arrays.stream(valoriGenerate).boxed().collect(Collectors.toList());
    }

    public static double[] distributieBinomiala(int n, double p) {
        double[] distributieBinomiala = new double[n + 1];
        for (int k = 0; k <= n; k++) {
            distributieBinomiala[k] = probabilitateBinomiala(k, n, p);
        }
        return distributieBinomiala;
    }

    /*
    B(k; n p) = C(n k) * p^k * (1 - p)^n-k
     */
    public static double probabilitateBinomiala(int k, int n, double p) {
        return combinari(n, k).doubleValue() * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }

    /*
    C(n k) = n! / k! * (n-k)!
     */
    public static BigInteger combinari(int n, int k) {
        return factorial(n).divide(factorial(k).multiply(factorial(n - k)));
    }

    public static BigInteger factorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++)
            result = result.multiply(BigInteger.valueOf(i));
        return result;
    }

    public static void histograma(List<Double> distributia, double media, double dispersia) {
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600)
//                .title("Distributie Binomiala")
//                .xAxisTitle("")
//                .yAxisTitle("Probabilitate")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAvailableSpaceFill(0.99);
        chart.getStyler().setOverlapped(true);

        var xData = Stream.iterate(0, n -> n + 1).limit(distributia.size()).toList();
        String seriesName = "Distributie Binomiala\n" +
                "Media: " + media + "\n" +
                "Dispersia: " + dispersia + "\n";
        chart.addSeries(seriesName, xData, distributia);

        new SwingWrapper<>(chart).displayChart();
    }

    public static double media(List<Double> valori, int numarValoriGenerate) {
        double media = 0.0;
        for (int i = 0; i < valori.size(); i++) {
            media += valori.get(i) * i;
        }
        return media / numarValoriGenerate;
    }

    public static double dispersia(List<Double> valori, double media, int numarValoriGenerate) {
        double dispersia = 0.0;
        for (int i = 0; i < valori.size(); i++) {
            dispersia += valori.get(i) * Math.pow(i, 2);
        }
        return dispersia / numarValoriGenerate - Math.pow(media, 2);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Generare valori variabila binomiala (metoda transformatei inverse)");
        System.out.print("n = ");
        int n = in.nextInt();
        System.out.print("p = ");
        double p = in.nextDouble();
        System.out.print("Nr. valori generate = ");
        int numarValoriGenerate = in.nextInt();

        System.out.println("Media teoretica: " + n * p);
        System.out.println("Dispersia teoretica: " + n * p * (1 - p));

        List<Double> valori = generareDistributieBinomiala(n, p, numarValoriGenerate);
        double media = media(valori, numarValoriGenerate);
        double dispersia = dispersia(valori, media, numarValoriGenerate);
        histograma(valori, media, dispersia);
    }
}
