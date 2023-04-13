import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ClientPhoneComponent } from './list/client-phone.component';
import { ClientPhoneDetailComponent } from './detail/client-phone-detail.component';
import { ClientPhoneUpdateComponent } from './update/client-phone-update.component';
import { ClientPhoneDeleteDialogComponent } from './delete/client-phone-delete-dialog.component';
import { ClientPhoneRoutingModule } from './route/client-phone-routing.module';

@NgModule({
  imports: [SharedModule, ClientPhoneRoutingModule],
  declarations: [ClientPhoneComponent, ClientPhoneDetailComponent, ClientPhoneUpdateComponent, ClientPhoneDeleteDialogComponent],
})
export class ClientPhoneModule {}
