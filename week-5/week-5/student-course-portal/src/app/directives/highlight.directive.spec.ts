import { Component } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { HighlightDirective } from './highlight.directive';

@Component({
  standalone: true,
  imports: [HighlightDirective],
  template: `<p appHighlight="lightblue">Test</p>`,
})
class TestHostComponent {}

describe('HighlightDirective', () => {
  it('should set background colour on mouseenter and clear on mouseleave', () => {
    const fixture = TestBed.configureTestingModule({
      imports: [TestHostComponent],
    }).createComponent(TestHostComponent);
    fixture.detectChanges();

    const p: HTMLElement = fixture.nativeElement.querySelector('p');
    p.dispatchEvent(new Event('mouseenter'));
    expect(p.style.backgroundColor).toBe('lightblue');

    p.dispatchEvent(new Event('mouseleave'));
    expect(p.style.backgroundColor).toBe('');
  });
});
