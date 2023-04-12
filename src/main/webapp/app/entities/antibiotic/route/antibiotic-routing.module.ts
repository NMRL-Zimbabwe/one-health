import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AntibioticComponent } from '../list/antibiotic.component';
import { AntibioticDetailComponent } from '../detail/antibiotic-detail.component';
import { AntibioticUpdateComponent } from '../update/antibiotic-update.component';
import { AntibioticRoutingResolveService } from './antibiotic-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const antibioticRoute: Routes = [
  {
    path: '',
    component: AntibioticComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AntibioticDetailComponent,
    resolve: {
      antibiotic: AntibioticRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AntibioticUpdateComponent,
    resolve: {
      antibiotic: AntibioticRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AntibioticUpdateComponent,
    resolve: {
      antibiotic: AntibioticRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(antibioticRoute)],
  exports: [RouterModule],
})
export class AntibioticRoutingModule {}
