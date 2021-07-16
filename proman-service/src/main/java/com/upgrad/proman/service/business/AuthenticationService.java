package com.upgrad.proman.service.business;

import com.upgrad.proman.service.common.JwtTokenProvider;
import com.upgrad.proman.service.dao.UserDao;
import com.upgrad.proman.service.entity.UserAuthTokenEntity;
import com.upgrad.proman.service.entity.UserEntity;
import com.upgrad.proman.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class AuthenticationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthTokenEntity authenticate(final String username, final String password) throws AuthenticationFailedException {
        UserEntity userEntity = userDao.getUserByEmail(username);
        if (userEntity == null) {
            throw new AuthenticationFailedException("ATH-001", "USER WITH EMAIL NOT FOUND.");
        }
        final String encryptedPassword = cryptographyProvider.encrypt(password, userEntity.getSalt());

        if (encryptedPassword.equals(userEntity.getPassword())){
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            UserAuthTokenEntity userAuthToken = new UserAuthTokenEntity();
            userAuthToken.setUser(userEntity);
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            userAuthToken.setAccessToken(jwtTokenProvider.generateToken(userEntity.getUuid(), now, expiresAt));
            userAuthToken.setLoginAt(now);
            userAuthToken.setExpiresAt(expiresAt);
            userAuthToken.setCreatedBy("API-BACKEND.");
            userAuthToken.setCreatedAt(now);
            userDao.createAuthToken(userAuthToken);
            userEntity.setLastLoginAt(now);
            userDao.updateUserEntity(userEntity);
            return userAuthToken;
        } else {
            throw new AuthenticationFailedException("ATH-002", "WRONG PASSWORD");
        }
    }
}
