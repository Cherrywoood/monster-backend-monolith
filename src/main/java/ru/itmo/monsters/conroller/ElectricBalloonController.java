package ru.itmo.monsters.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

@Controller
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


    @GetMapping("date/{date}")
    @ResponseStatus(HttpStatus.OK)
    public List<ElectricBalloonDTO> findAllFilledByDate(@PathVariable @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy") Date date) {
        List<ElectricBalloonDTO> electricBalloonDTOS = new ArrayList<>();
        electricBalloonService.findAllFilledByDate(date).ifPresent(e -> electricBalloonDTOS.add(electricBalloonMapper.mapEntityToDto(e)));
        return electricBalloonDTOS;
    }

    @GetMapping("city/{cityId}/date/{date}")
    @ResponseStatus(HttpStatus.OK)
    public List<ElectricBalloonDTO> findAllFilledByDateAndCity(@PathVariable @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy") Date date, @PathVariable UUID cityId) {
        List<ElectricBalloonDTO> electricBalloonDTOS = new ArrayList<>();
        electricBalloonService.findAllFilledByDateAndCity(date, cityId).ifPresent(e -> electricBalloonDTOS.add(electricBalloonMapper.mapEntityToDto(e)));
        return electricBalloonDTOS;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteElectricBalloon(@PathVariable UUID id) {
        electricBalloonService.delete(id);
    }

    @PutMapping("{id}")
    public ElectricBalloonDTO putElectricBalloon(@PathVariable UUID id, @Valid @RequestBody ElectricBalloonDTO electricBalloonDTO) {
        electricBalloonDTO.setId(id);
        return electricBalloonMapper.mapEntityToDto(electricBalloonService.save(electricBalloonDTO));
    }


}
