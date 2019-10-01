import { Component, OnInit } from '@angular/core';
import { Product } from '../models/product';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-list-product',
  templateUrl: './list-product.component.html',
  styleUrls: ['./list-product.component.css']
})
export class ListProductComponent implements OnInit {

  public title: string;
  public productSelected: Product;
  public listProducts: Product[] = [];

  constructor(private productService: ProductService) {
    this.title = 'Listar productos';
    this.productSelected = new Product();
  }

  ngOnInit() {
    this.getProducts();
  }

  public getProducts(): void {
    this.productSelected.productId = 1;
    this.productSelected.name = 'Cerveza Pilsen';
    this.productSelected.description = 'Bebida embriagante a base de malta';
    this.productSelected.price = 3000;
    this.listProducts.push(this.productSelected);
    /*
    this.productService.getProducts().subscribe(response => {
      this.listProducts = response;
    }, error => {
      console.error(error);
    });*/
  }

  public setProduct(product: Product): void {
    this.productSelected = product;
  }

  public deleteProduct(): void {
    this.productService.deleteProduct(this.productSelected.productId).subscribe(response => {
      this.getProducts();
    }, error => {
      console.error(error);
    });
  }

}
