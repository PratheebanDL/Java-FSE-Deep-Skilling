import { Routes } from '@angular/router';

import { HomeComponent } from './pages/home/home.component';
import { CourseListComponent } from './pages/course-list/course-list.component';
import { StudentProfileComponent } from './pages/student-profile/student-profile.component';
import { EnrollmentFormComponent } from './pages/enrollment-form/enrollment-form.component';
import { ReactiveEnrollmentFormComponent } from './pages/reactive-enrollment-form/reactive-enrollment-form.component';

/**
 * Central route table for the Student Course Portal.
 * Guards and lazy loading are introduced in Hands-On 7 - not yet needed
 * for Hands-On 1 through 5.
 */
export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'courses', component: CourseListComponent },
  { path: 'profile', component: StudentProfileComponent },
  { path: 'enroll', component: EnrollmentFormComponent },
  { path: 'enroll-reactive', component: ReactiveEnrollmentFormComponent },
  { path: '**', redirectTo: '' },
];
