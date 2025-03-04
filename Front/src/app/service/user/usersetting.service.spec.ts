import { TestBed } from '@angular/core/testing';

import { UsersettingService } from './usersetting.service';

describe('UsersettingService', () => {
  let service: UsersettingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UsersettingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
