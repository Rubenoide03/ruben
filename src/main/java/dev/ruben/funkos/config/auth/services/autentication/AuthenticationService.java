package dev.ruben.funkos.config.auth.services.autentication;

import dev.ruben.funkos.config.auth.dto.JwtAuthResponse;
import dev.ruben.funkos.config.auth.dto.UserSignInRequest;
import dev.ruben.funkos.config.auth.dto.UserSignUpRequest;

public interface AuthenticationService {
    JwtAuthResponse signUp(UserSignUpRequest request);

    JwtAuthResponse signIn(UserSignInRequest request);
}
