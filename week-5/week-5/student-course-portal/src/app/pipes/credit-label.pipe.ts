import { Pipe, PipeTransform } from '@angular/core';

/**
 * Transforms a numeric credits value into a human-readable label.
 * 0, null or undefined -> 'No Credits'
 * 1                     -> '1 Credit'
 * 2+                    -> 'N Credits'
 */
@Pipe({
  name: 'creditLabel',
  standalone: true,
})
export class CreditLabelPipe implements PipeTransform {
  transform(value: number | null | undefined): string {
    if (value === null || value === undefined || value === 0) {
      return 'No Credits';
    }
    return value === 1 ? '1 Credit' : `${value} Credits`;
  }
}
