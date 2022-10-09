package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.monster.MonsterDTO;
import ru.itmo.monsters.dto.monster.MonsterRatingDTO;
import ru.itmo.monsters.model.MonsterEntity;

@RequiredArgsConstructor
@Component
public class MonsterMapper {

    private final ModelMapper modelMapper;

    public MonsterDTO mapEntityToDto(MonsterEntity monsterEntity) {
        MonsterDTO monsterDTO = modelMapper.map(monsterEntity, MonsterDTO.class);
        return monsterDTO;
    }

    public MonsterEntity mapDtoToEntity(MonsterDTO monsterDTO) {
        MonsterEntity monsterEntity = modelMapper.map(monsterDTO, MonsterEntity.class);
        return monsterEntity;
    }

    public MonsterRatingDTO mapEntityToRatingDTO(MonsterEntity monsterEntity, int countBalloons) {
        return MonsterRatingDTO.builder()
                .monsterID(monsterEntity.getId())
                .countBalloons(countBalloons)
                .build();
    }

}
