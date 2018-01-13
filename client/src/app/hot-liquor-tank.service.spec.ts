import { TestBed, inject } from '@angular/core/testing';

import { HotLiquorTankService } from './hot-liquor-tank.service';

describe('HotLiquorTankService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HotLiquorTankService]
    });
  });

  it('should be created', inject([HotLiquorTankService], (service: HotLiquorTankService) => {
    expect(service).toBeTruthy();
  }));
});
