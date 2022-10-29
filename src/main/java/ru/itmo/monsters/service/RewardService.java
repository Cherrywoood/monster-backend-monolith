package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.controller.exception.NotFoundException;
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
    private static final String EXC_MES_ID = "none reward was found by id";

    private final RewardMapper rewardMapper;
    private final RewardRepository rewardRepository;

    public RewardEntity save(RewardDTO rewardDTO) {
        if (rewardRepository.findByMoney(rewardDTO.getMoney()).isPresent()) {
            throw new EntityExistsException(EXC_EXIST + ": " + rewardDTO.getMoney());
        }
        return rewardRepository.save(rewardMapper.mapDtoToEntity(rewardDTO));
    }

    public RewardEntity findById(UUID rewardId) {
        return rewardRepository.findById(rewardId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + rewardId)
        );
    }

    public RewardEntity updateById(UUID rewardId, RewardDTO rewardDTO) {
        if (rewardRepository.findById(rewardId).isEmpty()) {
            throw new NotFoundException(EXC_MES_ID + ": " + rewardId);
        }
        rewardDTO.setId(rewardId);
        return rewardRepository.save(rewardMapper.mapDtoToEntity(rewardDTO));
    }

    public void delete(UUID rewardId) {
        if (rewardRepository.findById(rewardId).isEmpty()) {
            throw new NotFoundException(EXC_MES_ID + ": " + rewardId);
        }
        rewardRepository.deleteById(rewardId);
    }
}
