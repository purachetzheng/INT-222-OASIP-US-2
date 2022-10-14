package sit.int221.oasipserver.token;

public class AuthenticationResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String jwt) {
        this.token = jwt;
    }

    public AuthenticationResponse(String jwt) {
        this.token = jwt;
    }
}
