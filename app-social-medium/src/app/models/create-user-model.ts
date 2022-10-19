import { AuthInfoModel } from "./auth-info.model";
import { UserInfoModel } from "./user-info.model";

export class CreateUserRequest {
    userInfo ?: UserInfoModel;
    authInfo ?: AuthInfoModel;
}