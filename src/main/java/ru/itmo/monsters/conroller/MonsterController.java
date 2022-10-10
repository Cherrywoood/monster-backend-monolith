package ru.itmo.monsters.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("monsters")
public class MonsterController {

    private final MonsterService monsterService;
    private final MonsterMapper monsterMapper;

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

    @GetMapping("job/{job}")
    @ResponseStatus(HttpStatus.OK)
    // TODO: 10.10.2022 pagination
    public List<MonsterDTO> findAllByJob(@PathVariable String job) {
        List<MonsterDTO> monsterDTOS = new ArrayList<>();
        monsterService.findAllByJob(Job.valueOf(job)).ifPresent(m -> monsterDTOS.add(monsterMapper.mapEntityToDto(m)));
        return monsterDTOS;
    }

    @GetMapping("fear-action/{date}")
    @ResponseStatus(HttpStatus.OK)
    public List<MonsterDTO> findAllByFearActionDate(@PathVariable @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy") Date date) {
        List<MonsterDTO> monsterDTOS = new ArrayList<>();
        monsterService.findAllByDateOfFearAction(date).ifPresent(m -> monsterDTOS.add(monsterMapper.mapEntityToDto(m)));
        return monsterDTOS;
    }

    @GetMapping("infection/{date}")
    @ResponseStatus(HttpStatus.OK)
    public List<MonsterDTO> findAllByInfectionDate(@PathVariable @DateTimeFormat(fallbackPatterns = "dd-MM-yyyy") Date date) {
        List<MonsterDTO> monsterDTOS = new ArrayList<>();
        monsterService.findAllByInfectionDate(date).ifPresent(m -> monsterDTOS.add(monsterMapper.mapEntityToDto(m)));
        return monsterDTOS;
    }

    @PatchMapping("{monsterId}/job")
    @ResponseStatus(HttpStatus.OK)
    public MonsterDTO updateJobById(@PathVariable UUID monsterId, @RequestBody Job job) {
        return monsterMapper.mapEntityToDto(monsterService.updateJobById(job, monsterId));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMonster(@PathVariable UUID id) {
        monsterService.delete(id);
    }

    @PutMapping("{id}")
    public MonsterDTO putMonster(@PathVariable UUID id, @Valid @RequestBody MonsterDTO monsterDTO) {
        monsterDTO.setId(id);
        return monsterMapper.mapEntityToDto(monsterService.save(monsterDTO));
    }
}
