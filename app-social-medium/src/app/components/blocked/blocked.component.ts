import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UserInfoModel} from "../../models/user-info.model";
import {UserInfoService} from "../../services/user-info.service";
import {UserConnectionModel} from "../../models/user-connection.model";
import {UserConnectionService} from "../../services/user-connection.service";
import { CookieService } from 'ngx-cookie-service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-blocked',
  templateUrl: './blocked.component.html',
  styleUrls: ['./blocked.component.scss']
})
export class BlockedComponent implements OnInit {

  blockedUserConnections ?: UserConnectionModel[];
  currentPageIdx = 1;
  @ViewChild('removable') private removableElement ?: ElementRef;

  constructor(private userInfoService: UserInfoService, private userConnectionService: UserConnectionService, private cookieService: CookieService) { }


  ngOnInit(): void {
    let userStr = this.cookieService.get(AuthenticationService.USER_INFO);
    // @ts-ignore
    let loggedInUser: UserInfoModel = JSON.parse(userStr)
    this.userInfoService.getBlockedUsers(loggedInUser?.username, this.currentPageIdx)
      .subscribe(value => {
        this.blockedUserConnections = value;
      })
  }

  unblock(id ?: string) {
    let userStr = this.cookieService.get(AuthenticationService.USER_INFO);
    // @ts-ignore
    let loggedInUser: UserInfoModel = JSON.parse(userStr)
    console.log(this.removableElement?.nativeElement)
    this.userConnectionService.unblockConnection(id, loggedInUser?.username).subscribe(resp => {
      this.blockedUserConnections?.forEach(blockedUserConnection => {
        if (resp.body?.connection?.username === blockedUserConnection?.connection?.username && resp.body?.status === "UNBLOCKED") {
          this.removableElement?.nativeElement.getElementsByClassName(`removable-${blockedUserConnection?.connection?.id}`)[0].parentElement.remove()
        }
      })
    })
  }
}
