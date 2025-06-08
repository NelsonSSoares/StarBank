/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { NasaapiService } from './nasaapi.service';

describe('Service: Nasaapi', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NasaapiService]
    });
  });

  it('should ...', inject([NasaapiService], (service: NasaapiService) => {
    expect(service).toBeTruthy();
  }));
});
