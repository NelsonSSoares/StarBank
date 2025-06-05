import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ToolbarComponent } from "../shared/components/toolbar/toolbar.component";
import { FooterComponent } from "../shared/components/footer/footer.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    ToolbarComponent,
    FooterComponent
],
providers: [],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})

export class AppComponent {
  title = 'FED-StarBank';

  constructor(

  ) {}

  ngOnInit() {

  }

}
