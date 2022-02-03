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

public class DistributieBinomiala {

    public static List<Double> generareValoriDistributieBinomiala(int n, double p, int numarValoriGenerate) {
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

    public static void histograma(List<Double> distribution)  {
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600)
//                .title("Distributie Binomiala")
//                .xAxisTitle("")
//                .yAxisTitle("Probabilitate")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAvailableSpaceFill(0.99);
        chart.getStyler().setOverlapped(true);

        var xData = Stream.iterate(0, n -> n + 1).limit(distribution.size()).toList();
        chart.addSeries("Distributie Binomiala", xData, distribution);

        new SwingWrapper<>(chart).displayChart();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Generare valori variabila binomiala prin metoda transformatei inverse");
        System.out.print("n = ");
        int n = in.nextInt();
        System.out.print("p = ");
        double p = in.nextDouble();

        List<Double> valori = generareValoriDistributieBinomiala(n, p, 10000);
        histograma(valori);
    }
}
