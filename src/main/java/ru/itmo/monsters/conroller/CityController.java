package ru.itmo.monsters.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.CityDTO;
import ru.itmo.monsters.dto.ElectricBalloonDTO;
import ru.itmo.monsters.dto.monster.MonsterDTO;
import ru.itmo.monsters.mapper.CityMapper;
import ru.itmo.monsters.service.CityService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("cities")
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityDTO addCity(@Valid @RequestBody CityDTO cityDTO) {
        return cityMapper.mapEntityToDto(cityService.save(cityDTO));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // TODO: 10.10.2022 pagination
    public List<CityDTO> findAll() {
        List<CityDTO> cityDTOS = new ArrayList<>();
        cityService.findAll().forEach(c -> cityDTOS.add(cityMapper.mapEntityToDto(c)));
        return cityDTOS;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable UUID id) {
        cityService.delete(id);
    }

    @PutMapping("{id}")
    public CityDTO putCity(@PathVariable UUID id, @Valid @RequestBody CityDTO cityDTO) {
        cityDTO.setId(id);
        return cityMapper.mapEntityToDto(cityService.save(cityDTO));
    }
}

