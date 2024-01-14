package sbankauthms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sbankauthms.dao.entity.User;
import sbankauthms.dao.repository.UserRepository;
import sbankauthms.exception.InvalidUsernameOrPasswordException;
import sbankauthms.jwt.JwtService;
import sbankauthms.model.request.LoginRequest;
import sbankauthms.model.response.TokenResponse;
import sbankauthms.service.AuthService;
import sbankauthms.util.encryptor.DataEncryptor;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        return userRepository.findFirstByCif(loginRequest.getCif())
                .map(user -> getTokenIfPasswordIsValidOrElseThrow(user, loginRequest))
                .orElseThrow(InvalidUsernameOrPasswordException::new);
    }

    private TokenResponse getTokenIfPasswordIsValidOrElseThrow(User user, LoginRequest loginRequest) {
        if (!checkUserPassword(user.getPassword(), loginRequest.getPassword())) {
            throw new InvalidUsernameOrPasswordException();
        }
        String token = TOKEN_PREFIX.concat(jwtService.issueToken(user.getId(), user.getCif()));
        return TokenResponse.builder()
                .cif(user.getCif())
                .token(token)
                .build();
    }

    private boolean checkUserPassword(String password, String requestedPassword) {
        return password.equals(DataEncryptor.sha256Hex(DataEncryptor.md5Hex(requestedPassword)));
    }

}
