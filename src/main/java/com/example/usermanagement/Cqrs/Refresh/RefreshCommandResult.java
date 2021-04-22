package com.example.usermanagement.Cqrs.Refresh;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshCommandResult {
    private String accessToken;
    private String refreshToken;
}
