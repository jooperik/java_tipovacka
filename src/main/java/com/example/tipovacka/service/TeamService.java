package com.example.tipovacka.service;

import com.example.tipovacka.dao.TeamDAO;
import com.example.tipovacka.entity.TeamEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamDAO teamDAO;

    public TeamService(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    public List<TeamEntity> findAllTeams() {
        return teamDAO.findAll();
    }

    public TeamEntity findTeamById(Long id) {
        return teamDAO.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Tým s id %d neexistuje", id)));
    }

    public TeamEntity saveTeam(TeamEntity team) {
        if (teamDAO.findByNazev(team.getNazev()).isPresent()) {
            throw new RuntimeException("Tým s tímto názvem již existuje");
        }
        return teamDAO.save(team);
    }

    public void deleteTeam(Long id) {
        if (!teamDAO.existsById(id)) {
            throw new RuntimeException(String.format("Tým s id %d neexistuje", id));
        }
        teamDAO.deleteById(id);
    }

    public TeamEntity updateTeam(Long id, TeamEntity team) {
        TeamEntity existingTeam = findTeamById(id);
        
        // Kontrola, zda nový název již neexistuje u jiného týmu
        teamDAO.findByNazev(team.getNazev())
                .ifPresent(t -> {
                    if (!t.getId().equals(id)) {
                        throw new RuntimeException("Tým s tímto názvem již existuje");
                    }
                });

        existingTeam.setNazev(team.getNazev());
        existingTeam.setLogo(team.getLogo());
        
        return teamDAO.save(existingTeam);
    }
} 