import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AntibioticComponent } from './list/antibiotic.component';
import { AntibioticDetailComponent } from './detail/antibiotic-detail.component';
import { AntibioticUpdateComponent } from './update/antibiotic-update.component';
import { AntibioticDeleteDialogComponent } from './delete/antibiotic-delete-dialog.component';
import { AntibioticRoutingModule } from './route/antibiotic-routing.module';

@NgModule({
  imports: [SharedModule, AntibioticRoutingModule],
  declarations: [AntibioticComponent, AntibioticDetailComponent, AntibioticUpdateComponent, AntibioticDeleteDialogComponent],
})
export class AntibioticModule {}
