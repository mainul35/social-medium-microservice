import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserInfoModel} from "../../models/user-info.model";
import {UserInfoService} from "../../services/user-info.service";
import {Router} from "@angular/router";

class ImageSnippet {
  constructor(public src: string, public file: File) {}
}

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.scss']
})
export class UserRegisterComponent implements OnInit {

  form !: FormGroup
  userInfo?: UserInfoModel
  selectedFile ?: ImageSnippet;
  submitted = false;
  loading = false

  constructor(private formBuilder: FormBuilder, private userInfoService: UserInfoService, private router: Router) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      firstName: ['', Validators.required],
      surname: ['', Validators.required],
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern("(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")]],
      profileImagePath: ['']
    })
  }

  get f() {
    return this.form.controls;
  }

  submitForm() {
    console.log(this.form.get('firstName')?.value, this.form.get('surname')?.value)

    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    this.loading = true;
    // @ts-ignore
    this.userInfoService.registerUser(this.form.value)
      .subscribe( (value) => {
          console.log(value)
        });

  }

/*  processFile(imageInput: any) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {

      this.selectedFile = new ImageSnippet(event.target.result, file);
      console.log(this.selectedFile)
      // this.imageService.uploadImage(this.selectedFile.file).subscribe(
      //   (res) => {
      //
      //   },
      //   (err) => {
      //
      //   })
    });

    reader.readAsDataURL(file);
  }*/
}
