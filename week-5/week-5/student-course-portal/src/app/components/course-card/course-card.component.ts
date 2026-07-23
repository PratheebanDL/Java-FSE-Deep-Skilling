import { CommonModule } from '@angular/common';
import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  Output,
  SimpleChanges,
} from '@angular/core';

import { Course } from '../../models/course.model';
import { HighlightDirective } from '../../directives/highlight.directive';
import { CreditLabelPipe } from '../../pipes/credit-label.pipe';

@Component({
  selector: 'app-course-card',
  standalone: true,
  imports: [CommonModule, HighlightDirective, CreditLabelPipe],
  templateUrl: './course-card.component.html',
  styleUrls: ['./course-card.component.css'],
})
export class CourseCardComponent implements OnChanges {
  @Input() course!: Course;
  @Output() enrollRequested = new EventEmitter<number>();

  isExpanded = false;

  ngOnChanges(changes: SimpleChanges): void {
    // Fires whenever an @Input reference changes (e.g. a new course object
    // is passed in from the parent). Logging previous/current lets us see
    // exactly what changed and when.
    if (changes['course']) {
      console.log(
        'CourseCardComponent.ngOnChanges -> previous:',
        changes['course'].previousValue,
        'current:',
        changes['course'].currentValue
      );
    }
  }

  /**
   * A getter keeps the template free of inline object-literal expressions,
   * which makes the template easier to read and avoids re-creating the
   * object literal on every change-detection pass in some binding styles.
   */
  get cardClasses(): Record<string, boolean> {
    return {
      'card--enrolled': !!this.course?.enrolled,
      'card--full': (this.course?.credits ?? 0) >= 4,
      expanded: this.isExpanded,
    };
  }

  get borderColor(): string {
    switch (this.course?.gradeStatus) {
      case 'passed':
        return 'green';
      case 'failed':
        return 'red';
      default:
        return 'grey';
    }
  }

  toggleExpanded(): void {
    this.isExpanded = !this.isExpanded;
  }

  onEnrollClick(): void {
    this.enrollRequested.emit(this.course.id);
  }
}
