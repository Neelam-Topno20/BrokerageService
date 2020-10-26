import { TestBed } from '@angular/core/testing';

import { RouteAdminGuideService } from './route-admin-guide.service';

describe('RouteAdminGuideService', () => {
  let service: RouteAdminGuideService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RouteAdminGuideService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
