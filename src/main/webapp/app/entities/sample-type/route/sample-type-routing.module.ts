import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SampleTypeComponent } from '../list/sample-type.component';
import { SampleTypeDetailComponent } from '../detail/sample-type-detail.component';
import { SampleTypeUpdateComponent } from '../update/sample-type-update.component';
import { SampleTypeRoutingResolveService } from './sample-type-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const sampleTypeRoute: Routes = [
  {
    path: '',
    component: SampleTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SampleTypeDetailComponent,
    resolve: {
      sampleType: SampleTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SampleTypeUpdateComponent,
    resolve: {
      sampleType: SampleTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SampleTypeUpdateComponent,
    resolve: {
      sampleType: SampleTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sampleTypeRoute)],
  exports: [RouterModule],
})
export class SampleTypeRoutingModule {}
