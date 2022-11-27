//package sit.int221.oasipserver.token;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.security.KeyFactory;
//import java.security.NoSuchAlgorithmException;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//
//@Configuration
//public class KeyGeneratorConfig {
//
//
//
//    private String privateKey = "TestSecretPrivate"; //Encoded private key string
//
//
//
//    private String publicKey = "TestSecretPublic";//Encoded public key string
//
//
//    @Bean
//    public PrivateKey generatePrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
//
//        KeyFactory kf = KeyFactory.getInstance("RSA");
//        PKCS8EncodedKeySpec privKeySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
//        return kf.generatePrivate(privKeySpecPKCS8);
//    }
//
//    @Bean
//    public PublicKey generatePublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
//
//        KeyFactory kf = KeyFactory.getInstance("RSA");
//        X509EncodedKeySpec pubKeySpecX509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
//        return kf.generatePublic(pubKeySpecX509EncodedKeySpec);
//    }
//}
