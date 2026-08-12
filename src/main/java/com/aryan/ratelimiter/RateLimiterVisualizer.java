package com.aryan.ratelimiter;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class RateLimiterVisualizer {

    private static JTabbedPane tabbedPane;
    private static JFrame frame;

    private static final String[] userIds = { "Aryan", "Aryan", "Aryan", "Aryan", "Aryan", "Aryan" };
    private static final long[] timestamps = { 7, 8, 9, 10, 11, 12 };

    public static void main(String[] args) {

        frame = new JFrame("Rate Limiter Algorithm Comparison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(760, 550);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // --- Control panel (top) ---
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.add(new JLabel("Limit:"));
        JSpinner limitSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 20, 1));
        controlPanel.add(limitSpinner);

        controlPanel.add(new JLabel("Window Size (sec):"));
        JSpinner windowSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 60, 1));
        controlPanel.add(windowSpinner);

        JButton runButton = new JButton("Run Simulation");
        controlPanel.add(runButton);

        frame.add(controlPanel, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        frame.add(tabbedPane, BorderLayout.CENTER);

        runButton.addActionListener(e -> {
            int limit = (Integer) limitSpinner.getValue();
            int windowSize = (Integer) windowSpinner.getValue();
            runSimulationAndRefresh(limit, windowSize);
        });

        // initial run with defaults
        runSimulationAndRefresh(4, 10);

        frame.setVisible(true);
    }

    private static void runSimulationAndRefresh(int limit, int windowSize) {

        RateLimiter fixedWindow = new RateLimiter(StrategyType.FIXED_WINDOW);
        RateLimiter slidingWindow = new RateLimiter(StrategyType.SLIDING_WINDOW);
        RateLimiter tokenBucket = new RateLimiter(StrategyType.TOKEN_BUCKET);

        List<Long> timeList = new ArrayList<>();
        List<Boolean> fixedResults = new ArrayList<>();
        List<Boolean> slidingResults = new ArrayList<>();
        List<Boolean> tokenResults = new ArrayList<>();

        for (int i = 0; i < userIds.length; i++) {
            String user = userIds[i];
            long t = timestamps[i];

            timeList.add(t);
            fixedResults.add(fixedWindow.allowRequest(user, t, limit, windowSize));
            slidingResults.add(slidingWindow.allowRequest(user, t, limit, windowSize));
            tokenResults.add(tokenBucket.allowRequest(user, t, limit, windowSize));
        }

        TimelinePanel fixedPanel = new TimelinePanel("Fixed Window", new Color(230, 126, 34), timeList, fixedResults);
        TimelinePanel slidingPanel = new TimelinePanel("Sliding Window", new Color(52, 152, 219), timeList, slidingResults);
        TimelinePanel tokenPanel = new TimelinePanel("Token Bucket", new Color(46, 160, 67), timeList, tokenResults);

        List<TimelinePanel> panels = new ArrayList<>(Arrays.asList(fixedPanel, slidingPanel, tokenPanel));
        panels.sort((a, b) -> Double.compare(b.getHitRate(), a.getHitRate()));

        tabbedPane.removeAll();
        for (TimelinePanel panel : panels) {
            String label = String.format("%s (%.0f%%)", panel.getAlgorithmNamePublic(), panel.getHitRate());
            tabbedPane.addTab(label, panel);
        }
    }
}