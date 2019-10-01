import { Component, OnInit } from '@angular/core';
import { Product } from '../models/product';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {

  private productId: number;
  public title: string;
  public product: Product;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService) {
    this.title = 'Editar producto';
    this.product = new Product();
  }

  ngOnInit() {
    this.route.params.forEach((params: Params) => {
      this.productId = params['id'];
      if (this.productId == -1) {
        console.log('Agregar nuevo producto');
      } else {
        this.getProduct();
      }
    });
  }

  public getProduct(): void {
    /*
    this.productService.getProductById(this.productId).subscribe(response => {
      this.product = response;
    }, error => {
      console.error(error);
    });
    */    
  }

  public saveProduct(): void {
    if (this.productId == -1) {
      this.addProduct();
    } else {
      this.updateProduct();
    }
  }

  public addProduct(): void {
    this.productService.addProduct(this.product).subscribe(response => {
      this.router.navigate(['/list-product']);
    }, error => {
      console.error(error);
    });
  }

  public updateProduct(): void {
    this.productService.updateProduct(this.product).subscribe(response => {
      this.router.navigate(['/list-product']);
    }, error => {
      console.error(error);
    });
  }

  public cancel(): void {
    this.router.navigate(['/list-product']);
  }

}
