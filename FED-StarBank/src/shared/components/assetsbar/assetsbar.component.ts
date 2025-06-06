import { AfterViewInit, Component, signal } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-assetsbar',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './assetsbar.component.html',
  styleUrl: './assetsbar.component.scss'
})
export class AssetsbarComponent implements AfterViewInit {

  prices = signal({
    BTC: 0,
    ETH: 0,
    USD: 0,
    EUR: 0
  });

  directions = signal<{ [key: string]: 'up' | 'down' | 'none' }>({
    BTC: 'none',
    ETH: 'none',
    USD: 'none',
    EUR: 'none'
  });

  constructor(private http: HttpClient) {}

  ngAfterViewInit(): void {
    this.fetchPrices();
    setInterval(() => this.fetchPrices(), 5000);
  }

  fetchPrices(): void {
    const symbols = ['BTCUSDT', 'ETHUSDT', 'EURUSDT', 'USDTBRL'];

    symbols.forEach(symbol => {
      this.http.get<any>(`https://api.binance.com/api/v3/ticker/price?symbol=${symbol}`)
        .subscribe({
          next: (response) => {
            const newPrice = parseFloat(response.price);
            const prev = this.prices();

            switch (symbol) {
              case 'BTCUSDT':
                this.directions.update(d => ({ ...d, BTC: this.getDirection(prev.BTC, newPrice) }));
                this.prices.update(p => ({ ...p, BTC: newPrice }));
                break;
              case 'ETHUSDT':
                this.directions.update(d => ({ ...d, ETH: this.getDirection(prev.ETH, newPrice) }));
                this.prices.update(p => ({ ...p, ETH: newPrice }));
                break;
              case 'EURUSDT':
                this.directions.update(d => ({ ...d, EUR: this.getDirection(prev.EUR, newPrice) }));
                this.prices.update(p => ({ ...p, EUR: newPrice }));
                break;
              case 'USDTBRL':
                this.directions.update(d => ({ ...d, USD: this.getDirection(prev.USD, newPrice) }));
                this.prices.update(p => ({ ...p, USD: newPrice }));
                break;
            }
          },
          error: (error) => {
            console.error(`Erro ao buscar ${symbol}:`, error);
          }
        });
    });
  }

  private getDirection(oldPrice: number, newPrice: number): 'up' | 'down' | 'none' {
    if (oldPrice === 0) return 'none';
    return newPrice > oldPrice ? 'up' : newPrice < oldPrice ? 'down' : 'none';
  }
}
