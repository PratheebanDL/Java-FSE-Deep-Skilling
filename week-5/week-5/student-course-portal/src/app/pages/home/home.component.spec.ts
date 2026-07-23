import { TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';

describe('HomeComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeComponent],
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(HomeComponent);
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('should show the enrollment message after clicking Enroll Now', () => {
    const fixture = TestBed.createComponent(HomeComponent);
    fixture.detectChanges();
    fixture.componentInstance.onEnrollClick();
    fixture.detectChanges();
    expect(fixture.nativeElement.textContent).toContain('Enrollment opened!');
  });

  it('should log on init and destroy', () => {
    spyOn(console, 'log');
    const fixture = TestBed.createComponent(HomeComponent);
    fixture.detectChanges();
    expect(console.log).toHaveBeenCalledWith(
      'HomeComponent initialised — courses loaded'
    );
    fixture.destroy();
    expect(console.log).toHaveBeenCalledWith('HomeComponent destroyed');
  });
});
