import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssetsbarComponent } from './assetsbar.component';

describe('AssetsbarComponent', () => {
  let component: AssetsbarComponent;
  let fixture: ComponentFixture<AssetsbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssetsbarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AssetsbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
