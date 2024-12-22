package hongik.diary.service;

import hongik.diary.dto.LoginDTO;
import hongik.diary.dto.UserDTO;
import hongik.diary.entity.User;
import hongik.diary.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // id/pw가 맞는지 판단
    public boolean login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        User byUsername = userRepository.findByUsername(username);
        if (byUsername != null) {
            return byUsername.getPassword().equals(password);
        }
        return false;
    }
}
