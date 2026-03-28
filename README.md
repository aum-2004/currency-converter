# 💱 Currency Converter

A modern, sleek currency converter desktop application built with Java Swing featuring real-time exchange rates and smooth animations.

![Java](https://img.shields.io/badge/Java-11+-orange)
![License](https://img.shields.io/badge/License-MIT-blue)

## ✨ Features

- **Real-time Exchange Rates** - Fetches live rates from ExchangeRates API
- **100+ Currencies** - Support for major world currencies
- **Modern Dark UI** - Beautiful gradient background with indigo accents
- **Smooth Animations** - Hover effects, fade transitions, and loading spinners
- **One-click Swap** - Quickly swap source and target currencies
- **Responsive Design** - Clean, intuitive interface

## 🖼️ Screenshots

The application features a modern dark theme with:
- Gradient background (dark slate to deep indigo)
- Rounded input fields with focus animations
- Animated convert button with hover effects
- Fade-in result panel with rate information

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6+

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/currency-converter.git
   cd currency-converter
   ```

2. Build the project:
   ```bash
   mvn clean compile
   ```

3. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="CCGUI"
   ```

## 🛠️ Built With

- **Java Swing** - GUI framework
- **Maven** - Build automation
- **ExchangeRates API** - Real-time currency data
- **org.json** - JSON parsing

## 📁 Project Structure

```
currency-converter/
├── src/
│   └── main/
│       └── java/
│           └── CCGUI.java      # Main application
├── pom.xml                      # Maven configuration
└── README.md
```

## 🎨 UI Components

- **GradientPanel** - Background with smooth color gradient
- **AnimatedButton** - Buttons with hover and pulse effects
- **FadePanel** - Result panel with fade-in/out animations
- **LoadingSpinner** - Animated loading indicator
- **StyledComboBox** - Custom dark-themed dropdowns

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

## 👤 Author

**Aryan Modi**

---

⭐ Star this repo if you find it useful!
