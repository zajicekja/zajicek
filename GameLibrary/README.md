# GameLibrary

## Zadání práce
Semestrální práce na téma GameLibrary, zaměřená na správu her v knihovně, s podporou načítání a ukládání dat ze souborů a správou vlastníků her.

## Motivace
Vybral jsem si toto téma, protože mě zajímá programování aplikací s využitím datových souborů a manipulace s nimi. Chtěl jsem vytvořit aplikaci, která umožní uživateli spravovat kolekci her s možností načítání a ukládání dat z různých typů souborů a také správu vlastníků těchto her.

## Popis problému
Aplikace GameLibrary řeší potřebu uživatelů spravovat kolekci her. Umožňuje přidávání nových her, zobrazování a ukládání existujících her do/z textových a binárních souborů. Nově také umožňuje správu vlastníků jednotlivých her.

## Řešení

### Funkční specifikace
1. **Správa her**
   - Přidání hry do knihovny
   - Zobrazení seznamu her v knihovně
   - Ukládání her do textového souboru
   - Načítání her z textového souboru
   - Ukládání her do binárního souboru
   - Načítání her z binárního souboru

2. **Správa vlastníků**
   - Přidání vlastníka k určité hře
   - Zobrazení vlastníků her v knihovně

### Popis struktury vstupních a výstupních souborů
- **Textové soubory:** Obsahují data o hrách ve formátu: `název, rok_vydání, hodnocení`, kde jednotlivé položky jsou odděleny čárkami.
- **Binární soubory:** Obsahují serializované objekty kolekce her ve formátu Java Serialization.
- **Soubory vlastníků:** Obsahují informace o vlastnících her ve formátu `jméno, název_hry`, kde jednotlivé položky jsou odděleny čárkami.

### Class diagram
```mermaid
classDiagram
    class Game {
        -String title
        -int year
        -Rating rating
        +Game(String title, int year, Rating rating)
        +String getTitle()
        +int getYear()
        +Rating getRating()
        +String toString()
    }
    class Vlastnik {
        -String name
        -String gameTitle
        +Vlastnik(String name, String gameTitle)
        +String getName()
        +String getGameTitle()
    }
    class Library {
        -List<Game> games
        -List<Vlastnik> owners
        +Library()
        +void addGame(Game game)
        +List<Game> getGames()
        +void addOwner(Vlastnik owner)
        +List<Vlastnik> getOwners()
        +boolean loadGamesFromFile(String fileName)
        +boolean loadOwnersFromFile(String fileName)
        +boolean loadGamesFromBinaryFile(String fileName)
        +boolean saveGamesToFile(String fileName)
        +boolean saveGamesToBinaryFile(String fileName)
        +List<String> listOwnersWithGames()
    }
    enum Rating {
        EXCELLENT
        GOOD
        AVERAGE
        POOR
    }
    class Main {
        +main(String[] args)
    }
    class GameMenu {
        +displayMenu(Scanner scanner, Library library)
        -addGame(Scanner scanner, Library library)
        -listGames(Library library)
    }

# Testování

## Testovací soubory

- **games.txt**: Obsahuje seznam her v textovém formátu ve formě: název, rok_vydání, hodnocení.
- **games.dat**: Obsahuje serializovanou kolekci her v binárním formátu.

## Testy

### Přidání hry

- **Vstup**: Název: "Cyberpunk 2077", Rok vydání: 2020, Hodnocení: EXCELLENT
- **Očekávaný výstup**: "Game added successfully."

### Zobrazení seznamu her

- **Vstup**: -
- **Očekávaný výstup**: Seznam her: "Cyberpunk 2077"

### Ukládání her do textového souboru

- **Vstup**: games.txt
- **Očekávaný výstup**: "Games saved successfully to text file."

### Načítání her z textového souboru

- **Vstup**: games.txt
- **Očekávaný výstup**: "Games loaded successfully from text file."

### Ukládání her do binárního souboru

- **Vstup**: games.dat
- **Očekávaný výstup**: "Games saved successfully to binary file."

### Načítání her z binárního souboru

- **Vstup**: games.dat
- **Očekávaný výstup**: "Games loaded successfully from binary file."

### Ošetření nevalidního vstupu - neplatný rok

- **Vstup**: Název: "The Last of Us Part II", Rok vydání: "abc", Hodnocení: EXCELLENT
- **Očekávaný výstup**: "Invalid input for year."

### Ošetření nevalidního vstupu - neplatný název

- **Vstup**: Název: "", Rok vydání: 2018, Hodnocení: GOOD
- **Očekávaný výstup**: "Invalid input for title."

### Pokus o načtení neexistujícího textového souboru

- **Vstup**: non_existing_file.txt
- **Očekávaný výstup**: "Error: File 'non_existing_file.txt' not found."

### Pokus o načtení neexistujícího binárního souboru

- **Vstup**: non_existing_file.dat
- **Očekávaný výstup**: "Error: File 'non_existing_file.dat' not found."

## Popis fungování externí knihovny

Pro serializaci a deserializaci objektů do/z binárních souborů využívám standardního Java Serialization API.
