package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.controller.exception.NotFoundException;
import ru.itmo.monsters.dto.ElectricBalloonDTO;
import ru.itmo.monsters.mapper.ElectricBalloonMapper;
import ru.itmo.monsters.model.ElectricBalloonEntity;
import ru.itmo.monsters.repository.ElectricBalloonRepository;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ElectricBalloonService {

    private final ElectricBalloonRepository electricBalloonRepository;
    private final ElectricBalloonMapper electricBalloonMapper;

    private static final String EXC_MES_ID = "none electric balloon was found by id";

    public ElectricBalloonEntity save(ElectricBalloonDTO electricBalloonDTO) {
        return electricBalloonRepository.save(electricBalloonMapper.mapDtoToEntity(electricBalloonDTO));
    }

    public ElectricBalloonEntity findById(UUID electricBalloonId) {
        return electricBalloonRepository.findById(electricBalloonId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + electricBalloonId)
        );
    }

    public Page<ElectricBalloonEntity> findAllFilledByDate(Date date, int page, int size) {
        return electricBalloonRepository.findAllFilledByDate(date, PageRequest.of(page, size));
    }

    public Page<ElectricBalloonEntity> findAllFilledByDateAndCity(Date date, UUID citiId, Pageable pageable) {
        return electricBalloonRepository.findAllFilledByDateAndCity(date, citiId, pageable);
    }

    public ElectricBalloonEntity updateById(UUID electricBalloonId, ElectricBalloonDTO electricBalloonDTO) {
        if (electricBalloonRepository.findById(electricBalloonId).isEmpty()) {
            throw new NotFoundException(EXC_MES_ID + ": " + electricBalloonId);
        }
        electricBalloonDTO.setId(electricBalloonId);
        return electricBalloonRepository.save(electricBalloonMapper.mapDtoToEntity(electricBalloonDTO));
    }

    public void delete(UUID electricBalloonId) {
        if (electricBalloonRepository.findById(electricBalloonId).isEmpty()) {
            throw new NotFoundException(EXC_MES_ID + ": " + electricBalloonId);
        }
        electricBalloonRepository.deleteById(electricBalloonId);
    }
}
