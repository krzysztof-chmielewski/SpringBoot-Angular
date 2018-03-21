package com.kchmielewski.sda.java6.angular.player.application;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class PlayerService {
    private final PlayerRepository repository;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository repository, TeamRepository teamRepository) {
        this.repository = repository;
        this.teamRepository = teamRepository;
    }

    public Iterable<PlayerDto> all() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public PlayerDto one(Integer id) {
        checkNotNull(id, "Id cannot be null");

        return repository.findById(id).map(this::toDto).orElseThrow(EntityNotFoundException::new);
    }

    public PlayerDto add(PlayerDto dto) {
        checkNotNull(dto, "PlayerEntity cannot be null");
        checkArgument(dto.getId() == null, "Player to be added must not have id");

        return toDto(repository.save(toEntity(dto)));
    }

    public void remove(Integer id) {
        checkNotNull(id, "Id cannot be null");

        repository.deleteById(id);
    }

    public PlayerDto edit(PlayerDto dto) {
        checkNotNull(dto, "PlayerEntity cannot be null");
        checkArgument(dto.getId() != null, "Player to be edited must have id");

        return toDto(repository.save(toEntity(dto)));
    }

    private PlayerDto toDto(PlayerEntity entity) {
        checkNotNull(entity, "PlayerEntity cannot be null");
        return new PlayerDto(entity.getId(), entity.getName(), entity.getSurname(), entity.getTeam().map(TeamEntity::getName).orElse(""));
    }

    private PlayerEntity toEntity(PlayerDto dto) {
        Optional<TeamEntity> team = teamRepository.findByName(dto.getTeamName());
        PlayerEntity entity = new PlayerEntity(dto.getId(), dto.getName(), dto.getSurname());
        if (!team.isPresent()) {
            TeamEntity teamEntity = new TeamEntity(dto.getTeamName());
            entity.setTeam(teamRepository.save(teamEntity));
        } else {
            entity.setTeam(team.get());
        }

        return entity;
    }
}
