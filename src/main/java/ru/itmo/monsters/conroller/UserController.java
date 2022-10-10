package ru.itmo.monsters.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.PageDTO;
import ru.itmo.monsters.dto.UserRequestDTO;
import ru.itmo.monsters.dto.UserResponseDTO;
import ru.itmo.monsters.mapper.PageMapper;
import ru.itmo.monsters.mapper.UserMapper;
import ru.itmo.monsters.model.UserEntity;
import ru.itmo.monsters.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Map;

@RequiredArgsConstructor
@Validated
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PageMapper<UserResponseDTO> pageMapper;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO save(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userMapper.mapEntityToDto(userService.save(userRequestDTO));
    }

    @GetMapping("/{login}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO findByLogin(@PathVariable String login) {
        return userMapper.mapEntityToDto(userService.findByLogin(login));
    }

    @GetMapping
    public ResponseEntity<PageDTO<UserResponseDTO>> findAll(@RequestParam(defaultValue = "0")
                                                            @Min(value = 0, message = "must not be less than zero") int page,
                                                            @RequestParam(defaultValue = "5")
                                                            @Max(value = 50, message = "must not be more than 50 characters") int size,
                                                            @RequestParam(required = false) String role) {
        Page<UserEntity> pageUsers = userService.findAll(page, size, role);
        if (pageUsers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(pageMapper.mapToDto(pageUsers.map(userMapper::mapEntityToDto)), HttpStatus.OK);
        }

    }

    @PatchMapping("/{login}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO updateByRole(@RequestBody Map<String, String> roleUpdate, @PathVariable String login) {
        return userMapper.mapEntityToDto(userService.updateRoleByLogin(roleUpdate, login));
    }

    @DeleteMapping("/{login}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String login) {
        userService.deleteByLogin(login);
    }


}
