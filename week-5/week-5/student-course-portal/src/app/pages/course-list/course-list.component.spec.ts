import { TestBed } from '@angular/core/testing';
import { CourseListComponent } from './course-list.component';

describe('CourseListComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CourseListComponent],
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(CourseListComponent);
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('should load courses from CourseService on init', () => {
    const fixture = TestBed.createComponent(CourseListComponent);
    fixture.detectChanges();
    expect(fixture.componentInstance.courses.length).toBeGreaterThan(0);
  });

  it('should set selectedCourseId and log on enroll', () => {
    spyOn(console, 'log');
    const fixture = TestBed.createComponent(CourseListComponent);
    fixture.detectChanges();
    fixture.componentInstance.onEnroll(1);
    expect(fixture.componentInstance.selectedCourseId).toBe(1);
    expect(console.log).toHaveBeenCalledWith('Enrolling in course: 1');
  });

  it('trackByCourseId should return the course id', () => {
    const fixture = TestBed.createComponent(CourseListComponent);
    const result = fixture.componentInstance.trackByCourseId(0, {
      id: 7,
      name: 'X',
      code: 'X1',
      credits: 2,
      gradeStatus: 'pending',
    });
    expect(result).toBe(7);
  });
});
