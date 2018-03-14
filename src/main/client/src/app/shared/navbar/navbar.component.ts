import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router} from "@angular/router";
import { SearchService} from "../search/service/search.service";
import {Disease, Gene, Term} from "../../browse/models/models";
import {
  trigger,
  state,
  style,
  animate,
  transition, group
} from '@angular/animations';
@Component({
  selector: 'navbar-hpo',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  animations: [
    trigger('searchState', [
      state('inactive', style({
        'height': 0,
        'overflow-y': "hidden"
      })),
      state('active',   style({
        'height': '*',
        'overflow-y': "hidden"
      })),
      transition('inactive => active',
        animate('500ms ease-in-out')),
      transition('active => inactive',
        animate('400ms ease-in-out'))
      ]),
  ]
})
export class NavbarComponent implements OnInit {
  title:string = "Human Phenotype Ontology";
  showSearch: boolean =  false;
  navFilter: string = "all";
  terms: Term[] = [];
  diseases: Disease[] = [];
  genes: Gene[] = [];
  searchstate: string = "inactive";
  constructor(private router: Router, private searchService: SearchService ) {

  }

  ngOnInit() {
    this.router.events.subscribe((event) => {
      if(event instanceof NavigationEnd){
        if(this.router.url != "/"){
          this.showSearch = true;
        }else{
          this.showSearch = false;
        }
      }
    });
  }

  suggestContent(query: string): void {
    if(query){
      if(query.length >= 3 ){
        this.searchService.searchAll(query).subscribe((data) => {
          this.searchstate = "active";
          this.terms = data.terms;
          this.diseases = data.diseases;
          this.genes = data.genes;
        }, (error) => {
          // TODO: Implement Better Error Handling
          console.log(error);
        });
      }else{
        this.searchstate = "inactive";
      }
    }
  }
}
