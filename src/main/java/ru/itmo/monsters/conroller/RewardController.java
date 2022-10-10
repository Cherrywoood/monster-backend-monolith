package ru.itmo.monsters.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.CityDTO;
import ru.itmo.monsters.dto.FearActionDTO;
import ru.itmo.monsters.dto.RewardDTO;
import ru.itmo.monsters.mapper.RewardMapper;
import ru.itmo.monsters.service.RewardService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("rewards")
public class RewardController {

    private final RewardService rewardService;
    private final RewardMapper rewardMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RewardDTO addReward(@Valid @RequestBody RewardDTO rewardDTO){
        return rewardMapper.mapEntityToDto(rewardService.save(rewardDTO));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReward(@PathVariable UUID id) {
        rewardService.delete(id);
    }

    @PutMapping("{id}")
    public RewardDTO putReward(@PathVariable UUID id, @Valid @RequestBody RewardDTO reward) {
        reward.setId(id);
        return rewardMapper.mapEntityToDto(rewardService.save(reward));
    }

}
