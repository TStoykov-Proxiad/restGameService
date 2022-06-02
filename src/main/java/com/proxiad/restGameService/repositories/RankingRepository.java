package com.proxiad.restGameService.repositories;

import com.proxiad.restGameService.entities.Ranking;
import com.proxiad.restGameService.entities.Statistic;
import org.hibernate.stat.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    @Query("select s from Ranking r " +
            "join r.user u " +
            "join r.statistics s " +
            "where u.username = :username")
    Optional<Set<Statistic>> findStatisticsByUsername(String username);

    @Query("select u from User u " +
            "join Ranking r on u = r.user " +
            "join r.statistics s ")
        //todo finish function, correct where
    List<Ranking> findTopNUsers(@Param(value = "n") int n);

    @Query("select r from Ranking r " +
            "join r.user u " +
            "join r.statistics s " +
            "where u.username = :username")
    Optional<List<Ranking>> findByUsername(String username);
}