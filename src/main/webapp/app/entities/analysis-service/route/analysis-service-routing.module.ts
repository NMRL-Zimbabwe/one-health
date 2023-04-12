import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AnalysisServiceComponent } from '../list/analysis-service.component';
import { AnalysisServiceDetailComponent } from '../detail/analysis-service-detail.component';
import { AnalysisServiceUpdateComponent } from '../update/analysis-service-update.component';
import { AnalysisServiceRoutingResolveService } from './analysis-service-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const analysisServiceRoute: Routes = [
  {
    path: '',
    component: AnalysisServiceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnalysisServiceDetailComponent,
    resolve: {
      analysisService: AnalysisServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnalysisServiceUpdateComponent,
    resolve: {
      analysisService: AnalysisServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnalysisServiceUpdateComponent,
    resolve: {
      analysisService: AnalysisServiceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(analysisServiceRoute)],
  exports: [RouterModule],
})
export class AnalysisServiceRoutingModule {}
