package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.ElectricBalloonDTO;
import ru.itmo.monsters.mapper.ElectricBalloonMapper;
import ru.itmo.monsters.service.ElectricBalloonService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("electric-balloons")
public class ElectricBalloonController {

    private final ElectricBalloonService electricBalloonService;
    private final ElectricBalloonMapper electricBalloonMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ElectricBalloonDTO addElectricBalloon(@Valid @RequestBody ElectricBalloonDTO electricBalloonDTO) {
        return electricBalloonMapper.mapEntityToDto(electricBalloonService.save(electricBalloonDTO));
    }

    @GetMapping("{electricBalloonId}")
    @ResponseStatus(HttpStatus.OK)
    public ElectricBalloonDTO getElectricBalloon(@PathVariable UUID electricBalloonId) {
        return electricBalloonMapper.mapEntityToDto(electricBalloonService.findById(electricBalloonId));
    }

    @GetMapping("{date}")
    public ResponseEntity<List<ElectricBalloonDTO>> findAllFilledByDateAndCity(@PathVariable @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy") Date date, @RequestParam(required = false) UUID cityId) {
        List<ElectricBalloonDTO> electricBalloonDTOS = new ArrayList<>();
        if (cityId != null) {
            electricBalloonService.findAllFilledByDateAndCity(date, cityId).ifPresent(e -> electricBalloonDTOS.add(electricBalloonMapper.mapEntityToDto(e)));
        } else {
            electricBalloonService.findAllFilledByDate(date).ifPresent(e -> electricBalloonDTOS.add(electricBalloonMapper.mapEntityToDto(e)));
        }
        if (electricBalloonDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(electricBalloonDTOS, HttpStatus.OK);
    }

    @DeleteMapping("{electricBalloonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteElectricBalloon(@PathVariable UUID electricBalloonId) {
        electricBalloonService.delete(electricBalloonId);
    }

    @PutMapping("{electricBalloonId}")
    @ResponseStatus(HttpStatus.OK)
    public ElectricBalloonDTO putElectricBalloon(@PathVariable UUID electricBalloonId, @Valid @RequestBody ElectricBalloonDTO electricBalloonDTO) {
        return electricBalloonMapper.mapEntityToDto(electricBalloonService.updateById(electricBalloonId, electricBalloonDTO));
    }


}
