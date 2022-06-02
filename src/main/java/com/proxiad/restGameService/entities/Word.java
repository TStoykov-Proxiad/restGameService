package com.proxiad.restGameService.entities;

import javax.persistence.*;

@Entity
@Table(name = "words")
public class Word {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "word", updatable = false, unique = true, nullable = false)
    private String word;

    public Word() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}