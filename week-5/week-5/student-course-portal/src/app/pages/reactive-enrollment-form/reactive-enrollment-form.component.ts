import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  ValidationErrors,
  Validators,
} from '@angular/forms';

/**
 * Custom synchronous validator: rejects course codes that start with the
 * disallowed 'XX' prefix.
 */
export function noCourseCode(
  control: AbstractControl
): ValidationErrors | null {
  const value = control.value;
  if (typeof value === 'string' && value.toUpperCase().startsWith('XX')) {
    return { noCourseCode: true };
  }
  return null;
}

/**
 * Custom async validator: simulates a server-side "email already taken"
 * check. Any email containing 'test@' is treated as already registered.
 * Async validators only run after all sync validators on the same control
 * pass, so we avoid firing this "API call" on an already-invalid email.
 */
export function simulateEmailCheck(
  control: AbstractControl
): Promise<ValidationErrors | null> {
  return new Promise((resolve) => {
    setTimeout(() => {
      const value: string = control.value || '';
      resolve(value.includes('test@') ? { emailTaken: true } : null);
    }, 800);
  });
}

@Component({
  selector: 'app-reactive-enrollment-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './reactive-enrollment-form.component.html',
  styleUrls: ['./reactive-enrollment-form.component.css'],
})
export class ReactiveEnrollmentFormComponent implements OnInit {
  enrollForm!: FormGroup;
  submitted = false;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.enrollForm = this.fb.group({
      studentName: ['', [Validators.required, Validators.minLength(3)]],
      studentEmail: this.fb.control(
        '',
        [Validators.required, Validators.email],
        [simulateEmailCheck]
      ),
      courseId: [null, [Validators.required, noCourseCode]],
      preferredSemester: ['Odd', Validators.required],
      agreeToTerms: [false, Validators.requiredTrue],
      additionalCourses: this.fb.array([]),
    });
  }

  /**
   * Typed getter for the FormArray. Doing the `as FormArray` cast once
   * here (instead of in the template) keeps the template free of
   * TypeScript casting syntax and gives every consumer a properly typed
   * FormArray without repeating the cast everywhere it's used.
   */
  get additionalCourses(): FormArray {
    return this.enrollForm.get('additionalCourses') as FormArray;
  }

  addCourse(): void {
    this.additionalCourses.push(new FormControl('', Validators.required));
  }

  removeCourse(index: number): void {
    this.additionalCourses.removeAt(index);
  }

  onSubmit(): void {
    // enrollForm.value excludes disabled controls; getRawValue() includes
    // every control regardless of disabled state. Since nothing here is
    // disabled, both would currently be identical - but getRawValue() is
    // the safer choice if a field is ever conditionally disabled later.
    console.log('enrollForm.value:', this.enrollForm.value);
    console.log('enrollForm.getRawValue():', this.enrollForm.getRawValue());

    if (this.enrollForm.valid) {
      this.submitted = true;
    }
  }
}
