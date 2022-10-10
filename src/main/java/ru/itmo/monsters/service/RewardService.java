package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.conroller.exception.NotFoundException;
import ru.itmo.monsters.dto.RewardDTO;
import ru.itmo.monsters.mapper.RewardMapper;
import ru.itmo.monsters.model.RewardEntity;
import ru.itmo.monsters.repository.RewardRepository;

import javax.persistence.EntityExistsException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RewardService {

    private static final String EXC_EXIST = "reward with such amount of money already exists";
    private final String EXC_MES_ID = "none reward was found by id";

    private final RewardMapper mapper;
    private final RewardRepository rewardRepository;

    public RewardEntity save(RewardDTO rewardDTO) {
        if (rewardRepository.findByMoney(rewardDTO.getMoney()).isPresent()) {
            throw new EntityExistsException(EXC_EXIST + ": " + rewardDTO.getMoney());
        }
        RewardEntity rewardEntity = mapper.mapDtoToEntity(rewardDTO);
        return rewardRepository.save(rewardEntity);
    }

    public void delete(UUID rewardId) {
        rewardRepository.findById(rewardId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + rewardId)
        );
        rewardRepository.deleteById(rewardId);
    }
}
