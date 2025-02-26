package com.example.tipovacka.rest;

import com.example.tipovacka.entity.TeamEntity;
import com.example.tipovacka.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Týmy", description = "API pro správu týmů")
@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @Operation(summary = "Seznam všech týmů")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public List<TeamEntity> getAllTeams() {
        return teamService.findAllTeams();
    }

    @Operation(summary = "Najít tým podle ID",
            description = "Vrátí detaily týmu podle zadaného ID")
    @ApiResponse(responseCode = "200", description = "Tým nalezen")
    @ApiResponse(responseCode = "404", description = "Tým nenalezen")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public TeamEntity getTeamById(@PathVariable Long id) {
        return teamService.findTeamById(id);
    }

    @Operation(summary = "Vytvořit nový tým",
            description = "Vytvoří nový tým v systému")
    @ApiResponse(responseCode = "200", description = "Tým úspěšně vytvořen")
    @ApiResponse(responseCode = "400", description = "Neplatná data týmu")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public TeamEntity addTeam(@RequestBody TeamEntity team) {
        return teamService.saveTeam(team);
    }

    @Operation(summary = "Smazat tým",
            description = "Smaže tým podle zadaného ID")
    @ApiResponse(responseCode = "200", description = "Tým úspěšně smazán")
    @ApiResponse(responseCode = "404", description = "Tým nenalezen")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }

    @Operation(summary = "Upravit tým",
            description = "Upraví existující tým podle ID")
    @ApiResponse(responseCode = "200", description = "Tým úspěšně upraven")
    @ApiResponse(responseCode = "400", description = "Neplatná data týmu")
    @ApiResponse(responseCode = "404", description = "Tým nenalezen")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public TeamEntity updateTeam(@PathVariable Long id, @RequestBody TeamEntity team) {
        return teamService.updateTeam(id, team);
    }
} 