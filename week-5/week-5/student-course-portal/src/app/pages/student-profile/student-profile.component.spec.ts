import { TestBed } from '@angular/core/testing';
import { StudentProfileComponent } from './student-profile.component';

describe('StudentProfileComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentProfileComponent],
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(StudentProfileComponent);
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('should render the student name and email', () => {
    const fixture = TestBed.createComponent(StudentProfileComponent);
    fixture.detectChanges();
    const text = fixture.nativeElement.textContent;
    expect(text).toContain('Jordan Rivera');
    expect(text).toContain('jordan.rivera@example.edu');
  });

  it('should show the "no enrollments" message when nothing is enrolled', () => {
    const fixture = TestBed.createComponent(StudentProfileComponent);
    fixture.detectChanges();
    expect(fixture.nativeElement.textContent).toContain(
      'You are not enrolled in any courses yet.'
    );
  });
});
