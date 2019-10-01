import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { GLOBAL } from './global';
import { Product } from '../models/product';
import { Observable } from 'rxjs';

@Injectable()
export class ProductService {
  
  private url: string;

  constructor(private httpClient: HttpClient) {
    this.url = GLOBAL.url;
  }

  public getProducts(): Observable<any> {
    return this.httpClient.get(this.url + 'product');  
  }

  public getProductById(productId: number): Observable<any> {
    return this.httpClient.get(this.url + 'product/' + productId); 
  }

  public addProduct(product: Product): Observable<any> {
    let params = JSON.stringify(product);
    let headers = new HttpHeaders({'Content-Type': 'application/json'});

    return this.httpClient.post(this.url + 'product', params, {headers: headers});
  }

  public updateProduct(product: Product): Observable<any> {
    let params = JSON.stringify(product);
    let headers = new HttpHeaders({'Content-Type': 'application/json'});

    return this.httpClient.put(this.url + 'product', params, {headers: headers});
  }
  
  public deleteProduct(productId: number): Observable<any> {
    return this.httpClient.delete(this.url + 'product/' + productId);
  }
}
