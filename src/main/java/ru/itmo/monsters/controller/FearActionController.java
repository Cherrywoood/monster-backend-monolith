package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARER') or hasAuthority('SCARE ASSISTANT') or hasAuthority('RECRUITER')")
    public FearActionDTO getFearAction(@PathVariable UUID fearActionId) {
        return fearActionMapper.mapEntityToDto(fearActionService.findById(fearActionId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARER') or hasAuthority('SCARE ASSISTANT')")
    public FearActionDTO addFearAction(@Valid @RequestBody FearActionDTO fearActionDTO) {
        return fearActionMapper.mapEntityToDto(fearActionService.save(fearActionDTO));
    }

    @DeleteMapping("{fearActionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteFearAction(@PathVariable UUID fearActionId) {
        fearActionService.delete(fearActionId);
    }

    @PutMapping("{fearActionId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public FearActionDTO putFearAction(@PathVariable UUID fearActionId, @Valid @RequestBody FearActionDTO fearAction) {
        return fearActionMapper.mapEntityToDto(fearActionService.updateById(fearActionId, fearAction));
    }

}

