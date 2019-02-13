import { Inject, Injectable } from '@angular/core';
import { LoggerService } from '../../services/logger/logger.service';
import { UtilService } from '../../services/util/util.service';
import { TRANSLATIONS } from './translation';

@Injectable({
  providedIn: 'root'
})
export class TranslateService {
  private _currentLang: string;

  public get currentLang(): string {
    this._currentLang = this.utilService.remember('lang');
    return this._currentLang;
  }

  // inject our translations
  constructor(@Inject(TRANSLATIONS) private _translations: any,
    private logger: LoggerService,
    private utilService: UtilService) {
    logger.debug('{TranslateService} Build');
  }

  public use(lang: string): void {
    // set current language
    this._currentLang = lang;
    this.utilService.remember('lang', lang);
  }

  public getCurrentLang(): string {
    return this._currentLang;
  }

  private translate(key: string): string {
    // private perform translation
    const translation = key;
    const identifiers: string[] = key.split('.');
    let result = this._translations[this.currentLang];
    try {
      identifiers.forEach(identifier => {
        result = result[identifier];
      });
    } catch (e) {
      result = null;
    }
    if (result) {
      return result;
    }
    return translation;
  }

  public instant(key: string) {
    // call translation
    return this.translate(key);
  }

}
