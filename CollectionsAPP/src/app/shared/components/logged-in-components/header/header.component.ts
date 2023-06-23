import { Component } from '@angular/core';
import {SessionService} from "../../../services/session.service";
import {ActivatedRoute, Router} from "@angular/router";
import { JavaHttpService } from 'src/app/shared/connection/http/java-http.service';
import { TagDto } from 'src/app/shared/connection/models/tag.dto';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CollectionComponent } from 'src/app/pages/collection/collection.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent{

  tags : TagDto[]
  searchedTerm: string
  selectedTag: string

  form = new FormGroup({
    top_search: new FormControl(''),
    tag_search: new FormControl('') 
  });
  
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    private http: JavaHttpService,
  ) {
  }

  prepend(value:TagDto, array:TagDto[]):TagDto[] {
    var newArray = array.slice();
    newArray.unshift(value);
    return newArray;
  }

  ngOnInit() : void{
    
    this.http.get<TagDto[]>('/tag/all').subscribe({
      complete: () =>{},
      error: (error) => {console.log(error)},
      next: (response: TagDto[]) => {
        this.tags = response;
        let anyTag = new TagDto();
        anyTag.name = "ANY"
        this.tags = this.prepend(anyTag, this.tags)
        console.log(response)

      }
    })
  }

  onSearchClick(){
    console.log("ASDASDSADASDADSDs")
    console.log(this.searchedTerm )
    this.router.navigate(['../search/item/' + (this.searchedTerm ??'ANY') + '/' + ( this.selectedTag ?? 'ANY') ],{
      onSameUrlNavigation:"reload"
    })
  }

  logOut() {
    SessionService.getInstance().clearSession()
    window.location.reload()
    this.router.navigate([".."])
  }

  onNotificationClick() {
    this.router.navigate(["../notifications"])
  }

  onHomeClick() {
    this.router.navigate([".."])
  }
}
