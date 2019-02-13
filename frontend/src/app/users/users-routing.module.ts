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
    }
  },
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
