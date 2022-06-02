package com.proxiad.restGameService.services.implementations;

import com.proxiad.restGameService.entities.Ranking;
import com.proxiad.restGameService.entities.Statistic;
import com.proxiad.restGameService.repositories.RankingRepository;
import com.proxiad.restGameService.services.RankingService;
import com.proxiad.restGameService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RankingServiceImpl implements RankingService {

    private RankingRepository repository;
    private UserService userService;

    @Override
    public Ranking addRanking(Statistic statistic, String username) {
        Set<Statistic> allStatistics = repository.findStatisticsByUsername(username).orElseGet(() -> new HashSet<>());
        allStatistics.add(statistic);
        Ranking ranking = new Ranking(userService.getByUsername(username), allStatistics);
        return repository.save(ranking);
    }

    @Override
    public List<Ranking> getByUsername(String username) {
        return repository.findByUsername(username).get();
        //TODO implement custom exception
    }

    @Autowired
    public RankingServiceImpl(RankingRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }
}
