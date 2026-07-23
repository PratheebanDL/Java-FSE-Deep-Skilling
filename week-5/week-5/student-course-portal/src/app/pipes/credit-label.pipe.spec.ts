import { CreditLabelPipe } from './credit-label.pipe';

describe('CreditLabelPipe', () => {
  const pipe = new CreditLabelPipe();

  it('should return "No Credits" for null, undefined, or 0', () => {
    expect(pipe.transform(null)).toBe('No Credits');
    expect(pipe.transform(undefined)).toBe('No Credits');
    expect(pipe.transform(0)).toBe('No Credits');
  });

  it('should return singular label for 1', () => {
    expect(pipe.transform(1)).toBe('1 Credit');
  });

  it('should return plural label for 2 or more', () => {
    expect(pipe.transform(3)).toBe('3 Credits');
  });
});
