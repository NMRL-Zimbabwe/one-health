import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrganismComponent } from '../list/organism.component';
import { OrganismDetailComponent } from '../detail/organism-detail.component';
import { OrganismUpdateComponent } from '../update/organism-update.component';
import { OrganismRoutingResolveService } from './organism-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const organismRoute: Routes = [
  {
    path: '',
    component: OrganismComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrganismDetailComponent,
    resolve: {
      organism: OrganismRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganismUpdateComponent,
    resolve: {
      organism: OrganismRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrganismUpdateComponent,
    resolve: {
      organism: OrganismRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(organismRoute)],
  exports: [RouterModule],
})
export class OrganismRoutingModule {}
