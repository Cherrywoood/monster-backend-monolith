package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.monsters.service.DoorService;

@Controller
@RequiredArgsConstructor
@RequestMapping("doors")
public class DoorController {
    private final DoorService doorService;


}
