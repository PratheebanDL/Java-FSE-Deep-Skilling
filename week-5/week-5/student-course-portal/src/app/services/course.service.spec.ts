import { TestBed } from '@angular/core/testing';
import { CourseService } from './course.service';
import { Course } from '../models/course.model';

describe('CourseService', () => {
  let service: CourseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CourseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return the seeded list of courses', () => {
    expect(service.getCourses().length).toBe(5);
  });

  it('should find a course by id', () => {
    const course = service.getCourseById(1);
    expect(course?.name).toBe('Data Structures');
  });

  it('should return undefined for an unknown id', () => {
    expect(service.getCourseById(999)).toBeUndefined();
  });

  it('should add a new course', () => {
    const newCourse: Course = {
      id: 6,
      name: 'Cloud Computing',
      code: 'CS106',
      credits: 3,
      gradeStatus: 'pending',
    };
    service.addCourse(newCourse);
    expect(service.getCourses().length).toBe(6);
    expect(service.getCourseById(6)).toEqual(newCourse);
  });
});
