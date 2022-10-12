package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.controller.exception.NotFoundException;
import ru.itmo.monsters.dto.ElectricBalloonDTO;
import ru.itmo.monsters.mapper.ElectricBalloonMapper;
import ru.itmo.monsters.model.ElectricBalloonEntity;
import ru.itmo.monsters.repository.ElectricBalloonRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ElectricBalloonService {

    private final ElectricBalloonRepository electricBalloonRepository;
    private final ElectricBalloonMapper electricBalloonMapper;

    private final String EXC_MES_ID = "none electric balloon was found by id";

    public ElectricBalloonEntity save(ElectricBalloonDTO electricBalloonDTO) {
        return electricBalloonRepository.save(electricBalloonMapper.mapDtoToEntity(electricBalloonDTO));
    }

    public ElectricBalloonEntity findById(UUID electricBalloonId) {
        return electricBalloonRepository.findById(electricBalloonId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + electricBalloonId)
        );
    }

    public Optional<ElectricBalloonEntity> findAllFilledByDate(Date date) {
        return electricBalloonRepository.findAllFilledByDate(date);
    }

    public Optional<ElectricBalloonEntity> findAllFilledByDateAndCity(Date date, UUID citiId) {
        return electricBalloonRepository.findAllFilledByDateAndCity(date, citiId);
    }

    public ElectricBalloonEntity updateById(UUID electricBalloonId, ElectricBalloonDTO electricBalloonDTO) {
        electricBalloonRepository.findById(electricBalloonId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + electricBalloonId)
        );
        electricBalloonDTO.setId(electricBalloonId);
        return electricBalloonRepository.save(electricBalloonMapper.mapDtoToEntity(electricBalloonDTO));
    }

    public void delete(UUID electricBalloonId) {
        electricBalloonRepository.findById(electricBalloonId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + electricBalloonId)
        );
        electricBalloonRepository.deleteById(electricBalloonId);
    }
}
