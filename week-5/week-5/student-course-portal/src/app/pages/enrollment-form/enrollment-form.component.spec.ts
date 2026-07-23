import { TestBed } from '@angular/core/testing';
import { EnrollmentFormComponent } from './enrollment-form.component';

describe('EnrollmentFormComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EnrollmentFormComponent],
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(EnrollmentFormComponent);
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('should disable the submit button while the form is invalid', () => {
    const fixture = TestBed.createComponent(EnrollmentFormComponent);
    fixture.detectChanges();
    const submitBtn: HTMLButtonElement =
      fixture.nativeElement.querySelector('button[type="submit"]');
    expect(submitBtn.disabled).toBeTrue();
  });

  it('should log the form value and validity on submit', () => {
    spyOn(console, 'log');
    const fixture = TestBed.createComponent(EnrollmentFormComponent);
    fixture.detectChanges();
    const form: any = { value: { studentName: 'Test' }, valid: false };
    fixture.componentInstance.onSubmit(form);
    expect(console.log).toHaveBeenCalledWith('Form value:', form.value);
    expect(console.log).toHaveBeenCalledWith('Form valid:', form.valid);
  });
});
