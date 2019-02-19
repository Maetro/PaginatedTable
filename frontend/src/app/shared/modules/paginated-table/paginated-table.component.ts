import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTable, MatTableDataSource } from '@angular/material';
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import * as _ from 'lodash';
// tslint:disable-next-line:max-line-length
import { PaginatedFilter, PaginatedFilterResponse, PaginatedSearchService, SortStatus, TableAction, TableColumn } from './service/paginated-search-service';

@Component({
  selector: 'app-paginated-table',
  templateUrl: './paginated-table.component.html',
  styleUrls: ['./paginated-table.component.scss'],
})
export class PaginatedTableComponent implements OnInit {

  @Input() endPoint: string;

  @Input() resultList: any[];

  @Input() columnInfo: TableColumn[];

  @Input() displayedColumns: string[];

  @Input() tableActions: TableAction[];

  @Output() executeActionEvent: EventEmitter<any> = new EventEmitter();

  showInput = Array();
  sortStatus: SortStatus;
  baseSearchQuery: string;
  page: number;
  filters = new Map();
  numberOfElements: number;
  searchQuery: string;
  dataSource = new MatTableDataSource<any>(this.resultList);

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatTable) table: MatTable<any>;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private paginatedSearchService: PaginatedSearchService,
    private route: ActivatedRoute,
    private datePipe: DatePipe,
    private translateService: TranslateService) { }

  ngOnInit() {
    console.log('ngOnInit PaginatedTableComponent');
    this.route.data.subscribe((data: any) => {
      this.numberOfElements = data.data.numberOfElements;
      this.resultList = data.data.data;
      this.dataSource.data = this.resultList;
      this.baseSearchQuery = data.searchQuery;
      this.tableActions = data.tableActions;
    });
    this.paginator.page.subscribe((page) => {
      this.changePage(page);
    });
    this.sort.sortChange.subscribe((sortEvent) => {
      this.changeSorting(sortEvent);
    });

  }

  public changeSorting(sortEvent: any) {
    this.paginator.firstPage();
    console.log(sortEvent);
    this.sortStatus = sortEvent;
    this.search();
  }

  public changePage(event) {
    const stringFilterList = Array.from(this.filters.values());
    if (this.baseSearchQuery) {
      stringFilterList.push(this.baseSearchQuery);
    }
    const paginatedFilter: PaginatedFilter = {
      page: this.paginator.pageIndex,
      numberOfElements: this.paginator.pageSize,
      searchQuery: stringFilterList,
      sortStatus: this.sortStatus,
    };
    this.page = event;
    this.paginatedSearchService.searchWithPaginatedFilter(this.endPoint,
      paginatedFilter).subscribe((response) => {
        this.initializeList(response);
      });
  }

  initializeList(response: PaginatedFilterResponse): any {
    this.resultList = [];
    this.numberOfElements = response.numberOfElements;
    this.resultList = response.data;
    this.dataSource.data = this.resultList;
  }

  applyFilter(key, value) {
    if (value && value.trim().length > 0 && this.valueContainsComparator(value.trim())) {
      this.addFilter(key, value);
    } else {
      this.filters.delete(key);
    }

    const stringFilterList = Array.from(this.filters.values());
    if (this.baseSearchQuery) {
      stringFilterList.push(this.baseSearchQuery);
    }
    this.searchQuery = '';
    this.paginator.firstPage();
    let paginatedFilter: PaginatedFilter = {
      page: this.paginator.pageIndex,
      numberOfElements: this.paginator.pageSize,
      searchQuery: null,
      sortStatus: this.sortStatus,
    };
    if (stringFilterList && stringFilterList.length > 0) {
      this.searchQuery = key + ':' + value;
      paginatedFilter = {
        page: this.paginator.pageIndex,
        numberOfElements: this.paginator.pageSize,
        searchQuery: stringFilterList,
        sortStatus: this.sortStatus,
      };
    }
    this.paginatedSearchService.searchWithPaginatedFilter(this.endPoint, paginatedFilter).subscribe((response) => {
      this.onFilteredSearch(response);
    });
    if (value === null || value === '') {
      this.showInput[key] = false;
    }
  }

  public addFilter(key: any, value: any) {
    this.filters.set(key, key + ':' + value);
  }

  onFilteredSearch(event) {
    this.resultList = [];
    this.numberOfElements = event.numberOfElements;
    this.resultList = event.data;
    this.dataSource.data = this.resultList;
  }

  getNameOfColumn(column: string) {
    const columnInfo = _.find(this.columnInfo, (info) => column === info.key);
    return columnInfo.value;
  }

  hasAction(column: string) {
    const columnInfo = _.find(this.columnInfo, (info) => column === info.key);
    return columnInfo.action;
  }

  getAction(column: string) {
    const columnInfo = _.find(this.columnInfo, (info) => column === info.key);
    const action = _.find(this.tableActions, (actionP) => columnInfo.action === actionP.actionName);
    return action;
  }

  getValueOfColumn(columnDefinitions: any, column: string) {
    const columns = column.split('.');
    const columnInfo = _.find(this.columnInfo, (info) => column === info.key);
    const isDate = columnInfo.isDate ? columnInfo.isDate : false;
    const isTranslatable = columnInfo.isTranslatable ? columnInfo.isTranslatable : false;
    let value = columnDefinitions[column];

    if (columns.length > 1) {
      let cont = 0;
      columns.forEach((columnP) => {
        if (cont === 0) {
          value = columnDefinitions[columnP];
        } else {
          value = value[columnP];
        }
        cont++;
      });
    }

    if (columnInfo.display) {
      value = columnInfo.display(columnDefinitions, value);
    }
    if (isDate) {
      value = this.datePipe.transform(value, 'dd/MM/yyyy HH:mm');
    }
    if (isTranslatable) {
      value = this.translateService.instant(value);
    }
    return value;
  }

  isSortable(column: string) {
    const columnInfo = _.find(this.columnInfo, (info) => column === info.key);
    let result = true;
    if (columnInfo.isSortable !== undefined) {
      result = columnInfo.isSortable;
    }
    return result;
  }

  changeShowInput(column: string) {
    if (this.isSearchable(column)) {
      this.showInput[column] = true;
    }
  }

  isSearchable(column: string) {
    const columnInfo = _.find(this.columnInfo, (info) => column === info.key);
    let result = true;
    if (columnInfo.isSearchable !== undefined) {
      result = columnInfo.isSortable;
    }
    return result;
  }

  valueContainsComparator(value: string): boolean {
    let constainsValue = true;
    if (value.includes('>')) {
      constainsValue = value.substring(1).length > 0;
    }
    if (value.includes('>=')) {
      constainsValue = value.substring(2).length > 0;
    }
    if (value.includes('<')) {
      constainsValue = value.substring(1).length > 0;
    }
    if (value.includes('<=')) {
      constainsValue = value.substring(2).length > 0;
    }
    if (value.includes('==')) {
      constainsValue = value.substring(2).length > 0;
    }
    return constainsValue;
  }

  executeAction(action: TableAction, rowRepresentation: any) {
    this.executeActionEvent.emit({ tableAction: action, row: rowRepresentation });
  }

  showAction(action: TableAction, rowRepresentation: any): boolean {
    let showAction = true;
    if (action.accessFunctionality) {
      let writerGrant = false;
      if (action.writerGrant) {
        writerGrant = action.writerGrant;
      }
      // showAction = this.avaliableFunctionalitiesService.isFunctionalityActive(action.accessFunctionality, writerGrant);
    }
    if (showAction && action.actionCondition) {
      showAction = action.actionCondition(rowRepresentation);
    }
    return showAction;
  }

  private search() {
    const stringFilterList = Array.from(this.filters.values());
    if (this.baseSearchQuery) {
      stringFilterList.push(this.baseSearchQuery);
    }
    const paginatedFilter: PaginatedFilter = {
      page: this.paginator.pageIndex,
      numberOfElements: this.paginator.pageSize,
      searchQuery: stringFilterList,
      sortStatus: this.sortStatus,
    };
    this.paginatedSearchService.searchWithPaginatedFilter(this.endPoint, paginatedFilter).subscribe((response) => {
      this.initializeList(response);
    });
  }

  public reload() {
    this.search();
  }

  translate(key): string {
    return this.translateService.instant(key);
  }

}
