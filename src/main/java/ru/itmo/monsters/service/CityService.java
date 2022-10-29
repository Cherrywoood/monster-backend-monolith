package ru.itmo.monsters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.controller.exception.NotFoundException;
import ru.itmo.monsters.dto.CityDTO;
import ru.itmo.monsters.mapper.CityMapper;
import ru.itmo.monsters.model.CityEntity;
import ru.itmo.monsters.repository.CityRepository;

import javax.persistence.EntityExistsException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    private static final String EXC_MES_ID = "none city was found by id";
    private static final String EXC_MES_NAME = "none city was found by name";
    private static final String EXC_EXIST = "city with this name already exists";


    public CityEntity save(CityDTO cityDTO) {
        if (cityRepository.findByName(cityDTO.getName()).isPresent()) {
            throw new EntityExistsException(EXC_EXIST + ": " + cityDTO.getName());
        }
        return cityRepository.save(cityMapper.mapDtoToEntity(cityDTO));
    }

    public CityEntity updateById(UUID cityId, CityDTO cityDTO) {
        if (cityRepository.findById(cityId).isEmpty()) {
            throw new NotFoundException(EXC_MES_ID + ": " + cityId);
        }
        cityDTO.setId(cityId);
        return cityRepository.save(cityMapper.mapDtoToEntity(cityDTO));
    }

    public Page<CityEntity> findAll(int page, int size) {
        return cityRepository.findAll(PageRequest.of(page, size));
    }

    public CityEntity findByName(String cityName) {
        return cityRepository.findByName(cityName).orElseThrow(
                () -> new NotFoundException(EXC_MES_NAME + ": " + cityName)
        );
    }

    public void delete(UUID cityId) {
        if (cityRepository.findById(cityId).isEmpty()) {
            throw new NotFoundException(EXC_MES_ID + ": " + cityId);
        }
        cityRepository.deleteById(cityId);
    }

}
