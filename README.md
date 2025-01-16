# Smart-Clock
Here's an enhanced README for your project, formatted in Markdown and tailored to catch the attention of potential employers:

---

# Java Clock and Weather Display with OpenAI Integration

## Project Overview

This Java-based application serves as a comprehensive showcase of real-time data handling, API integration, and responsive user interface design using Java Swing. The application not only maintains and displays the local time but also integrates external APIs to fetch weather conditions and popular Java frameworks via OpenAI, enhancing its functionality as a practical tool for everyday use.

## Features

- **Real-Time Clock Management:** Utilizes multithreading to update and display the time every second, ensuring high accuracy and performance.
- **Alarm Functionality:** Allows users to set and edit alarm times, with visual indications and toggle options for easy management.
- **Weather Updates:** Connects to a weather API to fetch and display current weather conditions, including temperature and atmospheric conditions, for a predefined location.
- **OpenAI Integration:** Fetches information about the latest and most popular Java frameworks using OpenAI's powerful API, presenting the information in a user-friendly format on the main display.

## Technical Components

- **ClockMonitor:** Central class that handles time updates and alarm logic, ensuring that all components stay accurate and synchronized.
- **ClockDisplayPanel:** The GUI component that renders time, alarms, and fetched data on the screen, offering interactive functionalities such as edit modes for time and alarms.
- **TimeThread:** A dedicated thread that ensures the clock ticks every second, updates the GUI, and checks for alarm conditions.
- **WeatherService:** Implements HTTP client functionalities to retrieve weather information and processes JSON responses to update the UI accordingly.
- **OpenAIService:** Leverages the OpenAI API to provide users with valuable data about Java frameworks, demonstrating the application's extendibility and adaptability to different APIs.

## Technologies Used

- **Java and Java Swing:** For core application development and user interface creation.
- **HttpClient and JSON:** Used for networking and data handling.
- **Dotenv:** Manages environmental variables to securely store and retrieve API keys.

## Installation and Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourgithub/java-clock-weather
   ```
2. Navigate to the project directory and compile the code:
   ```bash
   cd java-clock-weather
   javac *.java
   ```
3. Run the application:
   ```bash
   java Main
   ```

## Potential for Future Development

- **Customizable UI:** Allow users to customize the look and feel of the clock and weather displays.
- **Extended Weather Forecasts:** Integrate extended weather forecast features.
- **Localization and Internationalization:** Support for multiple time zones and languages to enhance user accessibility and experience.

## Personal Learning Outcomes

Through this project, I have honed my skills in real-time application development, learned to integrate and utilize external APIs effectively, and deepened my understanding of Java Swing for creating responsive user interfaces. This experience has prepared me to tackle similar real-world problems and develop robust software solutions.

---
