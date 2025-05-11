# Littlepay Coding Exercise 

This project processes tap-on and tap-off card events to generate trips with calculated fares and statuses. It produces a `trips.csv` file based on the input `taps.csv`.

---

## 🧠 Assumptions

- Input CSV is **well-formed** with valid dates and values.
- Taps may be **out of order**; code will sort them.
- Trip identity is based on **PAN + BusID + CompanyId**.
- An **OFF** tap without a matching **ON** is ignored.
- Remaining unmatched **ON** taps are marked **INCOMPLETE** and charged the **max fare** from that stop.
- Fare table is **symmetric** and hardcoded for Stop1–Stop3.

---

## 🛠️ Build and Run

```bash
# 1. Build
mvn clean package

# 2. Run
java -jar target/bus-fare-app.jar path/to/taps.csv path/to/trips.csv

# 2. Running Tests
mvn test

#3. Check Test Coverage
mvn clean verify
# open target/site/jacoco/index.html
