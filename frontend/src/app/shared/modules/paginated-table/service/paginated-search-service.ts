import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface SortStatus {
  active: string;
  direction: string;
}

export interface TableColumn {
  key: string;
  value: string;
  isDate?: boolean;
  isSortable?: boolean;
  isSearchable?: boolean;
  isTranslatable?: boolean;
  action?: string;
  display?(data: any, value: any): string;
}

export interface TableAction {
  icon: string;
  actionName: string;
  actionText: string;
  accessFunctionality?: string;
  writerGrant?: boolean;
  actionCondition?(data: any): boolean;
}

export interface ITableActionEvent {
  tableAction: TableAction;
  row: any;
}
export interface PaginatedFilter {
  page: number;
  numberOfElements: number;
  searchQuery: string[];
  sortStatus?: SortStatus;
}

export interface PaginatedFilterResponse {
  numberOfElements: number;
  data: any[];
}

@Injectable({
  providedIn: 'root',
})
export class PaginatedSearchService {

  constructor(private httpClient: HttpClient) {
  }

  searchWithPaginatedFilter(url: string, paginatedFilter: PaginatedFilter): Observable<PaginatedFilterResponse> {
    const headers = new HttpHeaders({
      'Content-type': 'application/json',
    });

    return this.httpClient
      .post<PaginatedFilterResponse>(url, JSON.stringify(paginatedFilter), { headers });
  }

}
