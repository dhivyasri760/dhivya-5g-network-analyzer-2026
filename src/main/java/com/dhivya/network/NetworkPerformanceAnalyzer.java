
package com.dhivya.network;

public class NetworkPerformanceAnalyzer {

    public static double calculateScore(
            double rssi,
            double latency,
            double throughput,
            double packetLoss,
            double signalQuality) {

        double score = 0;

        score += (rssi + 100) * 0.20;
        score += (100 - latency) * 0.20;
        score += Math.min(throughput / 2, 100) * 0.20;
        score += (100 - packetLoss * 10) * 0.20;
        score += signalQuality * 0.20;

        return Math.max(0, Math.min(score, 100));
    }

    public static String classifyNetwork(double score) {

        if (score >= 80) {
            return "Excellent";
        } else if (score >= 60) {
            return "Good";
        } else if (score >= 40) {
            return "Fair";
        } else {
            return "Poor";
        }
    }

    public static void main(String[] args) {

        double rssi = -65;
        double latency = 20;
        double throughput = 150;
        double packetLoss = 1;
        double signalQuality = 90;

        double score = calculateScore(
                rssi, latency, throughput,
                packetLoss, signalQuality);

        String quality = classifyNetwork(score);

        System.out.println("5G Network Performance Analyzer");
        System.out.println("--------------------------------");
        System.out.println("RSSI: " + rssi + " dBm");
        System.out.println("Latency: " + latency + " ms");
        System.out.println("Throughput: " + throughput + " Mbps");
        System.out.println("Packet Loss: " + packetLoss + "%");
        System.out.println("Signal Quality: " + signalQuality);
        System.out.println("Performance Score: " + score);
        System.out.println("Network Quality: " + quality);
    }
}
