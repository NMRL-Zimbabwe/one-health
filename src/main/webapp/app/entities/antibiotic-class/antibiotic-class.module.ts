import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AntibioticClassComponent } from './list/antibiotic-class.component';
import { AntibioticClassDetailComponent } from './detail/antibiotic-class-detail.component';
import { AntibioticClassUpdateComponent } from './update/antibiotic-class-update.component';
import { AntibioticClassDeleteDialogComponent } from './delete/antibiotic-class-delete-dialog.component';
import { AntibioticClassRoutingModule } from './route/antibiotic-class-routing.module';

@NgModule({
  imports: [SharedModule, AntibioticClassRoutingModule],
  declarations: [
    AntibioticClassComponent,
    AntibioticClassDetailComponent,
    AntibioticClassUpdateComponent,
    AntibioticClassDeleteDialogComponent,
  ],
})
export class AntibioticClassModule {}
