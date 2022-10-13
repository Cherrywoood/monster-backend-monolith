package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.FearActionDTO;
import ru.itmo.monsters.mapper.FearActionMapper;
import ru.itmo.monsters.service.FearActionService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("fear-actions")
public class FearActionController {

    private final FearActionService fearActionService;
    private final FearActionMapper fearActionMapper;

    @GetMapping("{fearActionId}")
    @ResponseStatus(HttpStatus.OK)
    public FearActionDTO getFearAction(@PathVariable UUID fearActionId) {
        return fearActionMapper.mapEntityToDto(fearActionService.findById(fearActionId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FearActionDTO addFearAction(@Valid @RequestBody FearActionDTO fearActionDTO) {
        return fearActionMapper.mapEntityToDto(fearActionService.save(fearActionDTO));
    }

    @DeleteMapping("{fearActionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFearAction(@PathVariable UUID fearActionId) {
        fearActionService.delete(fearActionId);
    }

    @PutMapping("{fearActionId}")
    @ResponseStatus(HttpStatus.OK)
    public FearActionDTO putFearAction(@PathVariable UUID fearActionId, @Valid @RequestBody FearActionDTO fearAction) {
        return fearActionMapper.mapEntityToDto(fearActionService.updateById(fearActionId, fearAction));
    }

}

