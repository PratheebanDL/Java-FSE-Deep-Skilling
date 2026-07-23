import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit, OnDestroy {
  portalName = 'Student Course Portal';
  isPortalActive = true;
  message = '';

  // [(ngModel)]="searchTerm" is shorthand for:
  //   [ngModel]="searchTerm" (ngModelChange)="searchTerm = $event"
  // i.e. one-way property binding (component -> DOM) PLUS an event binding
  // (DOM -> component) combined into a single two-way syntax.
  searchTerm = '';

  enrolledCount = 3;
  gpa = 3.8;
  coursesAvailable = 0;

  constructor(private courseService: CourseService) {}

  ngOnInit(): void {
    // ngOnInit fires once, after @Inputs are first set - the right place
    // for data fetching (unlike the constructor, which runs before inputs
    // are available).
    this.coursesAvailable = this.courseService.getCourses().length;
    console.log('HomeComponent initialised — courses loaded');
  }

  ngOnDestroy(): void {
    console.log('HomeComponent destroyed');
  }

  onEnrollClick(): void {
    this.message = 'Enrollment opened!';
  }
}
