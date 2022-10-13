package ru.itmo.monsters;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.AuthRequestDTO;
import ru.itmo.monsters.dto.AuthResponseDTO;

@Component

public class AuthInfo {

    public HttpHeaders addJwtTokenToHeader(String username, String password, TestRestTemplate restTemplate, String url) {
        AuthResponseDTO authResponseDTO = restTemplate.postForObject(
                url,
                AuthRequestDTO.builder()
                        .login(username)
                        .password(password)
                        .build(),
                AuthResponseDTO.class);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authResponseDTO.getToken());
        return headers;
    }

    public String getAuthAdminLogin() {
        return "polina";
    }

    public String getAuthAdminPassword() {
        return "12345";
    }

    public String getAuthNoAdminLogin() {
        return "katya";
    }

    public String getAuthNoAdminPassword() {
        return "12345";
    }
}
