import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FilterModel} from "../../models/filter.model";
import {UserInfoService} from "../../services/user-info.service";
import {UserInfoModel} from "../../models/user-info.model";

@Component({
  selector: 'app-user-search',
  templateUrl: './user-search.component.html',
  styleUrls: ['./user-search.component.scss']
})
export class UserSearchComponent implements OnInit {

  field ?: string = "Please select a type";
  value ?: string
  userFound: boolean = false;
  user ?: UserInfoModel;

  constructor(private userInfoService: UserInfoService) { }

  ngOnInit(): void {
  }

  selectInputType(value: string) {
    this.field = value
    console.log(this.field)
  }

  searchUser() {

    const filter = new FilterModel();
    filter.field = this.field
    filter.value = this.value
    this.userInfoService.searchUser(filter).subscribe(value1 => {
      let user = value1?.body?.[0];
      if (user) {
        this.userFound = true;
        localStorage.setItem("user", JSON.stringify(user))
        this.user = user;
        console.log(user)
      }
    });
  }
}
