package controllers.dtos.response;

import controllers.dtos.enums.ConnectionStatus;
import lombok.Data;

@Data
public class UserConnectionInfoResponse {
    private UserInfoResponse user = new UserInfoResponse();
    private UserInfoResponse connection = new UserInfoResponse();
    private ConnectionStatus status = ConnectionStatus.NEW;
    private String requestedById = "";
    private String blockedById = "";
}
