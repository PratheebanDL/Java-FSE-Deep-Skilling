# Week 5 — Student Course Portal (Digital Nurture 5.0 — Angular v20.0)

This folder contains the Angular workspace for the **Student Course Portal**,
covering **Hands-On 1 through Hands-On 5** of the Angular Hands-On Exercise
Book:

| Hands-On | Topic                                                        |
|----------|---------------------------------------------------------------|
| 1        | Environment setup, project structure, first component        |
| 2        | Data binding, lifecycle hooks, @Input/@Output communication   |
| 3        | Structural/attribute directives, custom directive & pipe      |
| 4        | Template-driven forms & validation                            |
| 5        | Reactive forms — FormBuilder, custom & async validators, FormArray |

## Project location

```
week-5/
└── student-course-portal/   <- the Angular workspace (open this in VS Code)
```

## How to run it

This sandbox has no internet access, so the `node_modules` folder and the
Angular CLI binaries are **not** included — install them once on your own
machine:

```bash
cd student-course-portal
npm install
npm start          # ng serve — open http://localhost:4200
npm test           # ng test — runs all *.spec.ts files with Karma/Jasmine
```

## What's implemented

- **Standalone components** (Angular 20 default — no NgModules):
  `AppComponent`, `HeaderComponent`, `HomeComponent`, `CourseListComponent`,
  `CourseCardComponent`, `StudentProfileComponent`, `EnrollmentFormComponent`
  (template-driven form), `ReactiveEnrollmentFormComponent` (reactive form).
- **Routing**: `/`, `/courses`, `/profile`, `/enroll`, `/enroll-reactive`.
- **CourseService** (`providedIn: 'root'`) — a singleton in-memory data
  store shared by Home, CourseList and StudentProfile.
- **Custom directive**: `appHighlight` (configurable hover highlight).
- **Custom pipe**: `creditLabel` (`3` → `"3 Credits"`, `0`/`null` →
  `"No Credits"`).
- **Template-driven form** (`/enroll`): `ngModel`, `required`, `minlength`,
  `email`, contextual error messages, `ng-valid`/`ng-invalid` styling,
  reset via `NgForm.resetForm()`.
- **Reactive form** (`/enroll-reactive`): `FormBuilder`/`FormGroup`, a custom
  synchronous validator (`noCourseCode`), a custom async validator
  (`simulateEmailCheck`), and a `FormArray` for dynamically adding/removing
  additional course entries.
- **Unit tests**: every component, directive, pipe and service has a
  matching `*.spec.ts` file (Jasmine/Karma via `TestBed`).
- `notes.txt` inside `student-course-portal/` answers Hands-On 1, Task 1,
  steps 2 and 5 (file-purpose notes and the `angular.json` budgets
  explanation).

## Note on scaffolding

Because this environment can't reach npm, every file that `ng new` /
`ng generate` would normally produce (angular.json, tsconfig*.json,
package.json, main.ts, component/directive/pipe/service files and their
`.spec.ts` counterparts) has been hand-written to match what the CLI
generates for Angular 20 standalone projects. Running `npm install` will
pull in the real `@angular/cli` and dependencies so the project behaves
exactly as if it had been scaffolded normally.
