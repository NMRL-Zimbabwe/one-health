import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ClientPhoneComponent } from '../list/client-phone.component';
import { ClientPhoneDetailComponent } from '../detail/client-phone-detail.component';
import { ClientPhoneUpdateComponent } from '../update/client-phone-update.component';
import { ClientPhoneRoutingResolveService } from './client-phone-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const clientPhoneRoute: Routes = [
  {
    path: '',
    component: ClientPhoneComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClientPhoneDetailComponent,
    resolve: {
      clientPhone: ClientPhoneRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClientPhoneUpdateComponent,
    resolve: {
      clientPhone: ClientPhoneRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClientPhoneUpdateComponent,
    resolve: {
      clientPhone: ClientPhoneRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(clientPhoneRoute)],
  exports: [RouterModule],
})
export class ClientPhoneRoutingModule {}
