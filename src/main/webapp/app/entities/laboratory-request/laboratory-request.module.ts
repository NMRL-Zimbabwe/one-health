import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LaboratoryRequestComponent } from './list/laboratory-request.component';
import { LaboratoryRequestDetailComponent } from './detail/laboratory-request-detail.component';
import { LaboratoryRequestUpdateComponent } from './update/laboratory-request-update.component';
import { LaboratoryRequestDeleteDialogComponent } from './delete/laboratory-request-delete-dialog.component';
import { LaboratoryRequestRoutingModule } from './route/laboratory-request-routing.module';

@NgModule({
  imports: [SharedModule, LaboratoryRequestRoutingModule],
  declarations: [
    LaboratoryRequestComponent,
    LaboratoryRequestDetailComponent,
    LaboratoryRequestUpdateComponent,
    LaboratoryRequestDeleteDialogComponent,
  ],
})
export class LaboratoryRequestModule {}
