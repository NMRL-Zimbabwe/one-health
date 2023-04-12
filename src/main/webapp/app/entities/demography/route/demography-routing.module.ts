import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemographyComponent } from '../list/demography.component';
import { DemographyDetailComponent } from '../detail/demography-detail.component';
import { DemographyUpdateComponent } from '../update/demography-update.component';
import { DemographyRoutingResolveService } from './demography-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const demographyRoute: Routes = [
  {
    path: '',
    component: DemographyComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemographyDetailComponent,
    resolve: {
      demography: DemographyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemographyUpdateComponent,
    resolve: {
      demography: DemographyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemographyUpdateComponent,
    resolve: {
      demography: DemographyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demographyRoute)],
  exports: [RouterModule],
})
export class DemographyRoutingModule {}
