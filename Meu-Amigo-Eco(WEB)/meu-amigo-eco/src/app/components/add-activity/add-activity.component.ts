import { IActivity } from './../../models/IActivity';
import { ActivityService } from './../../services/activity.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-activity',
  templateUrl: './add-activity.component.html',
  styleUrls: ['./add-activity.component.scss'],
  animations:[

  ]
})
export class AddActivityComponent implements OnInit {
  activity: IActivity = {
    title:'',
    description:'',
    created:''
  }

  ActivityTextValue = "";
  activiesButtonText = "Atividades recentes \\/";
  isActiviesHistoricOpen = false;
  activities!: IActivity[];

  constructor(
    private activityService: ActivityService
    ) {
    }

  ngOnInit(): void {
    this.activityService.getActivies().subscribe(activities => {
      this.activities = activities;
    })
  }

  onSubmit(){
    if(this.activity.title !='' && this.activity.description != ''){
      let date = new Date()
      this.activity.created = `${(new Date).getDate()}/${date.getMonth()+1}/${date.getFullYear()} as ${date.getHours()}:${date.getMinutes()}`;
      this.activityService.addActivity(this.activity);
      this.activity.description = '';
      this.activity.title = '';
    }

  }

  deleteActivity(event: any, activity: IActivity){
    this.activityService.deleteActivity(activity)
  }

  onActiviesButton(){
    this.isActiviesHistoricOpen = !this.isActiviesHistoricOpen;
  }

}
