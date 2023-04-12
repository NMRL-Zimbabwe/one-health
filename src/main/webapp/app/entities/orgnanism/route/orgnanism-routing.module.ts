import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrgnanismComponent } from '../list/orgnanism.component';
import { OrgnanismDetailComponent } from '../detail/orgnanism-detail.component';
import { OrgnanismUpdateComponent } from '../update/orgnanism-update.component';
import { OrgnanismRoutingResolveService } from './orgnanism-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const orgnanismRoute: Routes = [
  {
    path: '',
    component: OrgnanismComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrgnanismDetailComponent,
    resolve: {
      orgnanism: OrgnanismRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrgnanismUpdateComponent,
    resolve: {
      orgnanism: OrgnanismRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrgnanismUpdateComponent,
    resolve: {
      orgnanism: OrgnanismRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(orgnanismRoute)],
  exports: [RouterModule],
})
export class OrgnanismRoutingModule {}
