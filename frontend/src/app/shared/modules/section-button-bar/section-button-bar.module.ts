import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { SectionButtonBarComponent } from './section-button-bar.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    TranslateModule.forChild({}),
  ],
  declarations: [SectionButtonBarComponent],
  exports: [SectionButtonBarComponent],
})
export class SectionButtonBarModule { }
