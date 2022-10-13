package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.PageDTO;
import ru.itmo.monsters.dto.monster.MonsterDTO;
import ru.itmo.monsters.dto.monster.MonsterRatingDTO;
import ru.itmo.monsters.enums.Job;
import ru.itmo.monsters.mapper.MonsterMapper;
import ru.itmo.monsters.mapper.PageMapper;
import ru.itmo.monsters.model.MonsterEntity;
import ru.itmo.monsters.service.MonsterService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("monsters")
public class MonsterController {

    private final MonsterService monsterService;
    private final MonsterMapper monsterMapper;
    private final PageMapper<MonsterDTO> pageMapper;

    @GetMapping("{monsterId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARER') or hasAuthority('SCARE ASSISTANT') or hasAuthority('RECRUITER') or hasAuthority('DISINFECTOR')")
    public MonsterDTO getMonster(@PathVariable UUID monsterId) {
        return monsterMapper.mapEntityToDto(monsterService.findById(monsterId));
    }

    @GetMapping("rating")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARER') or hasAuthority('SCARE ASSISTANT') or hasAuthority('RECRUITER')")
    public List<MonsterRatingDTO> getRating(int page, int size) {
        List<MonsterRatingDTO> ratingDTO = new ArrayList<>();
        Map<MonsterEntity, Integer> rating = monsterService.getRating(page, size);
        for (MonsterEntity monster : rating.keySet()) {
            ratingDTO.add(monsterMapper.mapEntityToRatingDTO(monster, rating.get(monster)));
        }
        return ratingDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('RECRUITER')")
    public MonsterDTO addMonster(@Valid @RequestBody MonsterDTO monsterDTO) {
        return monsterMapper.mapEntityToDto(monsterService.save(monsterDTO));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARER') or hasAuthority('SCARE ASSISTANT') or hasAuthority('RECRUITER') or hasAuthority('DISINFECTOR')")
    public ResponseEntity<PageDTO<MonsterDTO>> findAll(@RequestParam(defaultValue = "0")
                                                       @Min(value = 0, message = "must not be less than zero") int page,
                                                       @RequestParam(defaultValue = "5")
                                                       @Max(value = 50, message = "must not be more than 50 characters") int size) {

        Page<MonsterEntity> pages = monsterService.findAll(page, size);
        if (pages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pageMapper.mapToDto(pages.map(monsterMapper::mapEntityToDto)), HttpStatus.OK);
    }

    @GetMapping("job")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARER') or hasAuthority('SCARE ASSISTANT') or hasAuthority('RECRUITER') or hasAuthority('DISINFECTOR')")
    public ResponseEntity<PageDTO<MonsterDTO>> findAllByJob(@RequestParam Job job,
                                                            @RequestParam(defaultValue = "0")
                                                            @Min(value = 0, message = "must not be less than zero") int page,
                                                            @RequestParam(defaultValue = "5")
                                                            @Max(value = 50, message = "must not be more than 50 characters") int size) {

        Page<MonsterEntity> pages = monsterService.findAllByJob(job, page, size);
        if (pages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pageMapper.mapToDto(pages.map(monsterMapper::mapEntityToDto)), HttpStatus.OK);
    }

    @GetMapping("fear-action/{date}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARER') or hasAuthority('SCARE ASSISTANT') or hasAuthority('RECRUITER') or hasAuthority('DISINFECTOR')")
    public ResponseEntity<PageDTO<MonsterDTO>> findAllByFearActionDate(@PathVariable @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy") Date date, @RequestParam(defaultValue = "0")
    @Min(value = 0, message = "must not be less than zero") int page,
                                                                       @RequestParam(defaultValue = "5")
                                                                       @Max(value = 50, message = "must not be more than 50 characters") int size) {

        Page<MonsterEntity> pages = monsterService.findAllByDateOfFearAction(date, page, size);
        if (pages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pageMapper.mapToDto(pages.map(monsterMapper::mapEntityToDto)), HttpStatus.OK);
    }

    @GetMapping("infection/{date}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARER') or hasAuthority('SCARE ASSISTANT') or hasAuthority('RECRUITER') or hasAuthority('DISINFECTOR')")
    public ResponseEntity<PageDTO<MonsterDTO>> findAllByInfectionDate(@PathVariable @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy") Date date, @RequestParam(defaultValue = "0")
    @Min(value = 0, message = "must not be less than zero") int page,
                                                                      @RequestParam(defaultValue = "5")
                                                                      @Max(value = 50, message = "must not be more than 50 characters") int size) {

        Page<MonsterEntity> pages = monsterService.findAllByInfectionDate(date, page, size);
        if (pages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pageMapper.mapToDto(pages.map(monsterMapper::mapEntityToDto)), HttpStatus.OK);
    }

    @PatchMapping("{monsterId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('RECRUITER')")
    public MonsterDTO updateJobById(@PathVariable UUID monsterId, @RequestParam Job job) {
        return monsterMapper.mapEntityToDto(monsterService.updateJobById(job, monsterId));
    }

    @DeleteMapping("{monsterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteMonster(@PathVariable UUID monsterId) {
        monsterService.delete(monsterId);
    }

    @PutMapping("{monsterId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public MonsterDTO putMonster(@PathVariable UUID monsterId, @Valid @RequestBody MonsterDTO monsterDTO) {
        return monsterMapper.mapEntityToDto(monsterService.updateById(monsterId, monsterDTO));
    }
}
