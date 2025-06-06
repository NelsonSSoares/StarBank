
import { Component, OnInit, signal } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-assetsbar',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './assetsbar.component.html',
  styleUrl: './assetsbar.component.scss'
})
export class AssetsbarComponent implements OnInit {
  prices = signal<Record<'BTC' | 'ETH' | 'USD' | 'EUR', number>>({
    BTC: 0,
    ETH: 0,
    USD: 0,
    EUR: 0
  });

  directions = signal<Record<'BTC' | 'ETH' | 'USD' | 'EUR', 'up' | 'down' | 'none'>>({
    BTC: 'none',
    ETH: 'none',
    USD: 'none',
    EUR: 'none'
  });

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchPrices();
    setInterval(() => this.fetchPrices(), 5000); // atualiza a cada 5s
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
                this.updatePrice('BTC', prev.BTC, newPrice);
                break;
              case 'ETHUSDT':
                this.updatePrice('ETH', prev.ETH, newPrice);
                break;
              case 'EURUSDT':
                this.updatePrice('EUR', prev.EUR, newPrice);
                break;
              case 'USDTBRL':
                this.updatePrice('USD', prev.USD, newPrice);
                break;
            }
          },
          error: (error) => {
            console.error(`Erro ao buscar ${symbol}:`, error);
          }
        });
    });
  }

  private updatePrice(key: 'BTC' | 'ETH' | 'USD' | 'EUR', oldPrice: number, newPrice: number) {
    this.directions.update(d => ({ ...d, [key]: this.getDirection(oldPrice, newPrice) }));
    this.prices.update(p => ({ ...p, [key]: newPrice }));
  }

  private getDirection(oldPrice: number, newPrice: number): 'up' | 'down' | 'none' {
    if (oldPrice === 0) return 'none';
    return newPrice > oldPrice ? 'up' : newPrice < oldPrice ? 'down' : 'none';
  }
}

