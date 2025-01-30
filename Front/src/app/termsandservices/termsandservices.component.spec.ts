import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TermsandservicesComponent } from './termsandservices.component';

describe('TermsandservicesComponent', () => {
  let component: TermsandservicesComponent;
  let fixture: ComponentFixture<TermsandservicesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TermsandservicesComponent]
    });
    fixture = TestBed.createComponent(TermsandservicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
