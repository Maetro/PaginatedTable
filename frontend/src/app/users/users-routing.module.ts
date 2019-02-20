import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LoggerService } from '../shared/services/logger/logger.service';
import { UsersResolverService } from './resolvers/users-resolver.service';
import { UsersComponent } from './users.component';

const userRoutes = [
  {
    path: '',
    component: UsersComponent,
    pathMatch: 'full',
    resolve: { data: UsersResolverService },
    data: {
      searchQuery: 'userStatus: ACTIVE',
      tableActions: [
        {
          icon: 'fa fa-pencil',
          actionName: 'editUser',
          actionText: 'USERS.ACTION.EDIT',
          accessFunctionality: 'USER',
          writerGrant: true,
        }, {
          icon: 'fa fa-trash',
          actionName: 'removeUser',
          actionText: 'USERS.ACTION.REMOVE',
          accessFunctionality: 'USER',
          writerGrant: true,
        }],
    }
  }, {
    path: 'pending',
    component: UsersComponent,
    pathMatch: 'full',
    resolve: { data: UsersResolverService },
    data: {
      searchQuery: 'userStatus: PENDING',
      tableActions: [
        {
          icon: 'fa fa-check',
          actionName: 'approveUser',
          actionText: 'USERS.ACTION.APPROVE',
          accessFunctionality: 'USER',
          writerGrant: true,
        },
        {
          icon: 'fa fa-pencil',
          actionName: 'editUser',
          actionText: 'USERS.ACTION.EDIT',
          accessFunctionality: 'USER',
          writerGrant: true,
        }, {
          icon: 'fa fa-trash',
          actionName: 'removeUser',
          actionText: 'USERS.ACTION.REMOVE',
          accessFunctionality: 'USER',
          writerGrant: true,
        }],
    }
  }, {
    path: 'deleted',
    component: UsersComponent,
    pathMatch: 'full',
    resolve: { data: UsersResolverService },
    data: {
      searchQuery: 'userStatus: DELETED',
      tableActions: [
        {
          icon: 'fa fa-undo',
          actionName: 'restoreUser',
          actionText: 'USERS.ACTION.RESTORE',
          accessFunctionality: 'USER',
          writerGrant: true,
        }
      ],
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(userRoutes)],
  exports: [RouterModule],
})
export class UsersRoutingModule {
  constructor(private logger: LoggerService) {
    logger.debug('{UsersRoutingModule} Build');
  }
}
