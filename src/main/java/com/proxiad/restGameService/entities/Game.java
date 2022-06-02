package com.proxiad.restGameService.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "word_id", referencedColumnName = "id")
    private Word word;

    @Column(name = "guessed_letters")
    private String guessedLetters;

    @Column(name = "word_progress")
    private String wordProgress;

    @Column(name = "guesses_left")
    private int guessesLeft;

    @Column(name = "time_started")
    private Instant startTime;

    @Column(name = "time_ended")
    private Instant endTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Game() {
    }

    public Game(Word word, Instant startTime, User user) {
        this.word = word;
        this.startTime = startTime;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getGuessedLetters() {
        return guessedLetters;
    }

    public void setGuessedLetters(String guessedLetters) {
        this.guessedLetters = guessedLetters;
    }

    public String getWordProgress() {
        return wordProgress;
    }

    public void setWordProgress(String wordProgress) {
        this.wordProgress = wordProgress;
    }

    public int getGuessesLeft() {
        return guessesLeft;
    }

    public void setGuessesLeft(int guessesLeft) {
        this.guessesLeft = guessesLeft;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
