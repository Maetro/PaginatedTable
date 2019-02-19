import { CommonModule, DatePipe } from '@angular/common';
import { NgModule } from '@angular/core';
// tslint:disable-next-line:max-line-length
import { MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule, MatPaginatorIntl, MatPaginatorModule, MatSortModule, MatTableModule } from '@angular/material';
import { PaginatedTableComponent } from './paginated-table.component';
import { MatPaginatorIntlTranslation } from './translation/mat-paginator-intl-translation';

@NgModule({
  imports: [
    CommonModule,
    MatPaginatorModule,
    MatSortModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule
  ],
  declarations: [PaginatedTableComponent],
  providers: [DatePipe, { provide: MatPaginatorIntl, useClass: MatPaginatorIntlTranslation}],
  exports: [PaginatedTableComponent],
})
export class PaginatedTableModule { }
