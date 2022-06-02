package com.proxiad.restGameService.services.implementations;

import com.proxiad.restGameService.entities.Game;
import com.proxiad.restGameService.entities.User;
import com.proxiad.restGameService.repositories.GameRepository;
import com.proxiad.restGameService.services.GameService;
import com.proxiad.restGameService.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.*;

public class GameServiceImpl implements GameService {
    private GameRepository repository;
    private WordService wordService;

    @Override
    public Game startNewGame(User user) {
        Game hangman = new Game(wordService.getRandomWord(), Instant.now(), user);
        hangman.setWordProgress(initHangManGeneration(hangman));
        return repository.save(hangman);
    }

    @Override
    public Game makeTry(Character input, Long id) {
        Game hangman;
        Optional<Game> optional = repository.findById(id);
        if (!optional.isPresent())
            return null;
        else hangman = optional.get();
        Set<Character> guesses = stringToGuesses(hangman.getGuessedLetters());
        if (guesses.contains(input)) {
            return hangman;
        } else {
            guesses.add(input);
            hangman.setGuessedLetters(guessesToString(guesses));
            if (!hangman.getWord().getWord().contains(input.toString())) {
                hangman.setGuessesLeft(hangman.getGuessesLeft() - 1);
            } else {
                StringBuilder copy = new StringBuilder(hangman.getWordProgress());
                for (int i = 1; i < hangman.getWord().getWord().length() - 1; i++) {
                    if ((hangman.getWordProgress().charAt(i) == '-') && (guesses.contains(hangman.getWord().getWord().charAt(i)))) {
                        copy.setCharAt(i, hangman.getWord().getWord().charAt(i));
                    }
                }
                hangman.setWordProgress(copy.toString());
            }
            return repository.save(hangman);
        }
    }

    @Override
    public List<Game> getActiveGames() {
        return repository.findAllActiveGames().orElseGet(() -> new LinkedList<>());
    }

    @Override
    public List<Game> getAllGames() {
        return repository.findAll();
    }

    @Override
    public Game getById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public boolean isOver(Long gameID) {
        Game game = repository.findById(gameID).get();
        if (game.getEndTime() != null)
            return true;
        return false;
    }

    private String initHangManGeneration(Game hangman) {
        String word = hangman.getWord().getWord();
        StringBuilder current = new StringBuilder(word.length());
        char firstLetter = word.charAt(0);
        char lastLetter = word.charAt(word.length() - 1);
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == firstLetter || word.charAt(i) == lastLetter) {
                current.append(word.charAt(i));
            } else {
                current.append('-');
            }
        }
        Set<Character> guesses = new HashSet<>();
        guesses.add(firstLetter);
        guesses.add(lastLetter);
        hangman.setGuessedLetters(guessesToString(guesses));
        return current.toString();
    }

    private String guessesToString(Set<Character> guesses) {
        return String.join(",", guesses.stream().map(String::valueOf).toList());
    }

    private Set<Character> stringToGuesses(String guessedLetters) {
        Set<Character> guesses = new HashSet<>();
        guesses.addAll(Arrays.stream(guessedLetters.split(",")).map(s -> s.toCharArray()[0]).toList());
        return guesses;
    }

    @Autowired
    public GameServiceImpl(GameRepository repository, WordService wordService) {
        this.repository = repository;
        this.wordService = wordService;
    }
}
