import { Component, OnInit, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { environment } from 'src/environments/environment';
import { PaginatedTableComponent } from '../shared/modules/paginated-table/paginated-table.component';
import { TableColumn } from '../shared/modules/paginated-table/service/paginated-search-service';
import { Role } from './dtos/role';
import { User } from './dtos/user';
import { userLeftBarButtons, userRightBarButtons } from './users-buttons';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  leftbuttons = userLeftBarButtons;
  rightbuttons = userRightBarButtons;
  columnInfo: TableColumn[] = [
    { key: 'id', value: this.translateService.instant('USERS.ID') },
    { key: 'name', value: this.translateService.instant('USERS.NAME') },
    { key: 'surname', value: this.translateService.instant('USERS.SURNAME') },
    { key: 'birthdate', value: this.translateService.instant('USERS.BIRTHDATE'), isDate: true },
    { key: 'numberOfChildren', value: this.translateService.instant('USERS.NUMBER_OF_CHILDREN') },
    { key: 'score', value: this.translateService.instant('USERS.SCORE') },
    { key: 'roles', value: this.translateService.instant('USERS.ROLES'), display: (user: User, roles: Role[]) => {
      return this.displayRolesOfUser(roles);
    }    },
    { key: 'action', value: this.translateService.instant('USERS.ACTION'), isSortable: false }];
  serverUrl = environment.serverUrl;
  endPoint: string;
  displayedColumns: string[] = ['id', 'name', 'surname', 'birthdate', 'numberOfChildren', 'score', 'roles', 'action'];

  @ViewChild(PaginatedTableComponent) paginatedTableComponent: PaginatedTableComponent;

  constructor(private translateService: TranslateService) { }

  ngOnInit() {
  }

  private displayRolesOfUser(roles: Role[]) {
    let result = '';
    if (roles && roles.length > 0) {
      roles.forEach((role) => {
        result += role.name + ', ';
      });
    }
    return result.substr(0, result.length - 2);
  }

}
