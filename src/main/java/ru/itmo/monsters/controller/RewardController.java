package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.RewardDTO;
import ru.itmo.monsters.mapper.RewardMapper;
import ru.itmo.monsters.service.RewardService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("rewards")
public class RewardController {

    private final RewardService rewardService;
    private final RewardMapper rewardMapper;

    @GetMapping("{rewardId}")
    @ResponseStatus(HttpStatus.OK)
    public RewardDTO getReward(@PathVariable UUID rewardId) {
        return rewardMapper.mapEntityToDto(rewardService.findById(rewardId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RewardDTO addReward(@Valid @RequestBody RewardDTO rewardDTO) {
        return rewardMapper.mapEntityToDto(rewardService.save(rewardDTO));
    }

    @DeleteMapping("{rewardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReward(@PathVariable UUID rewardId) {
        rewardService.delete(rewardId);
    }

    @PutMapping("{rewardId}")
    @ResponseStatus(HttpStatus.OK)
    public RewardDTO putReward(@PathVariable UUID rewardId, @Valid @RequestBody RewardDTO rewardDTO) {
        return rewardMapper.mapEntityToDto(rewardService.updateById(rewardId, rewardDTO));
    }

}
