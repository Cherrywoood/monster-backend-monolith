package ru.itmo.monsters;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.itmo.monsters.dto.AuthRequestDTO;
import ru.itmo.monsters.dto.AuthResponseDTO;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthControllerIT extends AbstractIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private static final String URL = "/auth";

    @Test
    void userLoginWithRightCredentials() {
        AuthRequestDTO authRequest = AuthRequestDTO.builder()
                .login("polina")
                .password("12345")
                .build();
        ResponseEntity<AuthResponseDTO> response = restTemplate.postForEntity(URL, authRequest, AuthResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()).getToken());
    }

    @Test
    void userLoginWithWrongPassword() {
        AuthRequestDTO authRequest = AuthRequestDTO.builder()
                .login("polina")
                .password("123456")
                .build();
        ResponseEntity<Map> response =
                restTemplate.postForEntity(URL, authRequest, Map.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("invalid user credentials", Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    void userLoginWithWrongLogin() {
        AuthRequestDTO authRequest = AuthRequestDTO.builder()
                .login("polinaa")
                .password("12345")
                .build();
        ResponseEntity<Map> response =
                restTemplate.postForEntity(URL, authRequest, Map.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("invalid user credentials", Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    void userLoginInvalidCredentials() {
        AuthRequestDTO authRequest = AuthRequestDTO.builder()
                .login("pol")
                .password("")
                .build();
        ResponseEntity<Map> response = restTemplate.postForEntity(URL, authRequest, Map.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("invalid data", Objects.requireNonNull(response.getBody()).get("message"));
    }
}
