import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '../translate/translate.module';
import { SectionButtonBarComponent } from './section-button-bar.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    TranslateModule,
  ],
  declarations: [SectionButtonBarComponent],
  exports: [SectionButtonBarComponent],
})
export class SectionButtonBarModule { }
