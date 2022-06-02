package com.proxiad.restGameService.services;

import com.proxiad.restGameService.entities.Statistic;

public interface StatisticService {
    Statistic addGame(Long gameID, int score);
}
