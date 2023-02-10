package com.yunbaek.barogodelivery.auth.application;

import com.yunbaek.barogodelivery.auth.dto.TokenRequest;
import com.yunbaek.barogodelivery.auth.dto.TokenResponse;
import com.yunbaek.barogodelivery.auth.infrastructure.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;

	public AuthService(
			JwtTokenProvider jwtTokenProvider,
			AuthenticationManager authenticationManager
	) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationManager = authenticationManager;
	}

	public TokenResponse login(TokenRequest request) {
		UsernamePasswordAuthenticationToken authentication1 = new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPassword());
		Authentication authentication = authenticationManager.authenticate(
				authentication1);

		String token = jwtTokenProvider.createToken(authentication);

		return new TokenResponse(token);
	}
}
