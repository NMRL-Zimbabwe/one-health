import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrgnanismComponent } from './list/orgnanism.component';
import { OrgnanismDetailComponent } from './detail/orgnanism-detail.component';
import { OrgnanismUpdateComponent } from './update/orgnanism-update.component';
import { OrgnanismDeleteDialogComponent } from './delete/orgnanism-delete-dialog.component';
import { OrgnanismRoutingModule } from './route/orgnanism-routing.module';

@NgModule({
  imports: [SharedModule, OrgnanismRoutingModule],
  declarations: [OrgnanismComponent, OrgnanismDetailComponent, OrgnanismUpdateComponent, OrgnanismDeleteDialogComponent],
})
export class OrgnanismModule {}
