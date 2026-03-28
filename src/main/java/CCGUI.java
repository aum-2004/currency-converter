import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Objects;

public class CCGUI extends JFrame {
    private JTextField amount;
    private JLabel titlelabel;
    private JLabel subtitlelabel;
    private JLabel amtlabel;
    private JLabel fromlabel;
    private JComboBox<String> fromcomboBox;
    private JLabel tolabel;
    private JComboBox<String> tocomboBox;
    private AnimatedButton CONVERTButton;
    private AnimatedButton swapButton;
    private JPanel CC;
    private JLabel resultLabel;
    private FadePanel resultPanel;
    private JLabel rateInfoLabel;
    private LoadingSpinner loadingSpinner;

    public String fromCurrencyVal;
    public String fromCurrency;

    public String toCurrencyVal;
    public String toCurrency;

    public double enteredAmount;
    public Double rate;

    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(99, 102, 241);      // Indigo
    private static final Color PRIMARY_HOVER = new Color(79, 70, 229);       // Darker indigo
    private static final Color BACKGROUND_COLOR = new Color(15, 23, 42);     // Dark slate
    private static final Color CARD_COLOR = new Color(30, 41, 59);           // Slate
    private static final Color TEXT_PRIMARY = new Color(248, 250, 252);      // Almost white
    private static final Color TEXT_SECONDARY = new Color(148, 163, 184);    // Gray
    private static final Color INPUT_BG = new Color(51, 65, 85);             // Slate gray
    private static final Color INPUT_FOCUS = new Color(71, 85, 105);         // Lighter slate
    private static final Color SUCCESS_COLOR = new Color(34, 197, 94);       // Green
    private static final Color SUCCESS_BG = new Color(22, 78, 50);           // Dark green
    private static final Color ERROR_COLOR = new Color(248, 113, 113);       // Red
    private static final Color ERROR_BG = new Color(127, 29, 29);            // Dark red
    private static final Color ACCENT_COLOR = new Color(251, 191, 36);       // Amber

    private static final String[] CURRENCIES = {
        "Select source currency",
        "USD - US Dollar",
        "EUR - Euro",
        "GBP - British Pound",
        "JPY - Japanese Yen",
        "AUD - Australian Dollar",
        "CAD - Canadian Dollar",
        "CHF - Swiss Franc",
        "CNY - Chinese Yuan",
        "INR - Indian Rupee",
        "MXN - Mexican Peso",
        "SGD - Singapore Dollar",
        "HKD - Hong Kong Dollar",
        "NOK - Norwegian Krone",
        "SEK - Swedish Krona",
        "DKK - Danish Krone",
        "NZD - New Zealand Dollar",
        "ZAR - South African Rand",
        "RUB - Russian Ruble",
        "BRL - Brazilian Real",
        "AED - UAE Dirham",
        "AFN - Afghan Afghani",
        "ALL - Albanian Lek",
        "AMD - Armenian Dram",
        "ARS - Argentine Peso",
        "AZN - Azerbaijani Manat",
        "BAM - Bosnia Mark",
        "BBD - Barbadian Dollar",
        "BDT - Bangladeshi Taka",
        "BGN - Bulgarian Lev",
        "BHD - Bahraini Dinar",
        "BND - Brunei Dollar",
        "BOB - Bolivian Boliviano",
        "BSD - Bahamian Dollar",
        "BWP - Botswanan Pula",
        "BYN - Belarusian Ruble",
        "BZD - Belize Dollar",
        "CDF - Congolese Franc",
        "CLP - Chilean Peso",
        "COP - Colombian Peso",
        "CRC - Costa Rican Colón",
        "CZK - Czech Koruna",
        "DOP - Dominican Peso",
        "DZD - Algerian Dinar",
        "EGP - Egyptian Pound",
        "ETB - Ethiopian Birr",
        "FJD - Fijian Dollar",
        "GEL - Georgian Lari",
        "GHS - Ghanaian Cedi",
        "GTQ - Guatemalan Quetzal",
        "HNL - Honduran Lempira",
        "HRK - Croatian Kuna",
        "HUF - Hungarian Forint",
        "IDR - Indonesian Rupiah",
        "ILS - Israeli Shekel",
        "IQD - Iraqi Dinar",
        "IRR - Iranian Rial",
        "ISK - Icelandic Króna",
        "JMD - Jamaican Dollar",
        "JOD - Jordanian Dinar",
        "KES - Kenyan Shilling",
        "KRW - South Korean Won",
        "KWD - Kuwaiti Dinar",
        "KZT - Kazakhstani Tenge",
        "LBP - Lebanese Pound",
        "LKR - Sri Lankan Rupee",
        "MAD - Moroccan Dirham",
        "MDL - Moldovan Leu",
        "MNT - Mongolian Tugrik",
        "MUR - Mauritian Rupee",
        "MYR - Malaysian Ringgit",
        "NGN - Nigerian Naira",
        "NIO - Nicaraguan Córdoba",
        "NPR - Nepalese Rupee",
        "OMR - Omani Rial",
        "PAB - Panamanian Balboa",
        "PEN - Peruvian Sol",
        "PHP - Philippine Peso",
        "PKR - Pakistani Rupee",
        "PLN - Polish Zloty",
        "PYG - Paraguayan Guarani",
        "QAR - Qatari Rial",
        "RON - Romanian Leu",
        "RSD - Serbian Dinar",
        "RWF - Rwandan Franc",
        "SAR - Saudi Riyal",
        "SDG - Sudanese Pound",
        "THB - Thai Baht",
        "TND - Tunisian Dinar",
        "TRY - Turkish Lira",
        "TTD - Trinidad Dollar",
        "TWD - Taiwan Dollar",
        "TZS - Tanzanian Shilling",
        "UAH - Ukrainian Hryvnia",
        "UGX - Ugandan Shilling",
        "UYU - Uruguayan Peso",
        "UZS - Uzbekistan Som",
        "VES - Venezuelan Bolívar",
        "VND - Vietnamese Dong",
        "XAF - CFA Franc BEAC",
        "XOF - CFA Franc BCEAO",
        "YER - Yemeni Rial",
        "ZMW - Zambian Kwacha"
    };

    private static final String[] TO_CURRENCIES = {
        "Select target currency",
        "USD - US Dollar",
        "EUR - Euro",
        "GBP - British Pound",
        "JPY - Japanese Yen",
        "AUD - Australian Dollar",
        "CAD - Canadian Dollar",
        "CHF - Swiss Franc",
        "CNY - Chinese Yuan",
        "INR - Indian Rupee",
        "MXN - Mexican Peso",
        "SGD - Singapore Dollar",
        "HKD - Hong Kong Dollar",
        "NOK - Norwegian Krone",
        "SEK - Swedish Krona",
        "DKK - Danish Krone",
        "NZD - New Zealand Dollar",
        "ZAR - South African Rand",
        "RUB - Russian Ruble",
        "BRL - Brazilian Real",
        "AED - UAE Dirham",
        "AFN - Afghan Afghani",
        "ALL - Albanian Lek",
        "AMD - Armenian Dram",
        "ARS - Argentine Peso",
        "AZN - Azerbaijani Manat",
        "BAM - Bosnia Mark",
        "BBD - Barbadian Dollar",
        "BDT - Bangladeshi Taka",
        "BGN - Bulgarian Lev",
        "BHD - Bahraini Dinar",
        "BND - Brunei Dollar",
        "BOB - Bolivian Boliviano",
        "BSD - Bahamian Dollar",
        "BWP - Botswanan Pula",
        "BYN - Belarusian Ruble",
        "BZD - Belize Dollar",
        "CDF - Congolese Franc",
        "CLP - Chilean Peso",
        "COP - Colombian Peso",
        "CRC - Costa Rican Colón",
        "CZK - Czech Koruna",
        "DOP - Dominican Peso",
        "DZD - Algerian Dinar",
        "EGP - Egyptian Pound",
        "ETB - Ethiopian Birr",
        "FJD - Fijian Dollar",
        "GEL - Georgian Lari",
        "GHS - Ghanaian Cedi",
        "GTQ - Guatemalan Quetzal",
        "HNL - Honduran Lempira",
        "HRK - Croatian Kuna",
        "HUF - Hungarian Forint",
        "IDR - Indonesian Rupiah",
        "ILS - Israeli Shekel",
        "IQD - Iraqi Dinar",
        "IRR - Iranian Rial",
        "ISK - Icelandic Króna",
        "JMD - Jamaican Dollar",
        "JOD - Jordanian Dinar",
        "KES - Kenyan Shilling",
        "KRW - South Korean Won",
        "KWD - Kuwaiti Dinar",
        "KZT - Kazakhstani Tenge",
        "LBP - Lebanese Pound",
        "LKR - Sri Lankan Rupee",
        "MAD - Moroccan Dirham",
        "MDL - Moldovan Leu",
        "MNT - Mongolian Tugrik",
        "MUR - Mauritian Rupee",
        "MYR - Malaysian Ringgit",
        "NGN - Nigerian Naira",
        "NIO - Nicaraguan Córdoba",
        "NPR - Nepalese Rupee",
        "OMR - Omani Rial",
        "PAB - Panamanian Balboa",
        "PEN - Peruvian Sol",
        "PHP - Philippine Peso",
        "PKR - Pakistani Rupee",
        "PLN - Polish Zloty",
        "PYG - Paraguayan Guarani",
        "QAR - Qatari Rial",
        "RON - Romanian Leu",
        "RSD - Serbian Dinar",
        "RWF - Rwandan Franc",
        "SAR - Saudi Riyal",
        "SDG - Sudanese Pound",
        "THB - Thai Baht",
        "TND - Tunisian Dinar",
        "TRY - Turkish Lira",
        "TTD - Trinidad Dollar",
        "TWD - Taiwan Dollar",
        "TZS - Tanzanian Shilling",
        "UAH - Ukrainian Hryvnia",
        "UGX - Ugandan Shilling",
        "UYU - Uruguayan Peso",
        "UZS - Uzbekistan Som",
        "VES - Venezuelan Bolívar",
        "VND - Vietnamese Dong",
        "XAF - CFA Franc BEAC",
        "XOF - CFA Franc BCEAO",
        "YER - Yemeni Rial",
        "ZMW - Zambian Kwacha"
    };

    public CCGUI() {
        initComponents();
        
        CONVERTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });

        swapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Animate swap button rotation effect
                swapButton.triggerPulse();
                int fromIndex = fromcomboBox.getSelectedIndex();
                int toIndex = tocomboBox.getSelectedIndex();
                fromcomboBox.setSelectedIndex(toIndex);
                tocomboBox.setSelectedIndex(fromIndex);
            }
        });
    }

    private void performConversion() {
        fromCurrencyVal = (String) fromcomboBox.getSelectedItem();
        toCurrencyVal = (String) tocomboBox.getSelectedItem();

        if (amount.getText().trim().isEmpty()) {
            showResult("Please enter an amount", "", false);
            return;
        }

        try {
            enteredAmount = Double.parseDouble(amount.getText().trim());
        } catch (NumberFormatException ex) {
            showResult("Please enter a valid number", "", false);
            return;
        }

        if (Objects.equals(fromCurrencyVal, "Select source currency")) {
            showResult("Please select source currency", "", false);
            return;
        } else {
            fromCurrency = fromCurrencyVal.substring(0, 3);
        }

        if (Objects.equals(toCurrencyVal, "Select target currency")) {
            showResult("Please select target currency", "", false);
            return;
        } else {
            toCurrency = toCurrencyVal.substring(0, 3);
        }

        // Show loading state
        CONVERTButton.setEnabled(false);
        loadingSpinner.setVisible(true);
        loadingSpinner.startAnimation();
        resultPanel.fadeOut();

        SwingWorker<Double, Void> worker = new SwingWorker<Double, Void>() {
            @Override
            protected Double doInBackground() {
                return getExchangeRate(fromCurrency, toCurrency, "");
            }

            @Override
            protected void done() {
                loadingSpinner.stopAnimation();
                loadingSpinner.setVisible(false);
                
                try {
                    rate = get();
                    if (rate == 0.0) {
                        showResult("Conversion failed. Please try again.", "", false);
                    } else {
                        double convertedAmount = enteredAmount * rate;
                        DecimalFormat df = new DecimalFormat("#,##0.00");
                        String result = df.format(enteredAmount) + " " + fromCurrency + " = " + 
                                       df.format(convertedAmount) + " " + toCurrency;
                        DecimalFormat rateFormat = new DecimalFormat("#,##0.######");
                        String rateInfo = "Rate: 1 " + fromCurrency + " = " + rateFormat.format(rate) + " " + toCurrency;
                        showResult(result, rateInfo, true);
                    }
                } catch (Exception ex) {
                    showResult("Error occurred during conversion", "", false);
                } finally {
                    CONVERTButton.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    private void showResult(String message, String rateInfo, boolean success) {
        resultLabel.setText(message);
        rateInfoLabel.setText(rateInfo);
        
        if (success) {
            resultPanel.setTargetColor(SUCCESS_BG);
            resultLabel.setForeground(SUCCESS_COLOR);
            rateInfoLabel.setForeground(new Color(134, 239, 172));
        } else {
            resultPanel.setTargetColor(ERROR_BG);
            resultLabel.setForeground(ERROR_COLOR);
            rateInfoLabel.setForeground(ERROR_COLOR);
        }
        
        resultPanel.fadeIn();
    }

    private void initComponents() {
        // Main panel with gradient-like background
        CC = new GradientPanel();
        CC.setLayout(new BoxLayout(CC, BoxLayout.Y_AXIS));
        CC.setBorder(new EmptyBorder(40, 50, 40, 50));

        // Header section
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        titlelabel = new JLabel("💱 Currency Converter");
        titlelabel.setForeground(TEXT_PRIMARY);
        titlelabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titlelabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        subtitlelabel = new JLabel("Real-time exchange rates at your fingertips");
        subtitlelabel.setForeground(TEXT_SECONDARY);
        subtitlelabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitlelabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtitlelabel.setBorder(new EmptyBorder(5, 0, 0, 0));

        headerPanel.add(titlelabel);
        headerPanel.add(subtitlelabel);

        // Card panel for the form
        JPanel cardPanel = createRoundedPanel(CARD_COLOR, 20);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        cardPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.setMaximumSize(new Dimension(500, 500));

        // Amount section
        amtlabel = new JLabel("Amount");
        amtlabel.setForeground(TEXT_PRIMARY);
        amtlabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        amtlabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        amount = createStyledTextField("Enter amount");
        amount.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Currency selection panel (side by side)
        JPanel currencyPanel = new JPanel();
        currencyPanel.setLayout(new GridLayout(1, 3, 15, 0));
        currencyPanel.setOpaque(false);
        currencyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        currencyPanel.setMaximumSize(new Dimension(500, 80));

        // From currency
        JPanel fromPanel = new JPanel();
        fromPanel.setLayout(new BoxLayout(fromPanel, BoxLayout.Y_AXIS));
        fromPanel.setOpaque(false);

        fromlabel = new JLabel("From");
        fromlabel.setForeground(TEXT_PRIMARY);
        fromlabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        fromlabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        fromcomboBox = createStyledComboBox(CURRENCIES);
        fromcomboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        fromPanel.add(fromlabel);
        fromPanel.add(Box.createVerticalStrut(8));
        fromPanel.add(fromcomboBox);

        // Swap button panel
        JPanel swapPanel = new JPanel();
        swapPanel.setLayout(new BoxLayout(swapPanel, BoxLayout.Y_AXIS));
        swapPanel.setOpaque(false);
        swapPanel.add(Box.createVerticalStrut(25));

        swapButton = createSwapButton();
        swapButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        swapPanel.add(swapButton);

        // To currency
        JPanel toPanel = new JPanel();
        toPanel.setLayout(new BoxLayout(toPanel, BoxLayout.Y_AXIS));
        toPanel.setOpaque(false);

        tolabel = new JLabel("To");
        tolabel.setForeground(TEXT_PRIMARY);
        tolabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tolabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        tocomboBox = createStyledComboBox(TO_CURRENCIES);
        tocomboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        toPanel.add(tolabel);
        toPanel.add(Box.createVerticalStrut(8));
        toPanel.add(tocomboBox);

        currencyPanel.add(fromPanel);
        currencyPanel.add(swapPanel);
        currencyPanel.add(toPanel);

        // Convert button
        CONVERTButton = createStyledButton("Convert");
        CONVERTButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Loading spinner
        loadingSpinner = new LoadingSpinner();
        loadingSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        loadingSpinner.setVisible(false);

        // Result panel with fade animation
        resultPanel = new FadePanel(SUCCESS_BG, 12);
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultPanel.setMaximumSize(new Dimension(500, 80));
        resultPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        resultLabel = new JLabel("");
        resultLabel.setForeground(SUCCESS_COLOR);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rateInfoLabel = new JLabel("");
        rateInfoLabel.setForeground(new Color(134, 239, 172));
        rateInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rateInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultPanel.add(resultLabel);
        resultPanel.add(Box.createVerticalStrut(5));
        resultPanel.add(rateInfoLabel);

        // Add components to card
        cardPanel.add(amtlabel);
        cardPanel.add(Box.createVerticalStrut(8));
        cardPanel.add(amount);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(currencyPanel);
        cardPanel.add(Box.createVerticalStrut(25));
        cardPanel.add(CONVERTButton);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(loadingSpinner);
        cardPanel.add(Box.createVerticalStrut(15));
        cardPanel.add(resultPanel);

        // Add all to main panel
        CC.add(headerPanel);
        CC.add(Box.createVerticalStrut(30));
        CC.add(cardPanel);
    }

    // Gradient background panel
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gradient = new GradientPaint(
                0, 0, BACKGROUND_COLOR,
                0, getHeight(), new Color(30, 27, 75)
            );
            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }

    // Panel with fade in/out animation
    class FadePanel extends JPanel {
        private float alpha = 0f;
        private Timer fadeTimer;
        private boolean fadingIn = false;
        private Color targetColor;
        private final int radius;

        public FadePanel(Color bgColor, int radius) {
            this.targetColor = bgColor;
            this.radius = radius;
            setOpaque(false);
            setVisible(false);
        }

        public void setTargetColor(Color color) {
            this.targetColor = color;
        }

        public void fadeIn() {
            setVisible(true);
            fadingIn = true;
            if (fadeTimer != null && fadeTimer.isRunning()) {
                fadeTimer.stop();
            }
            fadeTimer = new Timer(16, e -> {
                alpha += 0.1f;
                if (alpha >= 1f) {
                    alpha = 1f;
                    fadeTimer.stop();
                }
                repaint();
            });
            fadeTimer.start();
        }

        public void fadeOut() {
            fadingIn = false;
            if (fadeTimer != null && fadeTimer.isRunning()) {
                fadeTimer.stop();
            }
            fadeTimer = new Timer(16, e -> {
                alpha -= 0.15f;
                if (alpha <= 0f) {
                    alpha = 0f;
                    fadeTimer.stop();
                    setVisible(false);
                }
                repaint();
            });
            fadeTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setColor(targetColor);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Loading spinner with animation
    class LoadingSpinner extends JPanel {
        private int angle = 0;
        private Timer spinTimer;

        public LoadingSpinner() {
            setOpaque(false);
            setPreferredSize(new Dimension(500, 40));
            setMaximumSize(new Dimension(500, 40));
        }

        public void startAnimation() {
            if (spinTimer != null && spinTimer.isRunning()) return;
            spinTimer = new Timer(50, e -> {
                angle = (angle + 30) % 360;
                repaint();
            });
            spinTimer.start();
        }

        public void stopAnimation() {
            if (spinTimer != null) {
                spinTimer.stop();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int radius = 12;

            for (int i = 0; i < 8; i++) {
                double theta = Math.toRadians(angle + i * 45);
                int x = (int) (centerX + radius * Math.cos(theta));
                int y = (int) (centerY + radius * Math.sin(theta));
                
                float alpha = (8 - i) / 8f;
                g2.setColor(new Color(99, 102, 241, (int)(alpha * 255)));
                g2.fillOval(x - 4, y - 4, 8, 8);
            }
            g2.dispose();
        }
    }

    // Animated button with smooth hover and pulse effects
    class AnimatedButton extends JButton {
        private float hoverProgress = 0f;
        private float pulseProgress = 0f;
        private Timer hoverTimer;
        private Timer pulseTimer;
        private boolean isHovered = false;
        private boolean isCircular;
        private Color baseColor;
        private Color hoverColor;

        public AnimatedButton(String text, boolean circular, Color base, Color hover) {
            super(text);
            this.isCircular = circular;
            this.baseColor = base;
            this.hoverColor = hover;
            
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    animateHover(true);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    animateHover(false);
                }
            });
        }

        private void animateHover(boolean hovering) {
            if (hoverTimer != null && hoverTimer.isRunning()) {
                hoverTimer.stop();
            }
            hoverTimer = new Timer(16, e -> {
                if (hovering) {
                    hoverProgress += 0.15f;
                    if (hoverProgress >= 1f) {
                        hoverProgress = 1f;
                        hoverTimer.stop();
                    }
                } else {
                    hoverProgress -= 0.15f;
                    if (hoverProgress <= 0f) {
                        hoverProgress = 0f;
                        hoverTimer.stop();
                    }
                }
                repaint();
            });
            hoverTimer.start();
        }

        public void triggerPulse() {
            if (pulseTimer != null && pulseTimer.isRunning()) {
                pulseTimer.stop();
            }
            pulseProgress = 0f;
            pulseTimer = new Timer(16, e -> {
                pulseProgress += 0.1f;
                if (pulseProgress >= 1f) {
                    pulseProgress = 0f;
                    pulseTimer.stop();
                }
                repaint();
            });
            pulseTimer.start();
        }

        private Color interpolateColor(Color c1, Color c2, float progress) {
            int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * progress);
            int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * progress);
            int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * progress);
            return new Color(r, g, b);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color currentColor = interpolateColor(baseColor, hoverColor, hoverProgress);
            
            // Draw pulse effect
            if (pulseProgress > 0) {
                float pulseAlpha = 1f - pulseProgress;
                float pulseScale = 1f + pulseProgress * 0.3f;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, pulseAlpha * 0.5f));
                g2.setColor(ACCENT_COLOR);
                if (isCircular) {
                    int size = (int) (getWidth() * pulseScale);
                    int offset = (getWidth() - size) / 2;
                    g2.fillOval(offset, offset, size, size);
                } else {
                    g2.fill(new RoundRectangle2D.Float(
                        -pulseProgress * 10, -pulseProgress * 5,
                        getWidth() + pulseProgress * 20, getHeight() + pulseProgress * 10,
                        12, 12));
                }
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }

            // Draw button
            g2.setColor(currentColor);
            if (isCircular) {
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(interpolateColor(TEXT_SECONDARY, ACCENT_COLOR, hoverProgress));
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(1, 1, getWidth() - 3, getHeight() - 3);
            } else {
                // Add subtle shadow for depth
                g2.setColor(new Color(0, 0, 0, 30));
                g2.fill(new RoundRectangle2D.Float(2, 3, getWidth() - 2, getHeight() - 2, 12, 12));
                g2.setColor(currentColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() - 2, 12, 12));
            }

            // Draw text
            g2.setColor(isCircular ? interpolateColor(TEXT_SECONDARY, ACCENT_COLOR, hoverProgress) : TEXT_PRIMARY);
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2 - (isCircular ? 0 : 1);
            g2.drawString(getText(), x, y);

            g2.dispose();
        }
    }

    private JPanel createRoundedPanel(Color bgColor, int radius) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField() {
            private Color currentBg = INPUT_BG;
            private Color borderColor = INPUT_BG;
            private Timer focusTimer;

            {
                addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        animateFocus(true);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        animateFocus(false);
                    }
                });
            }

            private void animateFocus(boolean focused) {
                if (focusTimer != null && focusTimer.isRunning()) {
                    focusTimer.stop();
                }
                Color targetBg = focused ? INPUT_FOCUS : INPUT_BG;
                Color targetBorder = focused ? PRIMARY_COLOR : INPUT_BG;
                
                focusTimer = new Timer(16, e -> {
                    currentBg = interpolateColor(currentBg, targetBg, 0.2f);
                    borderColor = interpolateColor(borderColor, targetBorder, 0.2f);
                    
                    if (colorDistance(currentBg, targetBg) < 5 && colorDistance(borderColor, targetBorder) < 5) {
                        currentBg = targetBg;
                        borderColor = targetBorder;
                        focusTimer.stop();
                    }
                    repaint();
                });
                focusTimer.start();
            }

            private Color interpolateColor(Color c1, Color c2, float progress) {
                int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * progress);
                int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * progress);
                int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * progress);
                return new Color(r, g, b);
            }

            private double colorDistance(Color c1, Color c2) {
                return Math.sqrt(Math.pow(c1.getRed() - c2.getRed(), 2) +
                               Math.pow(c1.getGreen() - c2.getGreen(), 2) +
                               Math.pow(c1.getBlue() - c2.getBlue(), 2));
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw background
                g2.setColor(currentBg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                
                // Draw border
                if (!borderColor.equals(INPUT_BG)) {
                    g2.setColor(borderColor);
                    g2.setStroke(new BasicStroke(2));
                    g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 12, 12));
                }
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        field.setMaximumSize(new Dimension(500, 50));
        field.setPreferredSize(new Dimension(500, 50));
        return field;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items) {
            @Override
            public void updateUI() {
                super.updateUI();
                setUI(new StyledComboBoxUI());
            }
        };
        combo.setBackground(INPUT_BG);
        combo.setForeground(TEXT_PRIMARY);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBorder(BorderFactory.createEmptyBorder());
        combo.setMaximumSize(new Dimension(180, 45));
        combo.setPreferredSize(new Dimension(180, 45));
        combo.setFocusable(false);
        
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? PRIMARY_COLOR : CARD_COLOR);
                setForeground(TEXT_PRIMARY);
                setBorder(new EmptyBorder(10, 12, 10, 12));
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                
                // Style the list itself
                list.setBackground(CARD_COLOR);
                list.setSelectionBackground(PRIMARY_COLOR);
                list.setSelectionForeground(TEXT_PRIMARY);
                
                return this;
            }
        });

        // Style the popup
        Object popup = combo.getUI().getAccessibleChild(combo, 0);
        if (popup instanceof JPopupMenu) {
            JPopupMenu popupMenu = (JPopupMenu) popup;
            popupMenu.setBorder(BorderFactory.createLineBorder(INPUT_BG, 1));
            popupMenu.setBackground(CARD_COLOR);
        }
        
        return combo;
    }

    // Custom ComboBox UI for modern styling
    class StyledComboBoxUI extends javax.swing.plaf.basic.BasicComboBoxUI {
        
        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton() {
                @Override
                public void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Draw arrow
                    g2.setColor(TEXT_SECONDARY);
                    int centerX = getWidth() / 2;
                    int centerY = getHeight() / 2;
                    int[] xPoints = {centerX - 5, centerX + 5, centerX};
                    int[] yPoints = {centerY - 2, centerY - 2, centerY + 4};
                    g2.fillPolygon(xPoints, yPoints, 3);
                    
                    g2.dispose();
                }
            };
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            return button;
        }

        @Override
        protected ComboPopup createPopup() {
            return new StyledComboPopup(comboBox);
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            // Don't paint default background
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw rounded background
            g2.setColor(INPUT_BG);
            g2.fill(new RoundRectangle2D.Float(0, 0, c.getWidth(), c.getHeight(), 10, 10));
            
            // Draw border on hover
            if (c.getMousePosition() != null) {
                g2.setColor(new Color(99, 102, 241, 100));
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, c.getWidth() - 2, c.getHeight() - 2, 10, 10));
            }
            
            g2.dispose();
            super.paint(g, c);
        }

        @Override
        protected void installDefaults() {
            super.installDefaults();
            LookAndFeel.installProperty(comboBox, "opaque", false);
        }
    }

    // Custom styled popup for ComboBox
    class StyledComboPopup extends javax.swing.plaf.basic.BasicComboPopup {
        public StyledComboPopup(JComboBox<Object> combo) {
            super(combo);
        }

        @Override
        protected JScrollPane createScroller() {
            JScrollPane scroller = new JScrollPane(list,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroller.setBackground(CARD_COLOR);
            scroller.getViewport().setBackground(CARD_COLOR);
            scroller.setBorder(BorderFactory.createLineBorder(INPUT_BG, 1));
            
            // Style scrollbar
            scroller.getVerticalScrollBar().setBackground(CARD_COLOR);
            scroller.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = INPUT_BG;
                    this.trackColor = CARD_COLOR;
                }

                @Override
                protected JButton createDecreaseButton(int orientation) {
                    return createZeroButton();
                }

                @Override
                protected JButton createIncreaseButton(int orientation) {
                    return createZeroButton();
                }

                private JButton createZeroButton() {
                    JButton button = new JButton();
                    button.setPreferredSize(new Dimension(0, 0));
                    return button;
                }

                @Override
                protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(INPUT_BG);
                    g2.fill(new RoundRectangle2D.Float(thumbBounds.x + 2, thumbBounds.y, 
                            thumbBounds.width - 4, thumbBounds.height, 8, 8));
                    g2.dispose();
                }

                @Override
                protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                    g.setColor(CARD_COLOR);
                    g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                }
            });
            
            return scroller;
        }

        @Override
        protected void configurePopup() {
            super.configurePopup();
            setBackground(CARD_COLOR);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(99, 102, 241, 50), 1),
                BorderFactory.createEmptyBorder(5, 0, 5, 0)
            ));
        }

        @Override
        protected void configureList() {
            super.configureList();
            list.setBackground(CARD_COLOR);
            list.setSelectionBackground(PRIMARY_COLOR);
            list.setSelectionForeground(TEXT_PRIMARY);
        }
    }

    private AnimatedButton createStyledButton(String text) {
        AnimatedButton button = new AnimatedButton(text, false, PRIMARY_COLOR, PRIMARY_HOVER);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(TEXT_PRIMARY);
        button.setMaximumSize(new Dimension(500, 50));
        button.setPreferredSize(new Dimension(500, 50));
        return button;
    }

    private AnimatedButton createSwapButton() {
        AnimatedButton button = new AnimatedButton("⇄", true, CARD_COLOR, INPUT_BG);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setForeground(TEXT_SECONDARY);
        button.setPreferredSize(new Dimension(45, 45));
        button.setMaximumSize(new Dimension(45, 45));
        button.setToolTipText("Swap currencies");
        return button;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default look and feel
        }
        
        SwingUtilities.invokeLater(() -> {
            CCGUI ccgui = new CCGUI();
            ccgui.setContentPane(ccgui.CC);
            ccgui.setTitle("Currency Converter");
            ccgui.setSize(600, 580);
            ccgui.setMinimumSize(new Dimension(600, 580));
            ccgui.setLocationRelativeTo(null);
            ccgui.setVisible(true);
            ccgui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    private static double getExchangeRate(String fromCurrency, String toCurrency, String apiKey) {
        String apiUrl = "https://api.apilayer.com/exchangerates_data/latest?symbols=" + toCurrency + "&base=" + fromCurrency;
        JSONObject rate;
        double finalRate = 0.0;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("apikey", "KXLQKSNCBuA4CCEaw84gp1ErTkgLFX05");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONObject jsonObject = new JSONObject(response.toString());
                rate = jsonObject.getJSONObject("rates");
                finalRate = rate.getDouble(toCurrency);
                System.out.println(rate);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return finalRate;
    }
}
