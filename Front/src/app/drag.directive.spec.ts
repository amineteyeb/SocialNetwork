import { TestBed } from '@angular/core/testing';
import { DragDirective } from './drag.directive';

describe('DragDirective', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DragDirective], // Declare the directive in the testing module
    });
  });

  it('should create an instance', () => {
    const directive = TestBed.inject(DragDirective); // Use TestBed to create an instance
    expect(directive).toBeTruthy();
  });
});
