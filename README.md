# 🎓 Brighties - System Rezerwacji Korepetycji

System o architekturze mikroserwisowej służący do zarządzania korepetycjami, umożliwiający uczniom rezerwowanie spotkań z nauczycielami. Projekt wykorzystuje Spring Boot, Apache Kafka, gRPC i komunikację REST do zapewnienia elastycznej i skalowalnej architektury.

---

## 🧩 Mikroserwisy

### 1. `teacher-service`
Zarządza informacjami o nauczycielach.

#### Funkcje:
- Pobierz wszystkich nauczycieli
- Pobierz nauczyciela po ID
- Stwórz nowego nauczyciela
- Zaktualizuj dane nauczyciela
- Usuń nauczyciela

#### gRPC:
- `CheckTeacherExistence` – walidacja czy nauczyciel istnieje

---

### 2. `student-service`
Zarządza uczniami i ich danymi.

#### Funkcje:
- Pobierz wszystkich uczniów
- Pobierz ucznia po ID
- Stwórz ucznia
- Zaktualizuj dane ucznia
- Usuń ucznia


#### gRPC:
- `CheckStudentExistence` – sprawdzenie czy uczeń istnieje

---

### 3. `availability-service`
Zarządza terminami dostępności nauczycieli.

#### Funkcje:
- Pobierz dostępność według nauczyciela i daty
- Stwórz nowy termin dostępności
- Zaktualizuj lub usuń termin
- Waliduj dostępność slotu


#### gRPC:
- `CheckSlotAvailability` – sprawdzenie czy slot jest dostępny

#### Kafka:
- Konsumuje event `SlotReservedEvent` – ustawia slot jako niedostępny

---

### 4. `reservation-service`
Obsługuje rezerwacje między uczniami a nauczycielami.

#### Funkcje:
- Pobierz rezerwacje danego nauczyciela/ucznia
- Stwórz, zaktualizuj lub usuń rezerwację


#### gRPC:
- Walidacja danych przez:
  - `TeacherService` (czy nauczyciel istnieje)
  - `StudentService` (czy uczeń istnieje)
  - `AvailabilityService` (czy slot dostępny)

#### Kafka:
- Wysyła event `SlotReservedEvent` do `availability-service`

---

## 📦 W planach lub w trakcie implementacji

### 5. `notification-service`
Wysyła powiadomienia do użytkowników (SMS, push, itp.).

- Integracja z zewnętrznym API do SMS
- Asynchroniczna obsługa kolejki zdarzeń (Kafka)

### 6. `send-email-service`
Wysyła wiadomości e-mail z potwierdzeniem rezerwacji, anulowaniem itp.

- Obsługa SMTP (np. Gmail lub SendGrid)
- Szablony HTML do wiadomości

### 7. `authentication-service`
Obsługuje rejestrację, logowanie i weryfikację JWT.

- Rejestracja/logowanie ucznia i nauczyciela
- JWT i Refresh Tokeny
- Autoryzacja endpointów

### 8. `api-gateway`
Centralna brama API dla wszystkich serwisów.

---

## 🛰 Komunikacja

- ✅ **REST API** – dla użytkowników zewnętrznych
- ✅ **gRPC** – pomiędzy mikroserwisami (Teacher, Student, Availability)
- ✅ **Kafka** – do komunikacji asynchronicznej (Reservation ➝ Availability)

---

## ⚙️ Technologie

- Java 17+
- Spring Boot 3
- Spring Data JPA
- MySql
- Apache Kafka
- gRPC
- Maven

---

