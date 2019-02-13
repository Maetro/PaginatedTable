import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { LoggerService } from 'src/app/shared/services/logger/logger.service';
import { PaginatedFilterResponse, PaginatedSearchService } from 'src/app/shared/services/paginated-search/paginated-search-service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UsersResolverService implements Resolve<Observable<PaginatedFilterResponse>> {
  serverUrl = environment.serverUrl;

  constructor(
    private logger: LoggerService,
    private paginatedSearchService: PaginatedSearchService
  ) {
    logger.debug('{CommunicationTaskResolverService} build');
  }

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
  ): Observable<PaginatedFilterResponse> {
    this.logger.debug('{CommunicationTaskResolverService} resolve');
    const filter = { id: null, page: 0, numberOfElements: 10, searchQuery: [route.data.searchQuery] };
    return this.paginatedSearchService.searchWithPaginatedFilter(this.serverUrl + 'user/filter', filter);

  }
}
