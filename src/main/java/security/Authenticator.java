/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nicolaicornelis
 */
public class Authenticator {

    public static List<String> authenticate(String userName, String password) {
        return UserFacadeFactory.getInstance().authenticateUser(userName, password);
    }

    public static String createToken(String subject, List<String> roles) throws JOSEException {
        StringBuilder res = new StringBuilder();
        for (String string : roles) {
            res.append(string);
            res.append(",");
        }
        String rolesAsString = res.length() > 0 ? res.substring(0, res.length() - 1) : "";
        String issuer = "semester3demo-cphbusiness.dk-computerScience";
        // Generate random 256-bit (32-byte) shared secret
        SecureRandom random = new SecureRandom();
        Secret.SHARED_SECRET = new byte[32];
        random.nextBytes(Secret.SHARED_SECRET);

        JWSSigner signer = new MACSigner(Secret.SHARED_SECRET);
        Date date = new Date();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(subject)
                .claim("username", subject)
                .claim("roles", roles)
                .claim("issuer", issuer)
                .issueTime(date)
                .expirationTime(new Date(date.getTime() + 1000 * 60 * 60))
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
}
