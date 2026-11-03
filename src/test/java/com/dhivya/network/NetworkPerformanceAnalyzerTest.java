package com.dhivya.network;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class NetworkPerformanceAnalyzerTest {

    @Test
    void testCalculateScore() {
        double score = NetworkPerformanceAnalyzer.calculateScore(
                -65, 20, 150, 1, 90);

        assertEquals(74.0, score, 0.01);
    }

    @Test
    void testClassifyNetwork() {
        assertEquals("Excellent",
                NetworkPerformanceAnalyzer.classifyNetwork(85));

        assertEquals("Good",
                NetworkPerformanceAnalyzer.classifyNetwork(70));

        assertEquals("Fair",
                NetworkPerformanceAnalyzer.classifyNetwork(50));

        assertEquals("Poor",
                NetworkPerformanceAnalyzer.classifyNetwork(30));
    }
}