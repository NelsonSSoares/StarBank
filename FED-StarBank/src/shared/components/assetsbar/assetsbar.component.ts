import { AfterViewInit, Component, OnInit, signal } from '@angular/core';
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

  constructor(private http: HttpClient) {}
  ngAfterViewInit(): void {
    this.fetchPrices();
    setInterval(() => this.fetchPrices(), 5000); // Atualiza a cada 5 segundos
  }

/*   ngOnInit(): void {
    this.fetchPrices();
    setInterval(() => this.fetchPrices(), 5000); // atualiza a cada 5s
  } */


  fetchPrices(): void {
    const symbols = ['BTCUSDT', 'ETHUSDT', 'EURUSDT', 'USDTBRL'];

    symbols.forEach(symbol => {
      this.http.get<any>(`https://api.binance.com/api/v3/ticker/price?symbol=${symbol}`)
        .subscribe({
          next: (response) => {
            const price = parseFloat(response.price);
            switch (symbol) {
              case 'BTCUSDT':
                this.prices.update(p => ({ ...p, BTC: price }));
                break;
              case 'ETHUSDT':
                this.prices.update(p => ({ ...p, ETH: price }));
                break;
              case 'EURUSDT':
                this.prices.update(p => ({ ...p, EUR: price }));
                break;
              case 'USDTBRL':
                this.prices.update(p => ({ ...p, USD: price }));
                break;
            }
          },
          error: (error) => {
            console.error(`Erro ao buscar ${symbol}:`, error);
          }
        });
    });
  }
}
