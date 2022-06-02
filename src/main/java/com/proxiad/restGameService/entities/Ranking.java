package com.proxiad.restGameService.entities;

import org.hibernate.stat.Statistics;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "rankings")
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ranking_id", referencedColumnName = "id")
    private Set<Statistic> statistics;

    public Ranking() {
    }

    public Ranking(User user, Set<Statistic> statistics) {
        this.user = user;
        this.statistics = statistics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Statistic> getStatistics() {
        return Collections.unmodifiableSet(statistics);
    }

    public void setStatistics(Set<Statistic> statistics) {
        this.statistics = statistics;
    }
}