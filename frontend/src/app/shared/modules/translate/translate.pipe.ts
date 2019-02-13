import { Pipe, PipeTransform } from '@angular/core';
import { LoggerService } from '../../services/logger/logger.service';
import { TranslateService } from './translate.service';

@Pipe({
  name: 'translate'
})
export class TranslatePipe implements PipeTransform {
  constructor(
    private _translate: TranslateService,
    private logger: LoggerService
  ) {
    this.logger.debug('{TranslatePipe} Build');
  }

  transform(value: string): any {
    // this.logger.debug('{TranslatePipe} Transform: ' + value);
    if (!value) {
      return;
    }
    return this._translate.instant(value);
  }
}
