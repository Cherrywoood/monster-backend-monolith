package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.RewardDTO;
import ru.itmo.monsters.model.MonsterEntity;
import ru.itmo.monsters.model.RewardEntity;
import ru.itmo.monsters.service.MonsterService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RewardMapper {

    private final ModelMapper modelMapper;
    private final MonsterService monsterService;

    public RewardDTO mapEntityToDto(RewardEntity rewardEntity) {
        RewardDTO rewardDTO = modelMapper.map(rewardEntity, RewardDTO.class);
        rewardDTO.setMonstersIds(getIdsFromListOfEntities(rewardEntity.getMonsters()));
        return rewardDTO;
    }

    public RewardEntity mapDtoToEntity(RewardDTO rewardDTO) {
        RewardEntity rewardEntity = modelMapper.map(rewardDTO, RewardEntity.class);
        rewardEntity.setMonsters(getListOfEntitiesFromIds(rewardDTO.getMonstersIds()));
        return rewardEntity;
    }

    private List<UUID> getIdsFromListOfEntities(List<MonsterEntity> list) {
        ArrayList<UUID> ids = new ArrayList<>();
        list.forEach(e -> ids.add(e.getId()));
        return ids;
    }

    private List<MonsterEntity> getListOfEntitiesFromIds(List<UUID> list) {
        ArrayList<MonsterEntity> entities = new ArrayList<>();
        list.forEach(id -> entities.add(monsterService.findById(id)));
        return entities;
    }

}
