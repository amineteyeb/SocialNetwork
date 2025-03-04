import { Component, OnInit } from '@angular/core';
import { trigger, transition, style, animate } from '@angular/animations';

interface Product {
  id: number;
  name: string;
  category: string;
  price: number;
  originalPrice?: number;
  discount?: number;
  isNew: boolean;
  image: string;
  colors: string[];
  rating: number;
  stock: number;
  brand?: string;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(20px)' }),
        animate('300ms cubic-bezier(0.4, 0, 0.2, 1)', 
        style({ opacity: 1, transform: 'translateY(0)' }))
      ])
    ])
  ]
})
export class HomeComponent implements OnInit {
  // Data
  categories = [
    { id: 1, name: 'Laptops', count: 45 },
    { id: 2, name: 'Smartphones', count: 89 },
    { id: 3, name: 'Cameras', count: 32 },
    { id: 4, name: 'Accessories', count: 156 }
  ];

  colors = [
    { name: 'Red', code: '#ff0000' },
    { name: 'Blue', code: '#0000ff' },
    { name: 'Green', code: '#00ff00' },
    { name: 'Black', code: '#000000' },
    { name: 'White', code: '#ffffff' },
    { name: 'Pink', code: '#ff69b4' },
    { name: 'Gold', code: '#ffd700' },
    { name: 'Silver', code: '#c0c0c0' }
  ];

  products: Product[] = this.generateDummyProducts(50);
  filteredProducts: Product[] = [];
  paginatedProducts: Product[] = [];

  // Filters
  selectedCategories = new Set<number>();
  selectedColors = new Set<string>();
  priceRange = [0, 2000];
  sortOption = 'popular';
  currentPage = 1;
  pageSize = 12;
  wishlist = new Set<number>();

  ngOnInit() {
    this.applyFilters();
  }

  // Generate dummy products
  private generateDummyProducts(count: number): Product[] {
    const products = [];
    const categories = ['Laptops', 'Smartphones', 'Cameras', 'Accessories'];
    const brands = ['Apple', 'Samsung', 'Sony', 'Canon', 'Dell', 'HP'];
    
    for (let i = 1; i <= count; i++) {
      products.push({
        id: i,
        name: `Product ${i}`,
        category: categories[Math.floor(Math.random() * categories.length)],
        price: Math.floor(Math.random() * 2000) + 100,
        originalPrice: Math.random() > 0.5 ? Math.floor(Math.random() * 2500) + 100 : undefined,
        discount: Math.random() > 0.7 ? Math.floor(Math.random() * 50) + 10 : undefined,
        isNew: Math.random() > 0.5,
        image: `assets/images/product_${(i % 8) + 1}.png`,
        colors: ['#000000', '#c0c0c0', '#ff0000'].slice(0, Math.floor(Math.random() * 3) + 1),
        rating: Math.floor(Math.random() * 5) + 1,
        stock: Math.floor(Math.random() * 100),
        brand: brands[Math.floor(Math.random() * brands.length)]
      });
    }
    return products;
  }

  // Filter Management
  toggleCategoryFilter(categoryId: number) {
    this.selectedCategories.has(categoryId) 
      ? this.selectedCategories.delete(categoryId)
      : this.selectedCategories.add(categoryId);
    this.applyFilters();
  }

  toggleColorFilter(colorCode: string) {
    this.selectedColors.has(colorCode)
      ? this.selectedColors.delete(colorCode)
      : this.selectedColors.add(colorCode);
    this.applyFilters();
  }

  updatePriceFilter() {
    this.priceRange = [
      Math.max(0, this.priceRange[0]),
      Math.max(this.priceRange[0], this.priceRange[1])
    ];
    this.applyFilters();
  }

  updateSorting(option: string) {
    this.sortOption = option;
    this.applyFilters();
  }

  applyFilters() {
    this.filteredProducts = this.products
      .filter(product => this.filterByCategory(product))
      .filter(product => this.filterByColor(product))
      .filter(product => this.filterByPrice(product))
      .sort(this.getSortingFunction());
    
    this.currentPage = 1;
    this.updatePagination();
  }

  // Filter Predicates
  private filterByCategory(product: Product): boolean {
    return this.selectedCategories.size === 0 || 
           this.selectedCategories.has(this.categories.findIndex(c => c.name === product.category) + 1);
  }

  private filterByColor(product: Product): boolean {
    return this.selectedColors.size === 0 ||
           product.colors.some(color => this.selectedColors.has(color));
  }

  private filterByPrice(product: Product): boolean {
    const [min, max] = this.priceRange;
    return product.price >= min && product.price <= max;
  }

  // Sorting Functions
  private getSortingFunction(): (a: Product, b: Product) => number {
    switch (this.sortOption) {
      case 'priceLowHigh': return (a, b) => a.price - b.price;
      case 'priceHighLow': return (a, b) => b.price - a.price;
      case 'newest': return (a, b) => (b.isNew ? 1 : 0) - (a.isNew ? 1 : 0);
      default: return (a, b) => b.rating - a.rating;
    }
  }

  // Pagination
  updatePagination() {
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.paginatedProducts = this.filteredProducts.slice(start, end);
  }

  // Wishlist Management
  toggleWishlist(productId: number) {
    this.wishlist.has(productId) 
      ? this.wishlist.delete(productId) 
      : this.wishlist.add(productId);
  }

  isInWishlist(productId: number): boolean {
    return this.wishlist.has(productId);
  }

  // Product Actions
  addToCart(product: Product) {
    if (product.stock > 0) {
      product.stock--;
      // Implement actual cart functionality
    }
  }

  quickView(product: Product) {
    // Implement quick view modal
  }

  // Utility Functions
  get totalProducts(): number {
    return this.filteredProducts.length;
  }

  get showingProducts(): string {
    const start = (this.currentPage - 1) * this.pageSize + 1;
    const end = Math.min(this.currentPage * this.pageSize, this.totalProducts);
    return `${start} - ${end} of ${this.totalProducts}`;
  }

  getRatingStars(rating: number): number[] {
    const fullStars = Math.floor(rating);
    const hasHalfStar = rating % 1 !== 0;
    return Array(fullStars).fill(1).concat(hasHalfStar ? [0.5] : []);
  }
}