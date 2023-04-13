import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ClientAddressComponent } from './list/client-address.component';
import { ClientAddressDetailComponent } from './detail/client-address-detail.component';
import { ClientAddressUpdateComponent } from './update/client-address-update.component';
import { ClientAddressDeleteDialogComponent } from './delete/client-address-delete-dialog.component';
import { ClientAddressRoutingModule } from './route/client-address-routing.module';

@NgModule({
  imports: [SharedModule, ClientAddressRoutingModule],
  declarations: [ClientAddressComponent, ClientAddressDetailComponent, ClientAddressUpdateComponent, ClientAddressDeleteDialogComponent],
})
export class ClientAddressModule {}
