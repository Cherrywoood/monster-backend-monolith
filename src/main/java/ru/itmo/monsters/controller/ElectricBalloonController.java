package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.ElectricBalloonDTO;
import ru.itmo.monsters.dto.PageDTO;
import ru.itmo.monsters.mapper.ElectricBalloonMapper;
import ru.itmo.monsters.mapper.PageMapper;
import ru.itmo.monsters.model.ElectricBalloonEntity;
import ru.itmo.monsters.service.ElectricBalloonService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/electric-balloons")
public class ElectricBalloonController {

    private final ElectricBalloonService electricBalloonService;
    private final ElectricBalloonMapper electricBalloonMapper;
    private final PageMapper<ElectricBalloonDTO> pageMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARER') or hasAuthority('SCARE ASSISTANT')")
    public ElectricBalloonDTO addElectricBalloon(@Valid @RequestBody ElectricBalloonDTO electricBalloonDTO) {
        return electricBalloonMapper.mapEntityToDto(electricBalloonService.save(electricBalloonDTO));
    }

    @GetMapping("/{electricBalloonId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARER') or hasAuthority('SCARE ASSISTANT') or hasAuthority('RECRUITER')")
    public ElectricBalloonDTO getElectricBalloon(@PathVariable UUID electricBalloonId) {
        return electricBalloonMapper.mapEntityToDto(electricBalloonService.findById(electricBalloonId));
    }

    @GetMapping("/{date}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARER') or hasAuthority('SCARE ASSISTANT') or hasAuthority('RECRUITER')")
    public ResponseEntity<PageDTO<ElectricBalloonDTO>> findAllFilledByDateAndCity(@PathVariable @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy") Date date, @RequestParam(required = false) UUID cityId,
                                                                                  @RequestParam(defaultValue = "0")
                                                                                  @Min(value = 0, message = "must not be less than zero") int page,
                                                                                  @RequestParam(defaultValue = "5")
                                                                                  @Max(value = 50, message = "must not be more than 50 characters") int size) {
        Page<ElectricBalloonEntity> pages;
        if (cityId != null) {
            pages = electricBalloonService.findAllFilledByDateAndCity(date, cityId, PageRequest.of(page, size));
        } else {
            pages = electricBalloonService.findAllFilledByDate(date, page, size);
        }

        if (pages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pageMapper.mapToDto(pages.map(electricBalloonMapper::mapEntityToDto)), HttpStatus.OK);
    }

    @DeleteMapping("/{electricBalloonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARER') or hasAuthority('SCARE ASSISTANT')")
    public void deleteElectricBalloon(@PathVariable UUID electricBalloonId) {
        electricBalloonService.delete(electricBalloonId);
    }

    @PutMapping("/{electricBalloonId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARER') or hasAuthority('SCARE ASSISTANT')")
    public ElectricBalloonDTO putElectricBalloon(@PathVariable UUID electricBalloonId, @Valid @RequestBody ElectricBalloonDTO electricBalloonDTO) {
        return electricBalloonMapper.mapEntityToDto(electricBalloonService.updateById(electricBalloonId, electricBalloonDTO));
    }


}
