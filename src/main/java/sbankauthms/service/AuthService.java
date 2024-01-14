package sbankauthms.service;

import sbankauthms.model.request.LoginRequest;
import sbankauthms.model.response.TokenResponse;

public interface AuthService {

    TokenResponse login(LoginRequest loginRequest);

}
