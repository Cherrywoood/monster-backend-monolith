package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public MonsterDTO getMonster(@PathVariable UUID monsterId) {
        return monsterMapper.mapEntityToDto(monsterService.findById(monsterId));
    }

    @GetMapping("rating")
    @ResponseStatus(HttpStatus.OK)
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
    public MonsterDTO addMonster(@Valid @RequestBody MonsterDTO monsterDTO) {
        return monsterMapper.mapEntityToDto(monsterService.save(monsterDTO));
    }

    @GetMapping
    // TODO: 10.10.2022 pagination
    public ResponseEntity<List<MonsterDTO>> findAllByJob(@RequestParam Job job) {
        List<MonsterDTO> monsterDTOS = new ArrayList<>();
        monsterService.findAllByJob(job).ifPresent(m -> monsterDTOS.add(monsterMapper.mapEntityToDto(m)));
        if (monsterDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(monsterDTOS, HttpStatus.OK);
    }

    @GetMapping("fear-action/{date}")
    public ResponseEntity<List<MonsterDTO>> findAllByFearActionDate(@PathVariable @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy") Date date) {
        List<MonsterDTO> monsterDTOS = new ArrayList<>();
        monsterService.findAllByDateOfFearAction(date).ifPresent(m -> monsterDTOS.add(monsterMapper.mapEntityToDto(m)));
        if (monsterDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(monsterDTOS, HttpStatus.OK);
    }

    @GetMapping("infection/{date}")
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
    public MonsterDTO updateJobById(@PathVariable UUID monsterId, @RequestBody Job job) {
        return monsterMapper.mapEntityToDto(monsterService.updateJobById(job, monsterId));
    }

    @DeleteMapping("{monsterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMonster(@PathVariable UUID monsterId) {
        monsterService.delete(monsterId);
    }

    @PutMapping("{monsterId}")
    @ResponseStatus(HttpStatus.OK)
    public MonsterDTO putMonster(@PathVariable UUID monsterId, @Valid @RequestBody MonsterDTO monsterDTO) {
        return monsterMapper.mapEntityToDto(monsterService.updateById(monsterId, monsterDTO));
    }
}
