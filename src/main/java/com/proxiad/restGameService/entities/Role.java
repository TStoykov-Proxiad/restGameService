package com.proxiad.restGameService.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_authorities",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Set<Authority> authorities;

    public Role() {
    }

    public Role(String name) {
        setName(name);
    }

    public Role(String name, Collection<Authority> authorities) {
        this.name = name;
        setAuthorities(authorities);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        if (!name.contains("ROLE_"))
            return "ROLE_" + name;
        return name;
    }

    public void setName(String name) {
        if (!name.contains("ROLE_"))
            name = "ROLE_" + name;
        this.name = name;
    }

    public Set<Authority> getAuthorities() {
        return Collections.unmodifiableSet(authorities);
    }

    public void setAuthorities(Collection<Authority> authorities) {
        this.authorities = new HashSet<>();
        this.authorities.addAll(authorities);
    }

    @Override
    public String getAuthority() {
        return getName();
    }

    @Override
    public String toString() {
        return getAuthority();
    }
}
