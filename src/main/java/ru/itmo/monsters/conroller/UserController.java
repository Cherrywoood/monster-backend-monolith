package ru.itmo.monsters.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.UserDTO;
import ru.itmo.monsters.mapper.UserMapper;
import ru.itmo.monsters.model.UserEntity;
import ru.itmo.monsters.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;

    //TODO: http ответы не забыть

    @PostMapping
    public UserDTO save(@RequestBody UserDTO userDTO) {
        return mapper.mapEntityToDto(userService.save(userDTO));
    }

    @GetMapping("/{login}")
    public UserDTO findByLogin(@PathVariable String login) {
        return mapper.mapEntityToDto(userService.findByLogin(login));
    }

    @GetMapping //TODO:пагинация, количество возвращенных записей
    public List<UserDTO> findAll(@RequestParam(required = false) String role) {
        List<UserEntity> users;
        if (role == null) {
            users = userService.findAll();
        } else {
            users = userService.findAllByRole(role);
        }
        return users
                .stream()
                .map((mapper::mapEntityToDto))
                .collect(Collectors.toList());
    }

    @PatchMapping("/{login}")
    public UserDTO updateByRole(@RequestBody Map<String, String> roleUpdate, @PathVariable String login) {
        return mapper.mapEntityToDto(userService.updateRoleByLogin(roleUpdate, login));
    }

    @DeleteMapping("/{login}")
    public void delete(@PathVariable String login) {
        userService.deleteByLogin(login);
    }


}
