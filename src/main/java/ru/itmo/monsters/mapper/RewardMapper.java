package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.RewardDTO;
import ru.itmo.monsters.model.RewardEntity;

@RequiredArgsConstructor
@Component
public class RewardMapper {

    private final ModelMapper modelMapper;

    public RewardDTO mapEntityToDto(RewardEntity rewardEntity) {
        RewardDTO rewardDTO = modelMapper.map(rewardEntity, RewardDTO.class);
        return rewardDTO;
    }

    public RewardEntity mapDtoToEntity(RewardDTO rewardDTO) {
        RewardEntity rewardEntity = modelMapper.map(rewardDTO, RewardEntity.class);
        return rewardEntity;
    }

}
