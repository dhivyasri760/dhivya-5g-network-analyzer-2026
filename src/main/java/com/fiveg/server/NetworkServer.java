
package com.fiveg.server;

import com.sun.net.httpserver.HttpServer;
import com.dhivya.network.NetworkPerformanceAnalyzer;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class NetworkServer {

    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8081), 0);

        server.createContext("/api/network", exchange -> {

            // Generate simulated 5G network values
            double rssi = -90 + random.nextDouble() * 40;
            double latency = 10 + random.nextDouble() * 90;
            double throughput = 20 + random.nextDouble() * 180;
            double packetLoss = random.nextDouble() * 10;
            double signalQuality = 40 + random.nextDouble() * 60;

            // Calculate performance score
            double score = NetworkPerformanceAnalyzer.calculateScore(
                    rssi,
                    latency,
                    throughput,
                    packetLoss,
                    signalQuality
            );

            // Classify network quality
            String quality =
                    NetworkPerformanceAnalyzer.classifyNetwork(score);

            // Create JSON response
            String json = String.format(
                    "{\"rssi\": %.2f, \"latency\": %.2f, \"throughput\": %.2f, \"packetLoss\": %.2f, \"signalQuality\": %.2f, \"performanceScore\": %.2f, \"networkQuality\": \"%s\"}",
                    rssi,
                    latency,
                    throughput,
                    packetLoss,
                    signalQuality,
                    score,
                    quality
            );

            byte[] response =
                    json.getBytes(StandardCharsets.UTF_8);

            // Set response headers
            exchange.getResponseHeaders().set(
                    "Content-Type",
                    "application/json; charset=UTF-8"
            );

            exchange.getResponseHeaders().set(
                    "Access-Control-Allow-Origin",
                    "*"
            );

            // Send response
            exchange.sendResponseHeaders(200, response.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }

        });

        // Start server
        server.start();

        System.out.println("5G Network Server Started!");
        System.out.println(
                "Live Simulation API: http://localhost:8081/api/network"
        );
    }
}
