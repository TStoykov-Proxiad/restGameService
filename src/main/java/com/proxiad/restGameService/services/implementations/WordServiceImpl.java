package com.proxiad.restGameService.services.implementations;

import com.proxiad.restGameService.entities.Word;
import com.proxiad.restGameService.repositories.WordRepository;
import com.proxiad.restGameService.services.WordService;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

public class WordServiceImpl implements WordService {
    private WordRepository repository;
    @Override
    public Word getRandomWord() throws NoSuchElementException {
        Random random = new Random();
        return repository.findById(random.nextLong(1, repository.count() + 1)).get();
    }
}
