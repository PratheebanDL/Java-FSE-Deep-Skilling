import { TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { CourseCardComponent } from './course-card.component';
import { Course } from '../../models/course.model';

const mockCourse: Course = {
  id: 1,
  name: 'Data Structures',
  code: 'CS101',
  credits: 4,
  gradeStatus: 'passed',
};

describe('CourseCardComponent', () => {
  let component: CourseCardComponent;
  let fixture: ReturnType<
    typeof TestBed.createComponent<CourseCardComponent>
  >;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CourseCardComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CourseCardComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render the course name from @Input', () => {
    component.course = mockCourse;
    fixture.detectChanges();
    const h3 = fixture.debugElement.query(By.css('h3')).nativeElement;
    expect(h3.textContent).toContain('Data Structures');
  });

  it('should emit enrollRequested with the course id on button click', () => {
    component.course = mockCourse;
    fixture.detectChanges();
    spyOn(component.enrollRequested, 'emit');

    fixture.debugElement.query(By.css('.actions button')).nativeElement.click();

    expect(component.enrollRequested.emit).toHaveBeenCalledWith(1);
  });

  it('should log previous/current values in ngOnChanges', () => {
    spyOn(console, 'log');
    component.course = mockCourse;
    component.ngOnChanges({
      course: {
        previousValue: undefined,
        currentValue: mockCourse,
        firstChange: true,
        isFirstChange: () => true,
      },
    });
    expect(console.log).toHaveBeenCalled();
  });
});
