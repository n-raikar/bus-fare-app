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
```
## Sample Input
```csv
ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN
1, 22-01-2023 13:00:00, ON, Stop1, Company1, Bus37, 5500005555555559
2, 22-01-2023 13:05:00, OFF, Stop2, Company1, Bus37, 5500005555555559
3, 22-01-2023 09:20:00, ON, Stop3, Company1, Bus36, 4111111111111111
4, 23-01-2023 08:00:00, ON, Stop1, Company1, Bus37, 4111111111111111
5, 23-01-2023 08:02:00, OFF, Stop1, Company1, Bus37, 4111111111111111
6, 24-01-2023 16:30:00, OFF, Stop2, Company1, Bus37, 5500005555555559
```
## Sample Output
```csv
"Started","Finished","DurationSecs","FromStopId","ToStopId","ChargeAmount","CompanyId","BusID","PAN","Status"
"22-01-2023 13:00:00","22-01-2023 13:05:00","300","Stop1","Stop2","$3.25","Company1","Bus37","5500005555555559","COMPLETED"
"23-01-2023 08:00:00","23-01-2023 08:02:00","120","Stop1","Stop1","$0","Company1","Bus37","4111111111111111","CANCELLED"
"22-01-2023 09:20:00","","0","Stop3","","$7.30","Company1","Bus36","4111111111111111","INCOMPLETE"
