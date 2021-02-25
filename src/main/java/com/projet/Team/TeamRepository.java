package com.projet.Team;

import com.projet.Tournament.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TeamRepository
        extends JpaRepository<Team, Long> {


    List<Team> findTeamsBytournament(Tournament tournament);
    Optional<Team> findTeamByNameAndTournament(String name, Tournament tournament);

}