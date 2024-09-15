import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class LotoGame implements CommandLineRunner {

    private static final int TOTAL_NUMBERS = 90;
    private static final int NUMBERS_PER_CARD = 15;
    private static final int NUMBERS_IN_ROW = 5;
    private static final int TOTAL_ROWS = 3;

    public static void main(String[] args) {
        SpringApplication.run(LotoGame.class, args);
    }

    @Override
    public void run(String... args) {
        List<Integer> bag = createBag();
        List<Player> players = createPlayers(2);
        playGame(bag, players);
    }

    private List<Integer> createBag() {
        List<Integer> bag = new ArrayList<>();
        for (int i = 1; i <= TOTAL_NUMBERS; i++) {
            bag.add(i);
        }
        Collections.shuffle(bag);
        return bag;
    }

    private List<Player> createPlayers(int count) {
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            players.add(new Player("Игрок " + i, generateCard()));
        }
        return players;
    }

    private Card generateCard() {
        Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < NUMBERS_PER_CARD) {
            numbers.add((int) (Math.random() * TOTAL_NUMBERS) + 1);
        }
        return new Card(new ArrayList<>(numbers));
    }

    private void playGame(List<Integer> bag, List<Player> players) {
        Set<Integer> drawnNumbers = new HashSet<>();
        for (Integer number : bag) {
            drawnNumbers.add(number);
            for (Player player : players) {
                if (player.checkWin(drawnNumbers)) {
                    System.out.println(player.getName() + " победил!");
                    return;
                }
            }
        }
    }
}

class Player {
    private String name;
    private Card card;

    public Player(String name, Card card) {
        this.name = name;
        this.card = card;
    }

    public String getName() {
        return name;
    }

    public boolean checkWin(Set<Integer> drawnNumbers) {
        for (List<Integer> row : card.getRows()) {
            if (drawnNumbers.containsAll(row)) {
                return true;
            }
        }
        return false;
    }
}

class Card {
    private List<List<Integer>> rows;

    public Card(List<Integer> numbers) {
        rows = new ArrayList<>();
        for (int i = 0; i < TOTAL_ROWS; i++) {
            rows.add(numbers.subList(i * NUMBERS_IN_ROW, (i + 1) * NUMBERS_IN_ROW));
        }
    }

    public List<List<Integer>> getRows() {
        return rows;
    }
}
