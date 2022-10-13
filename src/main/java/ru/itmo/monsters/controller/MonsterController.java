package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.monster.MonsterDTO;
import ru.itmo.monsters.dto.monster.MonsterRatingDTO;
import ru.itmo.monsters.enums.Job;
import ru.itmo.monsters.mapper.MonsterMapper;
import ru.itmo.monsters.model.MonsterEntity;
import ru.itmo.monsters.service.MonsterService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("monsters")
public class MonsterController {

    private final MonsterService monsterService;
    private final MonsterMapper monsterMapper;

    @GetMapping("{monsterId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('SCARER') and hasAuthority('SCARE ASSISTANT') and hasAuthority('RECRUITER') and hasAuthority('DISINFECTOR')")
    public MonsterDTO getMonster(@PathVariable UUID monsterId) {
        return monsterMapper.mapEntityToDto(monsterService.findById(monsterId));
    }

    @GetMapping("rating")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('SCARER') and hasAuthority('SCARE ASSISTANT') and hasAuthority('RECRUITER')")
    public List<MonsterRatingDTO> getRating() {
        List<MonsterRatingDTO> ratingDTO = new ArrayList<>();
        Map<MonsterEntity, Integer> rating = monsterService.getRating();
        for (MonsterEntity monster : rating.keySet()) {
            ratingDTO.add(monsterMapper.mapEntityToRatingDTO(monster, rating.get(monster)));
        }
        return ratingDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('RECRUITER')")
    public MonsterDTO addMonster(@Valid @RequestBody MonsterDTO monsterDTO) {
        return monsterMapper.mapEntityToDto(monsterService.save(monsterDTO));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('SCARER') and hasAuthority('SCARE ASSISTANT') and hasAuthority('RECRUITER') and hasAuthority('DISINFECTOR')")
    // TODO: 10.10.2022 pagination
    public ResponseEntity<List<MonsterDTO>> findAll() {
        List<MonsterDTO> monsterDTOS = new ArrayList<>();
        monsterService.findAll().forEach(m -> monsterDTOS.add(monsterMapper.mapEntityToDto(m)));
        if (monsterDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(monsterDTOS, HttpStatus.OK);
    }

    @GetMapping("job")
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('SCARER') and hasAuthority('SCARE ASSISTANT') and hasAuthority('RECRUITER') and hasAuthority('DISINFECTOR')")
    // TODO: 10.10.2022 pagination
    public ResponseEntity<List<MonsterDTO>> findAllByJob(@RequestParam Job job) {
        List<MonsterDTO> monsterDTOS = new ArrayList<>();
        monsterService.findAllByJob(job).forEach(m -> monsterDTOS.add(monsterMapper.mapEntityToDto(m)));
        if (monsterDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(monsterDTOS, HttpStatus.OK);
    }

    @GetMapping("fear-action/{date}")
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('SCARER') and hasAuthority('SCARE ASSISTANT') and hasAuthority('RECRUITER') and hasAuthority('DISINFECTOR')")
    public ResponseEntity<List<MonsterDTO>> findAllByFearActionDate(@PathVariable @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy") Date date) {
        List<MonsterDTO> monsterDTOS = new ArrayList<>();
        monsterService.findAllByDateOfFearAction(date).ifPresent(m -> monsterDTOS.add(monsterMapper.mapEntityToDto(m)));
        if (monsterDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(monsterDTOS, HttpStatus.OK);
    }

    @GetMapping("infection/{date}")
    @PreAuthorize("hasAuthority('ADMIN') and hasAuthority('SCARER') and hasAuthority('SCARE ASSISTANT') and hasAuthority('RECRUITER') and hasAuthority('DISINFECTOR')")
    public ResponseEntity<List<MonsterDTO>> findAllByInfectionDate(@PathVariable @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy") Date date) {
        List<MonsterDTO> monsterDTOS = new ArrayList<>();
        monsterService.findAllByInfectionDate(date).ifPresent(m -> monsterDTOS.add(monsterMapper.mapEntityToDto(m)));
        if (monsterDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(monsterDTOS, HttpStatus.OK);
    }

    @PatchMapping("{monsterId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')  and hasAuthority('RECRUITER')")
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
