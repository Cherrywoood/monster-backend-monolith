package ru.itmo.monsters.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmo.monsters.model.UserEntity;
import ru.itmo.monsters.repository.UserRepository;

import static java.lang.String.format;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUserName");
        UserEntity userEntity = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        format("user %s not found", username)
                ));
        return User
                .withUsername(userEntity.getLogin())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRole().getName())
                .build();
    }
}
