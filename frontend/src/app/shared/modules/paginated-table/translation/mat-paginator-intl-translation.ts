import { Injectable } from '@angular/core';
import { MatPaginatorIntl } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';

@Injectable()
export class MatPaginatorIntlTranslation extends MatPaginatorIntl {
  itemsPerPageLabel = this.translateService.instant('PAGINATOR.ITEMS_PAGE');
  nextPageLabel = this.translateService.instant('PAGINATOR.NEXT');
  previousPageLabel = this.translateService.instant('PAGINATOR.PREVIOUS');
  firstPageLabel = this.translateService.instant('PAGINATOR.FIRST');
  lastPageLabel = this.translateService.instant('PAGINATOR.LAST');

  constructor(private translateService: TranslateService) {
    super();
  }

  getRangeLabel = function (page, pageSize, length) {
    if (length === 0 || pageSize === 0) {
      return '0' + this.translateService.instant('PAGINATOR.OF') + length;
    }
    length = Math.max(length, 0);
    const startIndex = page * pageSize;
    // If the start index exceeds the list length, do not try and fix the end index to the end.
    const endIndex = startIndex < length ?
      Math.min(startIndex + pageSize, length) :
      startIndex + pageSize;
    return startIndex + 1 + ' - ' + endIndex + this.translateService.instant('PAGINATOR.OF') + length;
  };

}
