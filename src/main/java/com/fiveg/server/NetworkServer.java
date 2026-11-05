
package com.fiveg.server;

import com.sun.net.httpserver.HttpServer;
import com.dhivya.network.NetworkPerformanceAnalyzer;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class NetworkServer {

    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8081), 0);

        // Dashboard frontend
        server.createContext("/", exchange -> {

            Path htmlPath = Path.of("src/main/java/index.html");

            if (!Files.exists(htmlPath)) {
                String error = "index.html file not found!";
                byte[] response = error.getBytes(StandardCharsets.UTF_8);

                exchange.sendResponseHeaders(404, response.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response);
                }
                return;
            }

            byte[] response = Files.readAllBytes(htmlPath);

            exchange.getResponseHeaders().set(
                    "Content-Type", "text/html; charset=UTF-8");

            exchange.sendResponseHeaders(200, response.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        });

        // Live Simulation API
        server.createContext("/api/network", exchange -> {

            double rssi = -90 + random.nextDouble() * 40;
            double latency = 10 + random.nextDouble() * 90;
            double throughput = 20 + random.nextDouble() * 180;
            double packetLoss = random.nextDouble() * 10;
            double signalQuality = 40 + random.nextDouble() * 60;

            double score = NetworkPerformanceAnalyzer.calculateScore(
                    rssi, latency, throughput,
                    packetLoss, signalQuality);

            String quality =
                    NetworkPerformanceAnalyzer.classifyNetwork(score);

            String json = String.format(
                    "{\"rssi\": %.2f, \"latency\": %.2f, \"throughput\": %.2f, \"packetLoss\": %.2f, \"signalQuality\": %.2f, \"performanceScore\": %.2f, \"networkQuality\": \"%s\"}",
                    rssi, latency, throughput,
                    packetLoss, signalQuality, score, quality);

            byte[] response = json.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set(
                    "Content-Type",
                    "application/json; charset=UTF-8");

            exchange.getResponseHeaders().set(
                    "Access-Control-Allow-Origin", "*");

            exchange.sendResponseHeaders(200, response.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        });

        server.start();

        System.out.println("5G Network Server Started!");
        System.out.println("Dashboard: http://localhost:8081");
        System.out.println("API: http://localhost:8081/api/network");
    }
}
