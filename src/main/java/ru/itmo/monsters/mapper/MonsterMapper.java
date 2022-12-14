package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.monster.MonsterDTO;
import ru.itmo.monsters.dto.monster.MonsterRatingDTO;
import ru.itmo.monsters.model.FearActionEntity;
import ru.itmo.monsters.model.MonsterEntity;
import ru.itmo.monsters.model.RewardEntity;
import ru.itmo.monsters.service.FearActionService;
import ru.itmo.monsters.service.RewardService;
import ru.itmo.monsters.service.UserService;

@RequiredArgsConstructor
@Component
public class MonsterMapper {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final RewardService rewardService;
    private final FearActionService fearActionService;

    @Autowired
    public MonsterMapper(UserService userService, ModelMapper modelMapper, @Lazy RewardService rewardService, FearActionService fearActionService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.rewardService = rewardService;
        this.fearActionService = fearActionService;
    }

    public MonsterDTO mapEntityToDto(MonsterEntity monsterEntity) {
        MonsterDTO monsterDTO = modelMapper.map(monsterEntity, MonsterDTO.class);
        monsterDTO.setUserId(monsterEntity.getUserEntity().getId());
        monsterDTO.setFearActionsIds(monsterEntity.getFearActions().stream().map(FearActionEntity::getId).toList());
        monsterDTO.setRewardsIds(monsterEntity.getRewards().stream().map(RewardEntity::getId).toList());
        return monsterDTO;
    }

    public MonsterEntity mapDtoToEntity(MonsterDTO monsterDTO) {
        MonsterEntity monsterEntity = modelMapper.map(monsterDTO, MonsterEntity.class);
        monsterEntity.setUserEntity(userService.findById(monsterDTO.getUserId()));
        monsterEntity.setFearActions(monsterDTO.getFearActionsIds().stream().map(fearActionService::findById).toList());
        monsterEntity.setRewards(monsterDTO.getRewardsIds().stream().map(rewardService::findById).toList());
        return monsterEntity;
    }

    public MonsterRatingDTO mapEntityToRatingDTO(MonsterEntity monsterEntity, int countBalloons) {
        return MonsterRatingDTO.builder()
                .monsterID(monsterEntity.getId())
                .countBalloons(countBalloons)
                .build();
    }

}
