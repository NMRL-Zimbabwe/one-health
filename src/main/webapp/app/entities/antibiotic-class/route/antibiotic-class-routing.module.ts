import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AntibioticClassComponent } from '../list/antibiotic-class.component';
import { AntibioticClassDetailComponent } from '../detail/antibiotic-class-detail.component';
import { AntibioticClassUpdateComponent } from '../update/antibiotic-class-update.component';
import { AntibioticClassRoutingResolveService } from './antibiotic-class-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const antibioticClassRoute: Routes = [
  {
    path: '',
    component: AntibioticClassComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AntibioticClassDetailComponent,
    resolve: {
      antibioticClass: AntibioticClassRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AntibioticClassUpdateComponent,
    resolve: {
      antibioticClass: AntibioticClassRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AntibioticClassUpdateComponent,
    resolve: {
      antibioticClass: AntibioticClassRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(antibioticClassRoute)],
  exports: [RouterModule],
})
export class AntibioticClassRoutingModule {}
