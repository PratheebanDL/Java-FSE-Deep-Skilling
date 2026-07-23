import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';

/**
 * app.config.ts is the standalone replacement for app.module.ts.
 * Application-wide providers (router, http client, forms modules, NgRx
 * store, etc.) are registered here instead of inside an @NgModule.
 */
export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes)],
};
