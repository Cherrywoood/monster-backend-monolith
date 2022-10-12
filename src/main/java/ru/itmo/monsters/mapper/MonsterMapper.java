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

import java.util.stream.Collectors;

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
        monsterDTO.setFearActionsIds(monsterEntity.getFearActions().stream().map(FearActionEntity::getId).collect(Collectors.toList()));
        monsterDTO.setRewardsIds(monsterEntity.getRewards().stream().map(RewardEntity::getId).collect(Collectors.toList()));
        return monsterDTO;
    }

    public MonsterEntity mapDtoToEntity(MonsterDTO monsterDTO) {
        MonsterEntity monsterEntity = modelMapper.map(monsterDTO, MonsterEntity.class);
        monsterEntity.setUserEntity(userService.findById(monsterDTO.getUserId()));
        monsterEntity.setFearActions(monsterDTO.getFearActionsIds().stream().map(fearActionService::findById).collect(Collectors.toList()));
        monsterEntity.setRewards(monsterDTO.getRewardsIds().stream().map(rewardService::findById).collect(Collectors.toList()));
        return monsterEntity;
    }

    public MonsterRatingDTO mapEntityToRatingDTO(MonsterEntity monsterEntity, int countBalloons) {
        return MonsterRatingDTO.builder()
                .monsterID(monsterEntity.getId())
                .countBalloons(countBalloons)
                .build();
    }

}
