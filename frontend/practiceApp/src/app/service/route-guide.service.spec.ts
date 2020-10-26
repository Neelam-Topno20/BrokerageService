import { TestBed } from '@angular/core/testing';

import { RouteGuideService } from './route-guide.service';

describe('RouteGuideService', () => {
  let service: RouteGuideService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RouteGuideService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
