package com.proxiad.restGameService.services;

import com.proxiad.restGameService.entities.Ranking;
import com.proxiad.restGameService.entities.Statistic;

import java.util.List;

public interface RankingService {
    Ranking addRanking(Statistic statistic, String username);

    List<Ranking> getByUsername(String username);
}
