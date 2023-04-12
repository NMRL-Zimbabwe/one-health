import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DemographyComponent } from './list/demography.component';
import { DemographyDetailComponent } from './detail/demography-detail.component';
import { DemographyUpdateComponent } from './update/demography-update.component';
import { DemographyDeleteDialogComponent } from './delete/demography-delete-dialog.component';
import { DemographyRoutingModule } from './route/demography-routing.module';

@NgModule({
  imports: [SharedModule, DemographyRoutingModule],
  declarations: [DemographyComponent, DemographyDetailComponent, DemographyUpdateComponent, DemographyDeleteDialogComponent],
})
export class DemographyModule {}
