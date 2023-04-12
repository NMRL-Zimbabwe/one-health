import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemographicCodingComponent } from '../list/demographic-coding.component';
import { DemographicCodingDetailComponent } from '../detail/demographic-coding-detail.component';
import { DemographicCodingUpdateComponent } from '../update/demographic-coding-update.component';
import { DemographicCodingRoutingResolveService } from './demographic-coding-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const demographicCodingRoute: Routes = [
  {
    path: '',
    component: DemographicCodingComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemographicCodingDetailComponent,
    resolve: {
      demographicCoding: DemographicCodingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemographicCodingUpdateComponent,
    resolve: {
      demographicCoding: DemographicCodingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemographicCodingUpdateComponent,
    resolve: {
      demographicCoding: DemographicCodingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demographicCodingRoute)],
  exports: [RouterModule],
})
export class DemographicCodingRoutingModule {}
