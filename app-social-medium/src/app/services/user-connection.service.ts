import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {UserConnectionModel} from "../models/user-connection.model";

@Injectable({
  providedIn: 'root'
})
export class UserConnectionService {

  constructor(private httpClient: HttpClient) { }


  connectWithUser(idToConnect?: string, id?: string): Observable<HttpResponse<UserConnectionModel>> {
    return this.httpClient.post<UserConnectionModel>(environment.USERINFO_URL + `users/${id}/connections/request/${idToConnect}`, null, {observe: 'response'});
  }

  acceptConnection(idToAccept ?: string, id ?: string): Observable<HttpResponse<UserConnectionModel>> {
    return this.httpClient.put<UserConnectionModel>(environment.USERINFO_URL + `users/${id}/connections/accept/${idToAccept}`, null, {observe: 'response'})
  }

  rejectConnection(idToIgnore ?: string, id ?: string): Observable<HttpResponse<UserConnectionModel>> {
    return this.httpClient.put<UserConnectionModel>(environment.USERINFO_URL + `users/${id}/connections/reject/${idToIgnore}`, null, {observe: 'response'})
  }

  blockConnection(idToBlock ?: string, id ?: string): Observable<HttpResponse<UserConnectionModel>> {
    return this.httpClient.put<UserConnectionModel>(environment.USERINFO_URL + `users/${id}/connections/block/${idToBlock}`, null, {observe: 'response'})
  }

  unblockConnection(idToUnblock ?: string, id ?: string): Observable<HttpResponse<UserConnectionModel>> {
    return this.httpClient.put<UserConnectionModel>(environment.USERINFO_URL + `users/${id}/connections/unblock/${idToUnblock}`, null, {observe: 'response'})
  }
}
