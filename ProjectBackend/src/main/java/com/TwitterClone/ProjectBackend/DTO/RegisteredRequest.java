package com.TwitterClone.ProjectBackend.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

@ToString
public class RegisteredRequest {
    private final String email;
    private String username;
    private String password;
}
