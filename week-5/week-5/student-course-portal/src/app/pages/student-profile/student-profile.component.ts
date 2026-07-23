import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

import { CourseService } from '../../services/course.service';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-student-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './student-profile.component.html',
  styleUrls: ['./student-profile.component.css'],
})
export class StudentProfileComponent {
  studentName = 'Jordan Rivera';
  studentEmail = 'jordan.rivera@example.edu';
  courses: Course[] = [];

  constructor(private courseService: CourseService) {
    this.courses = this.courseService.getCourses();
  }

  get enrolledCourses(): Course[] {
    return this.courses.filter((c) => c.enrolled);
  }
}
