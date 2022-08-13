package controllers.dtos.response;

import lombok.Data;

@Data
public class UserInfoResponse {
    private String id;
    private String firstName;
    private String surname;
    private String email;
    private String username;
    private String profileImagePath;
}
