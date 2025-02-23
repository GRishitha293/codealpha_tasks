import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StockMarketGUI {
    private JFrame frame;
    private JTextArea stockDisplay;
    private JTextArea portfolioDisplay;
    private JTextField stockInput;
    private JTextField quantityInput;
    private JButton buyButton, sellButton, updateButton, addStockButton;
    private Map<String, Double> stockPrices;
    private Map<String, Integer> portfolio;
    private double balance;
    private Random random;

    public StockMarketGUI() {
        frame = new JFrame("Stock Trading Platform");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        stockPrices = new HashMap<>();
        portfolio = new HashMap<>();
        balance = 10000.0;
        random = new Random();

        stockDisplay = new JTextArea();
        stockDisplay.setEditable(false);
        updateStockDisplay();

        portfolioDisplay = new JTextArea();
        portfolioDisplay.setEditable(false);
        updatePortfolioDisplay();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        stockInput = new JTextField();
        quantityInput = new JTextField();
        inputPanel.add(new JLabel("Stock Symbol:"));
        inputPanel.add(stockInput);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityInput);
        
        buyButton = new JButton("Buy");
        sellButton = new JButton("Sell");
        updateButton = new JButton("Update Prices");
        addStockButton = new JButton("Add New Stock");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(buyButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(addStockButton);

        frame.add(new JScrollPane(stockDisplay), BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(new JScrollPane(portfolioDisplay), BorderLayout.EAST);

        buyButton.addActionListener(e -> buyStock(stockInput.getText().toUpperCase(), Integer.parseInt(quantityInput.getText())));
        sellButton.addActionListener(e -> sellStock(stockInput.getText().toUpperCase(), Integer.parseInt(quantityInput.getText())));
        updateButton.addActionListener(e -> updateStockPrices());
        addStockButton.addActionListener(e -> addNewStock());

        frame.setVisible(true);
    }

    private double getRandomPrice(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    private void updateStockDisplay() {
        StringBuilder sb = new StringBuilder("Stock Prices:\n");
        for (Map.Entry<String, Double> entry : stockPrices.entrySet()) {
            sb.append(entry.getKey()).append(": $").append(String.format("%.2f", entry.getValue())).append("\n");
        }
        stockDisplay.setText(sb.toString());
    }

    private void updatePortfolioDisplay() {
        StringBuilder sb = new StringBuilder("Portfolio:\n");
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" shares, Value: $")
              .append(String.format("%.2f", stockPrices.get(entry.getKey()) * entry.getValue())).append("\n");
        }
        sb.append("Balance: $").append(String.format("%.2f", balance));
        portfolioDisplay.setText(sb.toString());
    }

    private void buyStock(String symbol, int quantity) {
        if (!stockPrices.containsKey(symbol)) {
            JOptionPane.showMessageDialog(frame, "Invalid stock symbol.");
            return;
        }
        double cost = stockPrices.get(symbol) * quantity;
        if (balance >= cost) {
            balance -= cost;
            portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + quantity);
            JOptionPane.showMessageDialog(frame, "Bought " + quantity + " shares of " + symbol);
        } else {
            JOptionPane.showMessageDialog(frame, "Insufficient funds.");
        }
        updatePortfolioDisplay();
    }

    private void sellStock(String symbol, int quantity) {
        if (!portfolio.containsKey(symbol) || portfolio.get(symbol) < quantity) {
            JOptionPane.showMessageDialog(frame, "Not enough shares to sell or invalid stock.");
            return;
        }
        double revenue = stockPrices.get(symbol) * quantity;
        balance += revenue;
        portfolio.put(symbol, portfolio.get(symbol) - quantity);
        if (portfolio.get(symbol) == 0) {
            portfolio.remove(symbol);
        }
        JOptionPane.showMessageDialog(frame, "Sold " + quantity + " shares of " + symbol);
        updatePortfolioDisplay();
    }

    private void updateStockPrices() {
        for (String stock : stockPrices.keySet()) {
            stockPrices.put(stock, stockPrices.get(stock) * (0.95 + (1.05 - 0.95) * random.nextDouble()));
        }
        updateStockDisplay();
        JOptionPane.showMessageDialog(frame, "Stock prices updated!");
    }
    
    private void addNewStock() {
        String symbol = JOptionPane.showInputDialog(frame, "Enter stock symbol:").toUpperCase();
        if (symbol == null || symbol.isEmpty() || stockPrices.containsKey(symbol)) {
            JOptionPane.showMessageDialog(frame, "Invalid or duplicate stock symbol.");
            return;
        }
        double price = getRandomPrice(50, 5000);
        stockPrices.put(symbol, price);
        updateStockDisplay();
        JOptionPane.showMessageDialog(frame, "Added new stock: " + symbol + " at $" + String.format("%.2f", price));
    }

    public static void main(String[] args) {
        new StockMarketGUI();
    }
}
