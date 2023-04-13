import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ClientAddressComponent } from '../list/client-address.component';
import { ClientAddressDetailComponent } from '../detail/client-address-detail.component';
import { ClientAddressUpdateComponent } from '../update/client-address-update.component';
import { ClientAddressRoutingResolveService } from './client-address-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const clientAddressRoute: Routes = [
  {
    path: '',
    component: ClientAddressComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClientAddressDetailComponent,
    resolve: {
      clientAddress: ClientAddressRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClientAddressUpdateComponent,
    resolve: {
      clientAddress: ClientAddressRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClientAddressUpdateComponent,
    resolve: {
      clientAddress: ClientAddressRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(clientAddressRoute)],
  exports: [RouterModule],
})
export class ClientAddressRoutingModule {}
