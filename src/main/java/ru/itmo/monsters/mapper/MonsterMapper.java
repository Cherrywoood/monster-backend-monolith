package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.monster.MonsterDTO;
import ru.itmo.monsters.dto.monster.MonsterRatingDTO;
import ru.itmo.monsters.model.MonsterEntity;
import ru.itmo.monsters.repository.UserRepository;

@RequiredArgsConstructor
@Component
public class MonsterMapper {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public MonsterDTO mapEntityToDto(MonsterEntity monsterEntity) {
        MonsterDTO monsterDTO = modelMapper.map(monsterEntity, MonsterDTO.class);
        monsterDTO.setLogin(monsterEntity.getUserEntity().getLogin());
        return monsterDTO;
    }

    public MonsterEntity mapDtoToEntity(MonsterDTO monsterDTO) {
        MonsterEntity monsterEntity = modelMapper.map(monsterDTO, MonsterEntity.class);
        monsterEntity.setUserEntity(userRepository.findByLogin(monsterDTO.getLogin()).get());
        return monsterEntity;
    }

    public MonsterRatingDTO mapEntityToRatingDTO(MonsterEntity monsterEntity, int countBalloons) {
        return MonsterRatingDTO.builder()
                .monsterID(monsterEntity.getId())
                .countBalloons(countBalloons)
                .build();
    }

}
