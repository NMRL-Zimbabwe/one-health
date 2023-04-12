import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemographicCodingComponent } from './list/demographic-coding.component';
import { DemographicCodingDetailComponent } from './detail/demographic-coding-detail.component';
import { DemographicCodingUpdateComponent } from './update/demographic-coding-update.component';
import { DemographicCodingDeleteDialogComponent } from './delete/demographic-coding-delete-dialog.component';
import { DemographicCodingRoutingModule } from './route/demographic-coding-routing.module';

@NgModule({
  imports: [SharedModule, DemographicCodingRoutingModule],
  declarations: [
    DemographicCodingComponent,
    DemographicCodingDetailComponent,
    DemographicCodingUpdateComponent,
    DemographicCodingDeleteDialogComponent,
  ],
})
export class DemographicCodingModule {}
