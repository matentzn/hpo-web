import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/toPromise';


@Injectable()
export class GeneEntrezService {
  headers: Headers;
  options: RequestOptions;

  constructor(private http: Http){

  }
  searchGeneInfo(query: string): Promise<any>{
      return this.http
          .get('https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=gene&id=' + query + "&retmode=json")
          .toPromise()
          .then(response => response.json())
          .catch(this.handleError);
  }
  private handleError(error: any): Promise<any> {
      console.error('Error:', error);
      return Promise.reject(error.message || error);
  }
}