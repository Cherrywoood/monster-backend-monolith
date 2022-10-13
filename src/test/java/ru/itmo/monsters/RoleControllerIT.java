package ru.itmo.monsters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ru.itmo.monsters.dto.PageDTO;
import ru.itmo.monsters.dto.RoleDTO;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleControllerIT extends AbstractIntegrationTest {
    private static final String URL = "/roles";
    private static final String NO_EXIST_ROLE = "TESTER";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AuthInfo authInfo;

    @Test
    void saveRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        String role = NO_EXIST_ROLE;

        RoleDTO roleRequest = RoleDTO.builder()
                .name(role)
                .build();

        ResponseEntity<RoleDTO> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                new HttpEntity<>(roleRequest, headersWithToken),
                RoleDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(role, Objects.requireNonNull(response.getBody()).getName());

        rollBackSave(headersWithToken);
    }

    @Test
    void saveRoleWithoutAuth() {
        RoleDTO roleRequest = RoleDTO.builder()
                .name("TESTER")
                .build();

        ResponseEntity<Object> response = restTemplate.postForEntity(URL, roleRequest, Object.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void saveRoleInvalidData() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        RoleDTO roleRequest = RoleDTO.builder()
                .name("")
                .build();

        ResponseEntity<Map> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                new HttpEntity<>(roleRequest, headersWithToken),
                Map.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("invalid data", Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    void saveAlreadyExistsRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        RoleDTO roleRequest = RoleDTO.builder()
                .name("ADMIN")
                .build();

        ResponseEntity<Map> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                new HttpEntity<>(roleRequest, headersWithToken),
                Map.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("role with this name exists", Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    void saveRoleWithNoAdminRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthNoAdminLogin(), authInfo.getAuthNoAdminPassword(), restTemplate, "/auth");

        RoleDTO roleRequest = RoleDTO.builder()
                .name("TESTER")
                .build();

        ResponseEntity<Object> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                new HttpEntity<>(roleRequest, headersWithToken),
                Object.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void findRoleByName() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        String role = "ADMIN";

        ResponseEntity<RoleDTO> response = restTemplate.exchange(
                URL + "/" + role,
                HttpMethod.GET,
                new HttpEntity<>(headersWithToken),
                RoleDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(role, Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    void findRoleByNameWithoutAuth() {
        String role = "ADMIN";

        ResponseEntity<Object> response = restTemplate.getForEntity(URL + "/" + role, Object.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


    @Test
    void findNoExistsRoleByName() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        ResponseEntity<Map> response = restTemplate.exchange(
                URL + "/" + NO_EXIST_ROLE,
                HttpMethod.GET,
                new HttpEntity<>(headersWithToken),
                Map.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("role not found by name " + NO_EXIST_ROLE, Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    void findRoleByNameNoAdminRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthNoAdminLogin(), authInfo.getAuthNoAdminPassword(), restTemplate, "/auth");

        ResponseEntity<Object> response = restTemplate.exchange(
                URL + "/ADMIN",
                HttpMethod.GET,
                new HttpEntity<>(headersWithToken),
                Object.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void deleteRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        String role = "SCARER";
        ResponseEntity<Object> response = restTemplate.exchange(
                URL + "/" + role,
                HttpMethod.DELETE,
                new HttpEntity<>(headersWithToken),
                Object.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        rollBackDelete(role, headersWithToken);
    }

    @Test
    void deleteRoleWithoutAuth() {
        ResponseEntity<Object> response = restTemplate.exchange(
                URL + "/SCARER",
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                Object.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void deleteNoExistsRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        ResponseEntity<Map> response = restTemplate.exchange(
                URL + "/" + NO_EXIST_ROLE,
                HttpMethod.DELETE,
                new HttpEntity<>(headersWithToken),
                Map.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("role not found by name " + NO_EXIST_ROLE, Objects.requireNonNull(response.getBody()).get("message"));
    }

    @Test
    void deleteRoleNoAdminRole() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthNoAdminLogin(), authInfo.getAuthNoAdminPassword(), restTemplate, "/auth");

        ResponseEntity<Object> response = restTemplate.exchange(
                URL + "/SCARER",
                HttpMethod.DELETE,
                new HttpEntity<>(headersWithToken),
                Object.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void findAllPagination() {
        HttpHeaders headersWithToken = authInfo
                .addJwtTokenToHeader(authInfo.getAuthAdminLogin(), authInfo.getAuthAdminPassword(), restTemplate, "/auth");

        int size = 5;
        int page = 0;

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
                URL + "/" + NO_EXIST_ROLE,
                HttpMethod.DELETE,
                new HttpEntity<>(headersWithToken),
                Object.class);
    }

    void rollBackDelete(String role, HttpHeaders headersWithToken) {
        RoleDTO roleRequest = RoleDTO.builder()
                .name(role)
                .build();

        restTemplate.exchange(
                URL,
                HttpMethod.POST,
                new HttpEntity<>(roleRequest, headersWithToken),
                Object.class);
    }
}
