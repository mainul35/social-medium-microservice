import { Component, OnInit } from '@angular/core';
import {UserInfoModel} from "../../models/user-info.model";
import {UserInfoService} from "../../services/user-info.service";
import {UserConnectionService} from "../../services/user-connection.service";
import {UserConnectionModel} from "../../models/user-connection.model";
import { CookieService } from 'ngx-cookie-service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-browser-users',
  templateUrl: './browse-users.component.html',
  styleUrls: ['./browse-users.component.scss']
})
export class BrowseUsersComponent implements OnInit {

  userConnections ?: UserConnectionModel[] = [];
  currentPageIdx = 1;
  constructor(private userInfoService: UserInfoService, private userConnectionService: UserConnectionService, private cookieService: CookieService) { }

  ngOnInit(): void {
    let userStr = this.cookieService.get(AuthenticationService.USER_INFO);
    // @ts-ignore
    let loggedInUser: UserInfoModel = JSON.parse(userStr)
    this.userInfoService.getGlobalUsers(loggedInUser?.username, this.currentPageIdx)
      .subscribe(userConnections => {
        this.userConnections = userConnections
      })
  }

  addFriend(idToConnect ?: string) {
    let userStr = this.cookieService.get(AuthenticationService.USER_INFO);
    // @ts-ignore
    let loggedInUser: UserInfoModel = JSON.parse(userStr)
    this.userConnectionService.connectWithUser(idToConnect, loggedInUser.username).subscribe(resp => {
      this.userConnections?.forEach((userConnection) => {
        if ((userConnection.user?.id === resp.body?.user?.id && userConnection.connection?.id === resp.body?.connection?.id)
        || (userConnection.user?.id === resp.body?.connection?.id && userConnection.connection?.id === resp.body?.user?.id)) {
          userConnection.requestedById = loggedInUser.id
        }
      })
    })
  }
}
