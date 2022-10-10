package ru.itmo.monsters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ru.itmo.monsters.dto.PageDTO;
import ru.itmo.monsters.dto.UserRequestDTO;
import ru.itmo.monsters.dto.UserResponseDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerIT extends AbstractIntegrationTest {
    private static final String URL = "/users";
    private static final String NO_EXIST_USER_LOGIN = "floppa";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthInfo authInfo;

    @Test
    void saveUser() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        String login = NO_EXIST_USER_LOGIN;
        String role = "ADMIN";

        UserRequestDTO userRequest = UserRequestDTO.builder()
                .login(login)
                .password("12345")
                .role(role)
                .build();

        ResponseEntity<UserResponseDTO> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                new HttpEntity<>(userRequest, headersWithToken),
                UserResponseDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(login, Objects.requireNonNull(response.getBody()).getLogin());
        assertEquals(role, Objects.requireNonNull(response.getBody()).getRole());

        rollBackSave(headersWithToken);
    }

    @Test
    void saveUserWithoutAuth() {
        UserRequestDTO userRequest = UserRequestDTO.builder()
                .login("lollipop")
                .password("12345")
                .role("ADMIN")
                .build();

        ResponseEntity<Object> response = restTemplate.postForEntity(URL, userRequest, Object.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void saveUserInvalidData() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        UserRequestDTO userRequest = UserRequestDTO.builder()
                .login("kat")
                .password("12")
                .role("A")
                .build();

        ResponseEntity<Map> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                new HttpEntity<>(userRequest, headersWithToken),
                Map.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("invalid data", Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    void saveAlreadyExistsUser() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        UserRequestDTO userRequest = UserRequestDTO.builder()
                .login("polina")
                .password("12345")
                .role("ADMIN")
                .build();

        ResponseEntity<Map> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                new HttpEntity<>(userRequest, headersWithToken),
                Map.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("user with this login exists", Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    void saveUserWithNoAdminRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthNoAdminLogin(), authInfo.getAuthNoAdminPassword(), restTemplate, "/auth");

        UserRequestDTO userRequest = UserRequestDTO.builder()
                .login("Jack Dogo")
                .password("bestdog")
                .role("SCARER")
                .build();

        ResponseEntity<Object> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                new HttpEntity<>(userRequest, headersWithToken),
                Object.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void findUserByLogin() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        String login = "polina";

        ResponseEntity<UserResponseDTO> response = restTemplate.exchange(
                URL + "/" + login,
                HttpMethod.GET,
                new HttpEntity<>(headersWithToken),
                UserResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(login, Objects.requireNonNull(response.getBody()).getLogin());
    }

    @Test
    void findUserByLoginWithoutAuth() {
        String login = "polina";

        ResponseEntity<Object> response = restTemplate.getForEntity(URL + "/" + login, Object.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


    @Test
    void findNoExistsUserByLogin() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        ResponseEntity<Map> response = restTemplate.exchange(
                URL + "/" + NO_EXIST_USER_LOGIN,
                HttpMethod.GET,
                new HttpEntity<>(headersWithToken),
                Map.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("user not found by login " + NO_EXIST_USER_LOGIN, Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    void findUserByLoginNoAdminRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthNoAdminLogin(), authInfo.getAuthNoAdminPassword(), restTemplate, "/auth");

        String login = "polina";

        ResponseEntity<Object> response = restTemplate.exchange(
                URL + "/" + login,
                HttpMethod.GET,
                new HttpEntity<>(headersWithToken),
                Object.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void deleteUser() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        String login = "katya";

        ResponseEntity<Object> response = restTemplate.exchange(
                URL + "/" + login,
                HttpMethod.DELETE,
                new HttpEntity<>(headersWithToken),
                Object.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        rollBackDelete(login, headersWithToken);
    }

    @Test
    void deleteUserWithoutAuth() {
        ResponseEntity<Object> response = restTemplate.exchange(
                URL + "/katya",
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                Object.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void deleteNoExistsUser() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        ResponseEntity<Map> response = restTemplate.exchange(
                URL + "/" + NO_EXIST_USER_LOGIN,
                HttpMethod.DELETE,
                new HttpEntity<>(headersWithToken),
                Map.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("user not found by login " + NO_EXIST_USER_LOGIN, Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    void deleteUserNoAdminRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthNoAdminLogin(), authInfo.getAuthNoAdminPassword(), restTemplate, "/auth");

        ResponseEntity<Object> response = restTemplate.exchange(
                URL + "/katya",
                HttpMethod.DELETE,
                new HttpEntity<>(headersWithToken),
                Object.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void updateUserByRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        String login = "katya";
        String role = "ADMIN";
        Map<String, String> request = new HashMap<>();
        request.put("role", role);

        ResponseEntity<UserResponseDTO> response = restTemplate.exchange(
                URL + "/" + login,
                HttpMethod.PATCH,
                new HttpEntity<>(request, headersWithToken),
                UserResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(login, Objects.requireNonNull(response.getBody()).getLogin());
        assertEquals(role, Objects.requireNonNull(response.getBody()).getRole());

        rollBackUpdate(login, role, headersWithToken);

    }

    @Test
    void updateUserByRoleWithoutAuth() {
        String login = "katya";
        String role = "ADMIN";
        Map<String, String> request = new HashMap<>();
        request.put("role", role);

        ResponseEntity<Object> response = restTemplate.exchange(
                URL + "/" + login,
                HttpMethod.PATCH,
                new HttpEntity<>(request),
                Object.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void updateNoExistsUserByRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        String role = "ADMIN";
        Map<String, String> request = new HashMap<>();
        request.put("role", role);

        ResponseEntity<Map> response = restTemplate.exchange(
                URL + "/" + NO_EXIST_USER_LOGIN,
                HttpMethod.PATCH,
                new HttpEntity<>(request, headersWithToken),
                Map.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("user not found by login " + NO_EXIST_USER_LOGIN, Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    void updateUserByRoleWithNoAdminRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthNoAdminLogin(), authInfo.getAuthNoAdminPassword(), restTemplate, "/auth");

        String login = "katya";
        String role = "ADMIN";
        Map<String, String> request = new HashMap<>();
        request.put("role", role);

        ResponseEntity<Object> response = restTemplate.exchange(
                URL + "/" + login,
                HttpMethod.PATCH,
                new HttpEntity<>(request, headersWithToken),
                Object.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void findAllPagination() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        int size = 4;
        int page = 1;

        String requestParamsPage = String.format("?page=%d&size=%d", page, size);

        ResponseEntity<PageDTO> response = restTemplate.exchange(
                URL + requestParamsPage,
                HttpMethod.GET,
                new HttpEntity<>(headersWithToken),
                PageDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PageDTO userPage = response.getBody();

        assertEquals(page, userPage.getCurrentPage());
        assertEquals(size, userPage.getSize());
        assertEquals(userPage.getContent().size(), userPage.getNumberOfElements());
    }

    @Test
    void findAllDefaultPagination() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        int defaultSize = 5;
        int defaultPage = 0;

        ResponseEntity<PageDTO> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                new HttpEntity<>(headersWithToken),
                PageDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        PageDTO userPage = response.getBody();

        assertEquals(defaultPage, userPage.getCurrentPage());
        assertEquals(defaultSize, userPage.getSize());
    }

    @Test
    void findAllByRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        String role = "ADMIN";

        ResponseEntity<PageDTO> response = restTemplate.exchange(
                URL + String.format("?role=%s", role),
                HttpMethod.GET,
                new HttpEntity<>(headersWithToken),
                PageDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Map> users = response.getBody().getContent();
        users.forEach(user ->
                assertEquals(role, user.get("role"))
        );
    }

    @Test
    void findAllNoContent() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        String requestParamsPage = String.format("?page=%d&size=%d", 3, 10);

        ResponseEntity<Object> response = restTemplate.exchange(
                URL + requestParamsPage,
                HttpMethod.GET,
                new HttpEntity<>(headersWithToken),
                Object.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void findAllWithoutAuth() {
        ResponseEntity<Object> response = restTemplate.getForEntity(URL, Object.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void findAllInvalidSizeAndPage() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        int size = 51;
        int page = -1;

        String requestParamsPage = String.format("?page=%d&size=%d", page, size);

        ResponseEntity<Map> response = restTemplate.exchange(
                URL + requestParamsPage,
                HttpMethod.GET,
                new HttpEntity<>(headersWithToken),
                Map.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("invalid data", Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    void findAllWithNoAdminRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthNoAdminLogin(), authInfo.getAuthNoAdminPassword(), restTemplate, "/auth");

        ResponseEntity<Object> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                new HttpEntity<>(headersWithToken),
                Object.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    void rollBackSave(HttpHeaders headersWithToken) {
        restTemplate.exchange(
                URL + "/" + NO_EXIST_USER_LOGIN,
                HttpMethod.DELETE,
                new HttpEntity<>(headersWithToken),
                Object.class);
    }

    void rollBackDelete(String login, HttpHeaders headersWithToken) {
        UserRequestDTO userRequest = UserRequestDTO.builder()
                .login(login)
                .password("12345")
                .role("SCARER")
                .build();

        restTemplate.exchange(
                URL,
                HttpMethod.POST,
                new HttpEntity<>(userRequest, headersWithToken),
                Object.class);
    }

    void rollBackUpdate(String login, String role, HttpHeaders headersWithToken) {
        Map<String, String> request = new HashMap<>();
        request.put("role", "SCARER");

        restTemplate.exchange(
                URL + "/" + login,
                HttpMethod.PATCH,
                new HttpEntity<>(request, headersWithToken),
                Object.class);
    }

}
