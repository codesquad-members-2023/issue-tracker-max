package codesquad.issueTracker.user.service;

import codesquad.issueTracker.user.domain.User;
import codesquad.issueTracker.user.dto.SignUpRequestDto;
import codesquad.issueTracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long save(SignUpRequestDto userSignUpRequestDto) {
        String encodedPassword = BCrypt.hashpw(userSignUpRequestDto.getPassword(), BCrypt.gensalt());
        User user = SignUpRequestDto.toEntity(userSignUpRequestDto, encodedPassword);
        return userRepository.insert(user);
    }

}
