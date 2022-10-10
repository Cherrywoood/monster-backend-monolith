package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.conroller.exception.NotFoundException;
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

    private final String EXC_MES_DATE = "none electric balloons were found by date";
    private final String EXC_MES_DATE_AND_CITY = "none electric balloons were found by date and city";
    private final String EXC_MES_ID = "none electric balloon was found by id";

    public ElectricBalloonEntity save(ElectricBalloonDTO electricBalloonDTO) {
        return electricBalloonRepository.save(electricBalloonMapper.mapDtoToEntity(electricBalloonDTO));
    }


    public Optional<ElectricBalloonEntity> findAllFilledByDate(Date date) {
        Optional<ElectricBalloonEntity> balloons = electricBalloonRepository.findAllFilledByDate(date);
        if (balloons.isEmpty()) {
            throw new NotFoundException(EXC_MES_DATE + ": " + date);
        }
        return balloons;
    }

    public Optional<ElectricBalloonEntity> findAllFilledByDateAndCity(Date date, UUID citiId) {
        Optional<ElectricBalloonEntity> balloons = electricBalloonRepository.findAllFilledByDateAndCity(date, citiId);
        if (balloons.isEmpty()) {
            throw new NotFoundException(EXC_MES_DATE_AND_CITY + ": " + date + ", " + citiId);
        }
        return balloons;
    }

    public void delete(UUID electricBalloonId) {
        electricBalloonRepository.findById(electricBalloonId).orElseThrow(
                () -> new NotFoundException(EXC_MES_ID + ": " + electricBalloonId)
        );
        electricBalloonRepository.deleteById(electricBalloonId);
    }
}
