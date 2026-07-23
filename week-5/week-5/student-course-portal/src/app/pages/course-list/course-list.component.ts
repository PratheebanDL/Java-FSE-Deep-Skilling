import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

import { CourseCardComponent } from '../../components/course-card/course-card.component';
import { Course } from '../../models/course.model';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [CommonModule, CourseCardComponent],
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css'],
})
export class CourseListComponent implements OnInit {
  courses: Course[] = [];
  isLoading = true;
  selectedCourseId: number | null = null;

  constructor(private courseService: CourseService) {}

  ngOnInit(): void {
    this.courses = this.courseService.getCourses();

    // Simulate an async load so the *ngIf loading state can be observed.
    setTimeout(() => {
      this.isLoading = false;
    }, 1500);
  }

  /**
   * trackBy lets Angular identify each item by its stable id instead of by
   * object reference/index. Without it, any array replacement forces
   * Angular to destroy and re-create every DOM node for the list. With it,
   * only items whose id actually changed are re-rendered.
   */
  trackByCourseId(_index: number, course: Course): number {
    return course.id;
  }

  onEnroll(courseId: number): void {
    console.log('Enrolling in course: ' + courseId);
    this.selectedCourseId = courseId;

    const course = this.courses.find((c) => c.id === courseId);
    if (course) {
      course.enrolled = !course.enrolled;
    }
  }
}
