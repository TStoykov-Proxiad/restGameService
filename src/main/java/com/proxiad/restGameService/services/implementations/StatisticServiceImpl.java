package com.proxiad.restGameService.services.implementations;

import com.proxiad.restGameService.entities.Game;
import com.proxiad.restGameService.entities.Statistic;
import com.proxiad.restGameService.repositories.StatisticRepository;
import com.proxiad.restGameService.services.GameService;
import com.proxiad.restGameService.services.StatisticService;
import org.hibernate.stat.Statistics;

public class StatisticServiceImpl implements StatisticService {
    private StatisticRepository repository;
    private GameService gameService;

    @Override
    public Statistic addGame(Long gameID, int score) {
        if (gameService.isOver(gameID)) {
            return repository.save(new Statistic(gameService.getById(gameID), score));
        }
        return null;
        //TODO implement custom exception?
    }
}
