import { Injectable } from '@angular/core';
import { IActivity} from './../models/IActivity';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AngularFirestore, AngularFirestoreCollection, AngularFirestoreDocument  } from '@angular/fire/compat/firestore';

@Injectable({
  providedIn: 'root'
})
export class ActivityService {

  activitiesCollection!: AngularFirestoreCollection<IActivity>;
  activities:Observable<IActivity[]>;
  activityDoc!: AngularFirestoreDocument<IActivity>;

  constructor( private readonly firestore: AngularFirestore) {
    this.activitiesCollection = firestore.collection<IActivity>('added-activities', ref => ref.orderBy('created','asc'));

    this.activities = this.activitiesCollection.snapshotChanges().pipe(
      map(actions => actions.map(a => {
        const data = a.payload.doc.data() as IActivity;
        data.id = a.payload.doc.id;
        console.log(data.id)
        return data;
      })
    ))
   }

   getActivies(){
     return this.activities;
   }

   addActivity(item: IActivity){
    this.activitiesCollection.add(item)
   }

   deleteActivity(activity: IActivity){
    this.activityDoc = this.firestore.doc(`added-activities/${activity.id}`);
    this.activityDoc.delete()
   }
}

