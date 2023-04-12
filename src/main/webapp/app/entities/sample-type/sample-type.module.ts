import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SampleTypeComponent } from './list/sample-type.component';
import { SampleTypeDetailComponent } from './detail/sample-type-detail.component';
import { SampleTypeUpdateComponent } from './update/sample-type-update.component';
import { SampleTypeDeleteDialogComponent } from './delete/sample-type-delete-dialog.component';
import { SampleTypeRoutingModule } from './route/sample-type-routing.module';

@NgModule({
  imports: [SharedModule, SampleTypeRoutingModule],
  declarations: [SampleTypeComponent, SampleTypeDetailComponent, SampleTypeUpdateComponent, SampleTypeDeleteDialogComponent],
})
export class SampleTypeModule {}
