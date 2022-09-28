package ru.itmo.monsters.conroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.AuthRequestDTO;
import ru.itmo.monsters.dto.AuthResponseDTO;
import ru.itmo.monsters.security.jwt.JwtTokenProvider;
import ru.itmo.monsters.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseDTO login(@Valid @RequestBody AuthRequestDTO authRequestDto) {
        String login = authRequestDto.getLogin();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, authRequestDto.getPassword())
        );

        String token = jwtTokenProvider.createToken(login, userService.findByLogin(login).getRole().getName());
        return new AuthResponseDTO(token);
    }
}
