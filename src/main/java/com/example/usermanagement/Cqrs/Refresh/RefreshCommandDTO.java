package com.example.usermanagement.Cqrs.Refresh;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshCommandDTO {
    private String accessToken;
    private String refreshToken;
}
