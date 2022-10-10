package ru.itmo.monsters.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.ElectricBalloonDTO;
import ru.itmo.monsters.dto.FearActionDTO;
import ru.itmo.monsters.dto.monster.MonsterDTO;
import ru.itmo.monsters.mapper.FearActionMapper;
import ru.itmo.monsters.service.FearActionService;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("fear-actions")
public class FearActionController {

    private final FearActionService fearActionService;
    private final FearActionMapper fearActionMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FearActionDTO addFearAction(@Valid @RequestBody FearActionDTO fearActionDTO) {
        return fearActionMapper.mapEntityToDto(fearActionService.save(fearActionDTO));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFearAction(@PathVariable UUID id) {
        fearActionService.delete(id);
    }

    @PutMapping("{id}")
    public FearActionDTO putFearAction(@PathVariable UUID id, @Valid @RequestBody FearActionDTO fearAction) {
        fearAction.setId(id);
        return fearActionMapper.mapEntityToDto(fearActionService.save(fearAction));
    }

}

