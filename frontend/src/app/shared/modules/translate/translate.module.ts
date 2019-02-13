import { NgModule } from '@angular/core';
import { TranslatePipe } from './translate.pipe';
import { TRANSLATION_PROVIDERS } from './translation';

@NgModule({
  declarations:   [ TranslatePipe ],
  providers:      [ TRANSLATION_PROVIDERS, TranslatePipe ],
  exports: 		[ TranslatePipe ]
})
export class TranslateModule { }
