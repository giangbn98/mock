package com.itsol.mockup.web.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anhvd_itsol
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String errorCode;
    private String id;
    private String username;
    private String password;
    private String token;
    private String role;

}
