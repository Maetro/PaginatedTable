import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilService {
  constructor(@Inject(PLATFORM_ID) private platformId: Object
  ) { }

  remember(key: string, value?: string) {
    if (isPlatformBrowser(this.platformId)) {
      if (value === undefined) {
        const b64Value = localStorage.getItem(key);
        let decoded = null;
        if (b64Value) {
          try {
            decoded = atob(b64Value);
          } catch (e) {
            localStorage.setItem(key, null);
          }
          return decoded;
        }
      } else {
        localStorage.setItem(key, btoa(value));
      }
    }
  }

  forget(key: string) {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem(key);
    }
  }

  rememberInSession(key: string, value?: string) {
    if (isPlatformBrowser(this.platformId)) {
      if (value === undefined) {
        const b64Value = sessionStorage.getItem(key);
        if (b64Value !== undefined) {
          return atob(b64Value);
        }
      } else {
        sessionStorage.setItem(key, btoa(value));
      }
    }
  }

  clearStorages(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.clear();
      sessionStorage.clear();
    }
  }
}
