package com.yunbaek.barogodelivery.auth.infrastructure;

public interface TokenProvider<T, U> {
	T createToken(U command);
	boolean validateToken(T token);
}
