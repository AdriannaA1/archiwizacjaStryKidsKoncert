import java.util.*;

// Interfejs reprezentujący dokument
interface Document {
    String getTitle();
    int getYear();
    String getCategory();
    String getStorageLocation();
    UUID getUUID();
    int getNumberOfCopies();
    void setNumberOfCopies(int numberOfCopies);
    void addCopy();
    void removeCopy();
}

// Klasa reprezentująca pojedynczy dokument koncertowy
class ConcertDocument implements Document {
    protected String title; // Zmiana dostępu na protected
    protected int year; // Zmiana dostępu na protected
    private String category;
    protected String storageLocation; // Zmiana dostępu na protected
    private UUID uuid;
    private int numberOfCopies;
    private List<String> songs = new ArrayList<>();
    private Map<String, String> members = new HashMap<>();

    public ConcertDocument(String title, int year, String category, String storageLocation) {
        this.title = title;
        this.year = year;
        this.category = category;
        this.storageLocation = storageLocation;
        this.uuid = UUID.randomUUID();
        this.numberOfCopies = 1; // Domyślnie jeden egzemplarz
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getStorageLocation() {
        return storageLocation;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    @Override
    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    @Override
    public void addCopy() {
        numberOfCopies++;
    }

    @Override
    public void removeCopy() {
        if (numberOfCopies > 0) {
            numberOfCopies--;
        }
    }

    public List<String> getSongs() {
        return songs;
    }

    public Map<String, String> getMembers() {
        return members;
    }
}

// Klasa do budowania dokumentów koncertowych
class ConcertDocumentBuilder {
    private String title;
    private int year;
    private String category;
    private String storageLocation; // Zmiana dostępu na protected
    private Map<String, String> members = new HashMap<>();
    private List<String> songs = new ArrayList<>();

    // Ustawienie tytułu koncertu
    public ConcertDocumentBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    // Ustawienie roku koncertu
    public ConcertDocumentBuilder setYear(int year) {
        this.year = year;
        return this;
    }

    // Ustawienie kategorii koncertu
    public ConcertDocumentBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    // Ustawienie lokalizacji przechowywania koncertu
    public ConcertDocumentBuilder setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
        return this;
    }

    // Dodanie członka zespołu do koncertu
    public ConcertDocumentBuilder addMember(String name, String role) {
        members.put(name, role);
        return this;
    }

    // Dodanie piosenki do koncertu
    public ConcertDocumentBuilder addSong(String song) {
        songs.add(song);
        return this;
    }

    // Budowanie obiektu dokumentu koncertowego
    public ConcertDocument build() {
        ConcertDocument concertDocument = new ConcertDocument(title, year, category, storageLocation);
        concertDocument.getMembers().putAll(members);
        concertDocument.getSongs().addAll(songs);
        return concertDocument;
    }
}

// Klasa reprezentująca system archiwizacji dokumentów
class DocumentArchive {
    private List<Document> documents = new ArrayList<>();

    // Metoda do dodawania nowych dokumentów
    public void addDocument(Document document) {
        documents.add(document);
    }

    // Metoda do usuwania dokumentów
    public void removeDocument(Document document) {
        documents.remove(document);
    }

    // Metoda do wyszukiwania dokumentów po tytule, roku lub miejscu przechowywania
    public List<Document> searchDocuments(String keyword) {
        List<Document> results = new ArrayList<>();
        for (Document document : documents) {
            if (document.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    String.valueOf(document.getYear()).contains(keyword) ||
                    document.getStorageLocation().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(document);
            }
        }
        return results;
    }

    // Metoda do wyświetlania wszystkich dokumentów
    public void displayAllDocuments() {
        System.out.println("Wszystkie dokumenty:");
        for (Document document : documents) {
            System.out.println(document.getTitle());
        }
    }

    // Metoda do wyświetlania koncertów
    public void displayConcerts() {
        System.out.println("Koncerty:");
        for (Document document : documents) {
            if (document instanceof ConcertDocument) {
                System.out.println(document.getTitle());
            }
        }
    }

    // Metoda do wyświetlania piosenek wraz z przypisanymi koncertami
    public void displaySongsWithConcerts() {
        System.out.println("Piosenki:");
        for (Document document : documents) {
            if (document instanceof ConcertDocument) {
                ConcertDocument concertDocument = (ConcertDocument) document;
                for (String song : concertDocument.getSongs()) {
                    System.out.println(song + " - " + concertDocument.getTitle());
                }
            }
        }
    }

    // Metoda do wyświetlania artystów
    public void displayArtists() {
        System.out.println("Artyści:");
        for (Document document : documents) {
            if (document instanceof ConcertDocument) {
                ConcertDocument concertDocument = (ConcertDocument) document;
                for (String artist : concertDocument.getMembers().keySet()) {
                    System.out.println(artist);
                }
            }
        }
    }

    // Metoda do wyświetlania lat z przypisanymi koncertami
    public void displayYearsWithConcerts() {
        System.out.println("Daty:");
        Set<Integer> years = new HashSet<>();
        for (Document document : documents) {
            years.add(document.getYear());
        }
        for (Integer year : years) {
            for (Document document : documents) {
                if (document.getYear() == year) {
                    System.out.println(year + " - " + document.getTitle());
                }
            }
        }
    }

    // Metoda do wyświetlania miejsc z przypisanymi koncertami
    public void displayLocationsWithConcerts() {
        System.out.println("Miejsca:");
        Set<String> locations = new HashSet<>();
        for (Document document : documents) {
            locations.add(document.getStorageLocation());
        }
        for (String location : locations) {
            for (Document document : documents) {
                if (document.getStorageLocation().equals(location)) {
                    System.out.println(location + " - " + document.getTitle());
                }
            }
        }
    }

    // Metoda do wyświetlania wszystkich informacji
    public void displayAll() {
        displayConcerts();
        displaySongsWithConcerts();
        displayArtists();
        displayYearsWithConcerts();
        displayLocationsWithConcerts();
    }

    // Metoda do wyszukiwania koncertu po nazwie piosenki
    public void findConcertBySong(String songName) {
        boolean found = false;
        for (Document document : documents) {
            if (document instanceof ConcertDocument) {
                ConcertDocument concertDocument = (ConcertDocument) document;
                if (concertDocument.getSongs().contains(songName)) {
                    System.out.println("Koncert: " + concertDocument.getTitle());
                    System.out.println("Data: " + concertDocument.getYear());
                    System.out.println("Miejsce: " + concertDocument.getStorageLocation());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono koncertu dla piosenki: " + songName);
        }
    }

    // Metoda do wyszukiwania koncertu po dacie
    public void findConcertByDate(int year) {
        boolean found = false;
        for (Document document : documents) {
            if (document instanceof ConcertDocument && document.getYear() == year) {
                ConcertDocument concertDocument = (ConcertDocument) document;
                System.out.println("Koncert: " + concertDocument.getTitle());
                System.out.println("Data: " + concertDocument.getYear());
                System.out.println("Miejsce: " + concertDocument.getStorageLocation());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono koncertu dla roku: " + year);
        }
    }

    // Metoda do wyszukiwania koncertu po miejscu
    public void findConcertByLocation(String location) {
        boolean found = false;
        for (Document document : documents) {
            if (document instanceof ConcertDocument && document.getStorageLocation().equalsIgnoreCase(location)) {
                ConcertDocument concertDocument = (ConcertDocument) document;
                System.out.println("Koncert: " + concertDocument.getTitle());
                System.out.println("Data: " + concertDocument.getYear());
                System.out.println("Miejsce: " + concertDocument.getStorageLocation());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono koncertu dla miejsca: " + location);
        }
    }

    // Metoda do wyszukiwania piosenek po nazwie koncertu
    public void findSongsByConcertTitle(String concertTitle) {
        boolean found = false;
        for (Document document : documents) {
            if (document instanceof ConcertDocument && document.getTitle().equalsIgnoreCase(concertTitle)) {
                ConcertDocument concertDocument = (ConcertDocument) document;
                System.out.println("Piosenki dla koncertu '" + concertTitle + "':");
                for (String song : concertDocument.getSongs()) {
                    System.out.println(song);
                }
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono koncertu o tytule: " + concertTitle);
        }
    }

    // Metoda do wyświetlania artystów po koncercie
    public void findArtistsByConcert(String concertTitle) {
        boolean found = false;
        for (Document document : documents) {
            if (document instanceof ConcertDocument && document.getTitle().equalsIgnoreCase(concertTitle)) {
                ConcertDocument concertDocument = (ConcertDocument) document;
                System.out.println("Artyści dla koncertu '" + concertTitle + "':");
                for (String artist : concertDocument.getMembers().keySet()) {
                    System.out.println(artist);
                }
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono koncertu o tytule: " + concertTitle);
        }
    }

    // Metoda do modyfikacji tytułu koncertu
    public void modifyConcertTitle(String currentTitle, String newTitle) {
        boolean found = false;
        for (Document document : documents) {
            if (document instanceof ConcertDocument && document.getTitle().equalsIgnoreCase(currentTitle)) {
                ConcertDocument concertDocument = (ConcertDocument) document;
                concertDocument.title = newTitle; // Ustawienie nowego tytułu
                System.out.println("Zmieniono tytuł koncertu '" + currentTitle + "' na '" + newTitle + "'.");
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono koncertu o tytule: " + currentTitle);
        }
    }

    // Metoda do modyfikacji piosenek w koncercie
    public void modifySongsInConcert(String concertTitle, List<String> newSongs) {
        boolean found = false;
        for (Document document : documents) {
            if (document instanceof ConcertDocument && document.getTitle().equalsIgnoreCase(concertTitle)) {
                ConcertDocument concertDocument = (ConcertDocument) document;
                concertDocument.getSongs().clear(); // Usunięcie wszystkich piosenek
                concertDocument.getSongs().addAll(newSongs); // Dodanie nowych piosenek
                System.out.println("Zaktualizowano listę piosenek dla koncertu '" + concertTitle + "'.");
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono koncertu o tytule: " + concertTitle);
        }
    }

    // Metoda do modyfikacji daty koncertu
    public void modifyConcertDate(String concertTitle, int newYear) {
        boolean found = false;
        for (Document document : documents) {
            if (document instanceof ConcertDocument && document.getTitle().equalsIgnoreCase(concertTitle)) {
                ConcertDocument concertDocument = (ConcertDocument) document;
                concertDocument.year = newYear; // Ustawienie nowej daty
                System.out.println("Zmieniono rok koncertu '" + concertTitle + "' na '" + newYear + "'.");
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono koncertu o tytule: " + concertTitle);
        }
    }

    // Metoda do modyfikacji artystów w koncercie
    public void modifyArtistsInConcert(String concertTitle, Map<String, String> newMembers) {
        boolean found = false;
        for (Document document : documents) {
            if (document instanceof ConcertDocument && document.getTitle().equalsIgnoreCase(concertTitle)) {
                ConcertDocument concertDocument = (ConcertDocument) document;
                concertDocument.getMembers().clear(); // Usunięcie wszystkich artystów
                concertDocument.getMembers().putAll(newMembers); // Dodanie nowych artystów
                System.out.println("Zaktualizowano listę artystów dla koncertu '" + concertTitle + "'.");
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono koncertu o tytule: " + concertTitle);
        }
    }

    // Metoda do modyfikacji miejsca koncertu
    public void modifyConcertLocation(String concertTitle, String newLocation) {
        boolean found = false;
        for (Document document : documents) {
            if (document instanceof ConcertDocument && document.getTitle().equalsIgnoreCase(concertTitle)) {
                ConcertDocument concertDocument = (ConcertDocument) document;
                concertDocument.storageLocation = newLocation; // Ustawienie nowego miejsca
                System.out.println("Zmieniono miejsce koncertu '" + concertTitle + "' na '" + newLocation + "'.");
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono koncertu o tytule: " + concertTitle);
        }
    }
    // Metoda do usuwania koncertu
    public void removeConcert(String concertTitle) {
        boolean removed = false;
        for (Iterator<Document> iterator = documents.iterator(); iterator.hasNext();) {
            Document document = iterator.next();
            if (document instanceof ConcertDocument && document.getTitle().equalsIgnoreCase(concertTitle)) {
                iterator.remove();
                System.out.println("Usunięto koncert o tytule: " + concertTitle);
                removed = true;
            }
        }
        if (!removed) {
            System.out.println("Nie znaleziono koncertu o tytule: " + concertTitle);
        }
    }

}

// Główna klasa programu
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DocumentArchive archive = new DocumentArchive();
        List<String> history = new ArrayList<>();

        // Tworzenie przykładowych dokumentów koncertowych
        ConcertDocument concert1 = new ConcertDocumentBuilder()
                .setTitle("Concert 1")
                .setYear(2023)
                .setCategory("Rock")
                .setStorageLocation("Location 1")
                .addMember("John Doe", "Lead Guitar")
                .addMember("Jane Smith", "Vocalist")
                .addSong("Song 1")
                .addSong("Song 2")
                .build();
        ConcertDocument concert2 = new ConcertDocumentBuilder()
                .setTitle("Concert 2")
                .setYear(2024)
                .setCategory("Pop")
                .setStorageLocation("Location 2")
                .addMember("Alice Johnson", "Drummer")
                .addMember("Bob Brown", "Bassist")
                .addSong("Song 3")
                .addSong("Song 4")
                .build();

        archive.addDocument(concert1);
        archive.addDocument(concert2);

        boolean exit = false;

        while (!exit) {
            System.out.println("\nWybierz opcję:");
            System.out.println("1. Wszystkie koncerty");
            System.out.println("2. Wszystkie piosenki");
            System.out.println("3. Artyści");
            System.out.println("4. Daty");
            System.out.println("5. Miejsca");
            System.out.println("6. Wszystko");
            System.out.println("7. Znajdź koncert po piosence");
            System.out.println("8. Znajdź koncert po dacie");
            System.out.println("9. Znajdź koncert po miejscu");
            System.out.println("10. Znajdź piosenki po nazwie koncertu");
            System.out.println("11. Znajdź artystów po koncercie");
            System.out.println("12. Modyfikuj tytuł koncertu");
            System.out.println("13. Modyfikuj piosenki w koncercie");
            System.out.println("14. Modyfikuj datę koncertu");
            System.out.println("15. Modyfikuj artystów w koncercie");
            System.out.println("16. Modyfikuj miejsce koncertu");
            System.out.println("17. Dodaj koncert");
            System.out.println("18. Usuń koncert");
            System.out.println("19. Wyświetl historię zmian");
            System.out.println("20. Wyjdź");
            System.out.print("Twój wybór: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Oczyszczenie bufora

            switch (choice) {
                case 1:
                    archive.displayConcerts();
                    break;
                case 2:
                    archive.displaySongsWithConcerts();
                    break;
                case 3:
                    archive.displayArtists();
                    break;
                case 4:
                    archive.displayYearsWithConcerts();
                    break;
                case 5:
                    archive.displayLocationsWithConcerts();
                    break;
                case 6:
                    archive.displayAll();
                    break;
                case 7:
                    System.out.print("Podaj nazwę piosenki: ");
                    String song = scanner.nextLine();
                    archive.findConcertBySong(song);
                    break;
                case 8:
                    System.out.print("Podaj rok koncertu: ");
                    int year = scanner.nextInt();
                    archive.findConcertByDate(year);
                    break;
                case 9:
                    System.out.print("Podaj miejsce koncertu: ");
                    String location = scanner.nextLine();
                    archive.findConcertByLocation(location);
                    break;
                case 10:
                    System.out.print("Podaj nazwę koncertu: ");
                    String concertTitle = scanner.nextLine();
                    archive.findSongsByConcertTitle(concertTitle);
                    break;
                case 11:
                    System.out.print("Podaj nazwę koncertu: ");
                    String concert = scanner.nextLine();
                    archive.findArtistsByConcert(concert);
                    break;
                case 12:
                    System.out.print("Podaj obecny tytuł koncertu: ");
                    String currentTitle = scanner.nextLine();
                    System.out.print("Podaj nowy tytuł koncertu: ");
                    String newTitle = scanner.nextLine();
                    archive.modifyConcertTitle(currentTitle, newTitle);
                    history.add("Zmieniono tytuł koncertu '" + currentTitle + "' na '" + newTitle + "'.");
                    break;
                case 13:
                    System.out.print("Podaj tytuł koncertu: ");
                    String title = scanner.nextLine();
                    System.out.print("Podaj nowe piosenki oddzielone przecinkami: ");
                    String[] newSongs = scanner.nextLine().split(",");
                    List<String> songList = Arrays.asList(newSongs);
                    archive.modifySongsInConcert(title, songList);
                    history.add("Zaktualizowano listę piosenek dla koncertu '" + title + "'.");
                    break;
                case 14:
                    System.out.print("Podaj tytuł koncertu: ");
                    String concertTitleToModify = scanner.nextLine();
                    System.out.print("Podaj nowy rok: ");
                    int newYear = scanner.nextInt();
                    archive.modifyConcertDate(concertTitleToModify, newYear);
                    history.add("Zmieniono rok koncertu '" + concertTitleToModify + "' na '" + newYear + "'.");
                    break;
                case 15:
                    System.out.print("Podaj tytuł koncertu: ");
                    String concertToModify = scanner.nextLine();
                    System.out.print("Podaj nowych artystów (imię i rola oddzielone przecinkami): ");
                    String[] artists = scanner.nextLine().split(",");
                    Map<String, String> artistMap = new HashMap<>();
                    for (int i = 0; i < artists.length; i += 2) {
                        artistMap.put(artists[i], artists[i + 1]);
                    }
                    archive.modifyArtistsInConcert(concertToModify, artistMap);
                    history.add("Zaktualizowano listę artystów dla koncertu '" + concertToModify + "'.");
                    break;
                case 16:
                    System.out.print("Podaj tytuł koncertu: ");
                    String concertTitleToChangeLocation = scanner.nextLine();
                    System.out.print("Podaj nowe miejsce: ");
                    String newLocation = scanner.nextLine();
                    archive.modifyConcertLocation(concertTitleToChangeLocation, newLocation);
                    history.add("Zmieniono miejsce koncertu '" + concertTitleToChangeLocation + "' na '" + newLocation + "'.");
                    break;
                case 17:
                    // Dodawanie koncertu
                    System.out.print("Podaj nazwę koncertu: ");
                    String newConcertTitle = scanner.nextLine();
                    System.out.print("Podaj rok koncertu: ");
                    int newConcertYear = scanner.nextInt();
                    scanner.nextLine(); // Oczyszczenie bufora
                    System.out.print("Podaj kategorię koncertu: ");
                    String newConcertCategory = scanner.nextLine();
                    System.out.print("Podaj lokalizację koncertu: ");
                    String newConcertLocation = scanner.nextLine();

                    ConcertDocumentBuilder newConcertBuilder = new ConcertDocumentBuilder()
                            .setTitle(newConcertTitle)
                            .setYear(newConcertYear)
                            .setCategory(newConcertCategory)
                            .setStorageLocation(newConcertLocation);

                    System.out.print("Podaj liczbę piosenek w koncercie: ");
                    int numberOfSongs = scanner.nextInt();
                    scanner.nextLine(); // Oczyszczenie bufora
                    for (int i = 0; i < numberOfSongs; i++) {
                        System.out.print("Podaj tytuł piosenki " + (i + 1) + ": ");
                        String songTitle = scanner.nextLine();
                        newConcertBuilder.addSong(songTitle);
                    }

                    System.out.print("Podaj liczbę artystów w koncercie: ");
                    int numberOfArtists = scanner.nextInt();
                    scanner.nextLine(); // Oczyszczenie bufora
                    for (int i = 0; i < numberOfArtists; i++) {
                        System.out.print("Podaj imię artysty " + (i + 1) + ": ");
                        String artistName = scanner.nextLine();
                        System.out.print("Podaj rolę artysty " + (i + 1) + ": ");
                        String artistRole = scanner.nextLine();
                        newConcertBuilder.addMember(artistName, artistRole);
                    }

                    archive.addDocument(newConcertBuilder.build());
                    history.add("Dodano nowy koncert '" + newConcertTitle + "'.");
                    break;
                case 18:
                    // Usuwanie koncertu
                    System.out.print("Podaj nazwę koncertu do usunięcia: ");
                    String concertToDelete = scanner.nextLine();
                    archive.removeConcert(concertToDelete);
                    history.add("Usunięto koncert '" + concertToDelete + "'.");
                    break;
                case 19:
                    // Wyświetlanie historii zmian
                    System.out.println("\nHistoria zmian:");
                    for (String change : history) {
                        System.out.println(change);
                    }
                    break;
                case 20:
                    exit = true;
                    break;
                default:
                    System.out.println("Nieprawidłowa opcja. Spróbuj ponownie.");
            }
        }
    }
}