import { map } from 'rxjs/operators';
import { AuthenticationService } from './authentication.service';
import { ProfileUser } from './../models/user-profile';
import { from, Observable, of, switchMap } from 'rxjs';
import { AngularFirestore, AngularFirestoreCollection, AngularFirestoreDocument } from '@angular/fire/compat/firestore';
import { Injectable } from '@angular/core';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  userDoc!: AngularFirestoreDocument<ProfileUser>;
  user!: Observable<ProfileUser | undefined>;

  get currentUserProfile$(){
    return this.authService.currentUser$.pipe(
      switchMap(user => {
        if(!user?.uid){

          return of(null);
        }

        this.userDoc = this.firestore.doc<ProfileUser>(`users/${user.uid}`)
        this.user = this.userDoc.valueChanges()

        return  this.user;
      })
    )
  }

  constructor(private firestore: AngularFirestore, private authService: AuthenticationService) {

  }

  addUser(user: ProfileUser):Observable<any>{
      const ref = this.firestore.doc(`users/${user?.uid}`)
      return from(ref.set(user));
  }

  updateUser(user: ProfileUser): Observable<any>{
    const ref = this.firestore.doc(`users/${user?.uid}`)
    return from(ref.update({...user}));
  }

}
