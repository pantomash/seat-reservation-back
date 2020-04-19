package pl.pantomash.seatreservation.config.auth.jwt;

public class JwtProperties {
    static final long EXPIRATION_TIME = 864_000_000_000L;
    static final String TOKEN_PREFIX = "Bearer ";
    static final String TOKEN_HEADER = "Authorization";
    static final String JWT_SECRET =
            "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";
}
