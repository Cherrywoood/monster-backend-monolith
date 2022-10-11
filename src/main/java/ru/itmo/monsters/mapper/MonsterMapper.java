package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.monster.MonsterDTO;
import ru.itmo.monsters.dto.monster.MonsterRatingDTO;
import ru.itmo.monsters.model.FearActionEntity;
import ru.itmo.monsters.model.MonsterEntity;
import ru.itmo.monsters.model.RewardEntity;
import ru.itmo.monsters.service.FearActionService;
import ru.itmo.monsters.service.RewardService;
import ru.itmo.monsters.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class MonsterMapper {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final RewardService rewardService;
    private final FearActionService fearActionService;

    public MonsterDTO mapEntityToDto(MonsterEntity monsterEntity) {
        MonsterDTO monsterDTO = modelMapper.map(monsterEntity, MonsterDTO.class);
        monsterDTO.setUserId(monsterEntity.getUserEntity().getId());
        monsterDTO.setFearActionsIds(getIdsFromListOfFearActionEntities(monsterEntity.getFearActions()));
        monsterDTO.setRewardsIds(getIdsFromListOfRewardEntities(monsterEntity.getRewards()));
        return monsterDTO;
    }

    public MonsterEntity mapDtoToEntity(MonsterDTO monsterDTO) {
        MonsterEntity monsterEntity = modelMapper.map(monsterDTO, MonsterEntity.class);
        monsterEntity.setUserEntity(userService.findById(monsterDTO.getUserId()));
        monsterEntity.setFearActions(getListOfFearActionEntitiesFromIds(monsterDTO.getFearActionsIds()));
        monsterEntity.setRewards(getListOfRewardEntitiesFromIds(monsterDTO.getRewardsIds()));
        return monsterEntity;
    }

    public MonsterRatingDTO mapEntityToRatingDTO(MonsterEntity monsterEntity, int countBalloons) {
        return MonsterRatingDTO.builder()
                .monsterID(monsterEntity.getId())
                .countBalloons(countBalloons)
                .build();
    }

    private List<UUID> getIdsFromListOfRewardEntities(List<RewardEntity> list) {
        ArrayList<UUID> ids = new ArrayList<>();
        list.forEach(e -> ids.add(e.getId()));
        return ids;
    }

    private List<RewardEntity> getListOfRewardEntitiesFromIds(List<UUID> list) {
        ArrayList<RewardEntity> entities = new ArrayList<>();
        list.forEach(id -> entities.add(rewardService.findById(id)));
        return entities;
    }

    private List<UUID> getIdsFromListOfFearActionEntities(List<FearActionEntity> list) {
        ArrayList<UUID> ids = new ArrayList<>();
        list.forEach(e -> ids.add(e.getId()));
        return ids;
    }

    private List<FearActionEntity> getListOfFearActionEntitiesFromIds(List<UUID> list) {
        ArrayList<FearActionEntity> entities = new ArrayList<>();
        list.forEach(id -> entities.add(fearActionService.findById(id)));
        return entities;
    }


}
