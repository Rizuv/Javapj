# Javapj - komunikator tekstowy

*WORK IN PROGRESS*

Aplikacja do komunikacji tekstowej miedzy dwoma klientami, z planowanym szyfrowaniem end-to-end i archiwizacja wiadomosci w bazie MariaDB. Projekt edukacyjny w Javie.

---
**Technologie:**\
Java, Maven, MariaDB, Jackson

---
**Uruchomienie:**
Wymaga konfiguracji pliku config.json
Patrz config_example.json

---
**Program zawiera funkcjonalne api do zarzadzania plikami JSON, obejmuje:** \
-usuwanie, \
-dodawanie, \
-szukanie danych w drzewie JSON

---
**Klasa Client:**
-generowanie unikalnego id przypisywanego danemu uzytkownikowi

---

## Status
- [x] API do zarzadzania danymi w plikach JSON (dodawanie, usuwanie, wyszukiwanie)
- [x] Klasa klienta z generowanym unikalnym ID
- [ ] Przekazywanie zapytan sieciowych miedzy klientami
- [ ] Archiwizacja zapytan w bazie danych
- [ ] Szyfrowanie end-to-end
