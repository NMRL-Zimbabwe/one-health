import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AnalysisComponent } from '../list/analysis.component';
import { AnalysisDetailComponent } from '../detail/analysis-detail.component';
import { AnalysisUpdateComponent } from '../update/analysis-update.component';
import { AnalysisRoutingResolveService } from './analysis-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const analysisRoute: Routes = [
  {
    path: '',
    component: AnalysisComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnalysisDetailComponent,
    resolve: {
      analysis: AnalysisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnalysisUpdateComponent,
    resolve: {
      analysis: AnalysisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnalysisUpdateComponent,
    resolve: {
      analysis: AnalysisRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(analysisRoute)],
  exports: [RouterModule],
})
export class AnalysisRoutingModule {}
