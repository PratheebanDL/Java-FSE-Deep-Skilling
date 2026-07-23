import { TestBed } from '@angular/core/testing';
import {
  ReactiveEnrollmentFormComponent,
  noCourseCode,
  simulateEmailCheck,
} from './reactive-enrollment-form.component';
import { FormControl } from '@angular/forms';

describe('ReactiveEnrollmentFormComponent', () => {
  let fixture: ReturnType<
    typeof TestBed.createComponent<ReactiveEnrollmentFormComponent>
  >;
  let component: ReactiveEnrollmentFormComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveEnrollmentFormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ReactiveEnrollmentFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should be invalid when empty', () => {
    expect(component.enrollForm.invalid).toBeTrue();
  });

  it('noCourseCode validator should flag codes starting with XX', () => {
    const control = new FormControl('XX101');
    expect(noCourseCode(control)).toEqual({ noCourseCode: true });

    const okControl = new FormControl('CS101');
    expect(noCourseCode(okControl)).toBeNull();
  });

  it('simulateEmailCheck should flag emails containing test@', async () => {
    const control = new FormControl('test@example.com');
    const result = await simulateEmailCheck(control);
    expect(result).toEqual({ emailTaken: true });

    const okControl = new FormControl('jordan@example.com');
    const okResult = await simulateEmailCheck(okControl);
    expect(okResult).toBeNull();
  });

  it('should add and remove additional course controls', () => {
    expect(component.additionalCourses.length).toBe(0);
    component.addCourse();
    expect(component.additionalCourses.length).toBe(1);
    component.removeCourse(0);
    expect(component.additionalCourses.length).toBe(0);
  });
});
