package com.aryan.ratelimiter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TimelinePanel extends JPanel {

    private final String algorithmName;
    private final Color accentColor;
    private List<Long> timestamps;
    private List<Boolean> results;
    private double hitRate;

    private int visibleCount = 0; // for animation
    private Timer animationTimer;

    private int[] dotX;
    private int dotAxisY;

    public TimelinePanel(String algorithmName, Color accentColor, List<Long> timestamps, List<Boolean> results) {
        this.algorithmName = algorithmName;
        this.accentColor = accentColor;
        setData(timestamps, results);

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(700, 400));
        setToolTipText(""); // enables tooltip system

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateTooltip(e.getX(), e.getY());
            }
        });
    }

    public void setData(List<Long> timestamps, List<Boolean> results) {
        this.timestamps = timestamps;
        this.results = results;

        int allowedCount = 0;
        for (boolean r : results) if (r) allowedCount++;
        this.hitRate = results.isEmpty() ? 0 : (allowedCount * 100.0) / results.size();

        dotX = new int[timestamps.size()];
        startAnimation();
    }

    public void startAnimation() {
        visibleCount = 0;
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        animationTimer = new Timer(150, e -> {
            visibleCount++;
            repaint();
            if (visibleCount >= timestamps.size()) {
                animationTimer.stop();
            }
        });
        animationTimer.start();
    }

    private void updateTooltip(int mouseX, int mouseY) {
        if (dotX == null) return;
        for (int i = 0; i < visibleCount && i < dotX.length; i++) {
            if (Math.abs(mouseX - dotX[i]) <= 9 && Math.abs(mouseY - dotAxisY) <= 9) {
                String status = results.get(i) ? "ALLOWED" : "BLOCKED";
                setToolTipText("t=" + timestamps.get(i) + "  |  " + status);
                return;
            }
        }
        setToolTipText(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int margin = 60;

        g2.setColor(new Color(40, 40, 40));
        g2.setFont(new Font("SansSerif", Font.BOLD, 20));
        g2.drawString(algorithmName, margin, 35);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g2.setColor(new Color(90, 90, 90));
        String hitRateText = String.format("Hit Rate: %.1f%%  (%d/%d allowed)",
                hitRate, countAllowed(), results.size());
        g2.drawString(hitRateText, margin, 58);

        int barX = margin;
        int barY = 68;
        int barWidth = width - 2 * margin;
        int barHeight = 14;

        g2.setColor(new Color(230, 230, 230));
        g2.fillRoundRect(barX, barY, barWidth, barHeight, 8, 8);

        int filledWidth = (int) (barWidth * (hitRate / 100.0));
        g2.setColor(accentColor);
        g2.fillRoundRect(barX, barY, filledWidth, barHeight, 8, 8);

        g2.setColor(new Color(200, 200, 200));
        g2.drawRoundRect(barX, barY, barWidth, barHeight, 8, 8);

        int axisY = height - 80;
        dotAxisY = axisY;
        g2.setColor(new Color(180, 180, 180));
        g2.drawLine(margin, axisY, width - margin, axisY);

        if (timestamps.isEmpty()) return;

        long minTime = timestamps.get(0);
        long maxTime = timestamps.get(timestamps.size() - 1);
        long range = Math.max(1, maxTime - minTime);

        for (int i = 0; i < timestamps.size(); i++) {
            long t = timestamps.get(i);
            int x = margin + (int) (((t - minTime) / (double) range) * (width - 2 * margin));
            dotX[i] = x;

            if (i >= visibleCount) continue; // not revealed yet (animation)

            boolean allowed = results.get(i);

            if (allowed) {
                g2.setColor(new Color(46, 160, 67));
                g2.fillOval(x - 7, axisY - 7, 14, 14);
            } else {
                g2.setColor(new Color(220, 53, 53));
                g2.fillOval(x - 6, axisY - 6, 12, 12);
                g2.setStroke(new BasicStroke(2));
                g2.setColor(new Color(150, 30, 30));
                g2.drawOval(x - 9, axisY - 9, 18, 18);
            }

            g2.setColor(new Color(120, 120, 120));
            g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            g2.drawString("t=" + t, x - 10, axisY + 25);
        }

        int legendY = height - 25;
        g2.setColor(new Color(46, 160, 67));
        g2.fillOval(margin, legendY - 10, 12, 12);
        g2.setColor(new Color(70, 70, 70));
        g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g2.drawString("Allowed", margin + 18, legendY);

        g2.setColor(new Color(220, 53, 53));
        g2.fillOval(margin + 100, legendY - 10, 12, 12);
        g2.setColor(new Color(70, 70, 70));
        g2.drawString("Blocked", margin + 118, legendY);
    }

    private int countAllowed() {
        int c = 0;
        for (boolean r : results) if (r) c++;
        return c;
    }

    public double getHitRate() {
        return hitRate;
    }

    public String getAlgorithmNamePublic() {
        return algorithmName;
    }
}