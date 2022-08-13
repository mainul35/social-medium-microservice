package controllers.dtos.request;

import lombok.Data;

@Data
public class UserInfoRequest {
    private String id;
    private String firstName;
    private String surname;
    private String email;
    private String username;
    private String profileImagePath;
}
