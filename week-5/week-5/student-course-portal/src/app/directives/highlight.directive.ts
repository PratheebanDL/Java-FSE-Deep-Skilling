import { Directive, ElementRef, HostListener, Input } from '@angular/core';

/**
 * appHighlight adds a background colour to its host element on mouseenter
 * and removes it on mouseleave. The colour is configurable via
 * @Input() appHighlight - defaults to 'yellow' when no value is passed.
 */
@Directive({
  selector: '[appHighlight]',
  standalone: true,
})
export class HighlightDirective {
  @Input() appHighlight = 'yellow';

  constructor(private el: ElementRef<HTMLElement>) {}

  @HostListener('mouseenter')
  onMouseEnter(): void {
    this.el.nativeElement.style.backgroundColor = this.appHighlight || 'yellow';
  }

  @HostListener('mouseleave')
  onMouseLeave(): void {
    this.el.nativeElement.style.backgroundColor = '';
  }
}
