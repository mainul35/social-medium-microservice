import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UserInfoModel} from "../../models/user-info.model";
import {UserInfoService} from "../../services/user-info.service";
import {UserConnectionService} from "../../services/user-connection.service";
import {UserConnectionModel} from "../../models/user-connection.model";
import { CookieService } from 'ngx-cookie-service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.scss']
})
export class RequestsComponent implements OnInit {

  requests ?: UserConnectionModel[] = [];
  currentPageIdx = 1;
  @ViewChild('removable') private removableElement ?: ElementRef;
  constructor(private userInfoService: UserInfoService, private userConnectionService: UserConnectionService, private cookieService: CookieService) { }

  ngOnInit(): void {

    let userStr = this.cookieService.get(AuthenticationService.USER_INFO);
    // @ts-ignore
    let loggedInUser: UserInfoModel = JSON.parse(userStr)
    this.userInfoService.getConnectionRequests(loggedInUser?.username, this.currentPageIdx)
      .subscribe(value => {
        this.requests = value;
      })

  }

  acceptConnectionRequest(idToAccept?: string) {
    let userStr = this.cookieService.get(AuthenticationService.USER_INFO);
    // @ts-ignore
    let loggedInUser: UserInfoModel = JSON.parse(userStr)
    console.log(this.removableElement?.nativeElement)
    this.userConnectionService.acceptConnection(idToAccept, loggedInUser?.username).subscribe(resp => {
      this.requests?.forEach(request => {
        if (resp.body?.connection?.username === request?.connection?.username && resp.body?.status === "ACCEPTED") {
          this.removableElement?.nativeElement.getElementsByClassName(`removable-${request?.connection?.id}`)[0].parentElement.remove()
        }
      })
    })
  }

  blockConnectionRequest(idToBlock ?: string) {
    let userStr = this.cookieService.get(AuthenticationService.USER_INFO);
    // @ts-ignore
    let loggedInUser: UserInfoModel = JSON.parse(userStr)
    console.log(this.removableElement?.nativeElement)
    this.userConnectionService.blockConnection(idToBlock, loggedInUser?.username).subscribe(resp => {
      this.requests?.forEach(request => {
        if (resp.body?.connection?.id === request?.connection?.id && resp.body?.status === "BLOCKED") {
          this.removableElement?.nativeElement.getElementsByClassName(`removable-${request?.connection?.id}`)[0].parentElement.remove()
        }
      })
    })
  }

  ignoreConnectionRequest(idToIgnore?: string) {
    let userStr = this.cookieService.get(AuthenticationService.USER_INFO);
    // @ts-ignore
    let loggedInUser: UserInfoModel = JSON.parse(userStr)
    console.log(this.removableElement?.nativeElement)
    this.userConnectionService.rejectConnection(idToIgnore, loggedInUser?.username).subscribe(resp => {
      this.requests?.forEach(request => {
        if (resp.body?.connection?.username === request?.connection?.username && resp.body?.status === "REJECTED") {
          this.removableElement?.nativeElement.getElementsByClassName(`removable-${request?.connection?.id}`)[0].parentElement.remove()
        }
      })
    })
  }
}
