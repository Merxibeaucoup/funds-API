package com.edgar.transferapi.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edgar.transferapi.config.JwtService;
import com.edgar.transferapi.models.token.Token;
import com.edgar.transferapi.models.token.TokenType;
import com.edgar.transferapi.models.user.Role;
import com.edgar.transferapi.models.user.User;
import com.edgar.transferapi.repositories.TokenRepository;
import com.edgar.transferapi.repositories.UserRepository;

@Service
public class AuthenticationService {
	
	@Autowired
	private UserRepository repository;
	
	
	@Autowired
	private TokenRepository tokenRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private  JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		
		var user = User.builder()
		        .firstname(request.getFirstname())
		        .lastname(request.getLastname())
		        .email(request.getEmail())
		        .password(passwordEncoder.encode(request.getPassword()))
		        .role(Role.USER)
		        .build();
		
		var savedUser = repository.save(user);
		
	    var jwtToken = jwtService.generateToken(user);
	   
	    saveUserToken(savedUser, jwtToken);
		
	    return AuthenticationResponse.builder()
	            .token(jwtToken)
	            .build();
	}
	
	
	

	

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		
		 authenticationManager.authenticate(
			        new UsernamePasswordAuthenticationToken(
			            request.getEmail(),
			            request.getPassword()
			        )
			    );
		 
			    var user = repository.findByEmail(request.getEmail())
			        .orElseThrow();
			    
			    var jwtToken = jwtService.generateToken(user);
			    revokeAllUserTokens(user);
			    saveUserToken(user, jwtToken);
			    return AuthenticationResponse.builder()
			        .token(jwtToken)
			        .build();
		
		
		
	}
	
	
	private void saveUserToken(User user, String jwtToken) {
		var token = Token.builder()
	    		.user(user)
	    		.token(jwtToken)
	    		.tokenType(TokenType.BEARER)
	    		.revoked(false)
	    		.expired(false)
	    		.build();
	    
	    tokenRepo.save(token);
	}
	
	private void revokeAllUserTokens(User user) {
		var validUserToken = tokenRepo.findAllValidTokensByUser(user.getId());
	
	if(validUserToken.isEmpty()) {
		return;
	}
	
	validUserToken.forEach(t -> {
	t.setExpired(true);
	t.setRevoked(true);
	
	});
	
	tokenRepo.saveAll(validUserToken);
	}

}
