package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.conroller.exception.NotFoundException;
import ru.itmo.monsters.dto.FearActionDTO;
import ru.itmo.monsters.mapper.FearActionMapper;
import ru.itmo.monsters.model.FearActionEntity;
import ru.itmo.monsters.repository.FearActionRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FearActionService {

    private final FearActionRepository fearActionRepository;
    private final FearActionMapper fearActionMapper;

    private final String EXC_MES_ID = "none fear action was found by id";

    public FearActionEntity save(FearActionDTO fearActionDTO) {
        return fearActionRepository.save(fearActionMapper.mapDtoToEntity(fearActionDTO));
    }

    public void delete(UUID fearActionId) {
        fearActionRepository.findById(fearActionId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + fearActionId)
        );
        fearActionRepository.deleteById(fearActionId);
    }

}
