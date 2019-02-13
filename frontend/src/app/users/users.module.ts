import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { PaginatedTableModule } from '../shared/modules/paginated-table/paginated-table.module';
import { SectionButtonBarModule } from '../shared/modules/section-button-bar/section-button-bar.module';
import { UsersRoutingModule } from './users-routing.module';
import { UsersComponent } from './users.component';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    SectionButtonBarModule,
    PaginatedTableModule,
    UsersRoutingModule
  ],
  declarations: [UsersComponent]
})
export class UsersModule { }
