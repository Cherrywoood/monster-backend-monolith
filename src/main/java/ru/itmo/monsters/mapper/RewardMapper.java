package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.RewardDTO;
import ru.itmo.monsters.model.MonsterEntity;
import ru.itmo.monsters.model.RewardEntity;
import ru.itmo.monsters.service.MonsterService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RewardMapper {

    private final ModelMapper modelMapper;
    private final MonsterService monsterService;

    public RewardDTO mapEntityToDto(RewardEntity rewardEntity) {
        RewardDTO rewardDTO = modelMapper.map(rewardEntity, RewardDTO.class);
        rewardDTO.setMonstersIds(rewardEntity.getMonsters().stream().map(MonsterEntity::getId).collect(Collectors.toList()));
        return rewardDTO;
    }

    public RewardEntity mapDtoToEntity(RewardDTO rewardDTO) {
        RewardEntity rewardEntity = modelMapper.map(rewardDTO, RewardEntity.class);
        rewardEntity.setMonsters(rewardDTO.getMonstersIds().stream().map(monsterService::findById).collect(Collectors.toList()));
        return rewardEntity;
    }

}
