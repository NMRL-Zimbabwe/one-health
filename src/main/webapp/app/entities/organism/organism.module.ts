import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrganismComponent } from './list/organism.component';
import { OrganismDetailComponent } from './detail/organism-detail.component';
import { OrganismUpdateComponent } from './update/organism-update.component';
import { OrganismDeleteDialogComponent } from './delete/organism-delete-dialog.component';
import { OrganismRoutingModule } from './route/organism-routing.module';

@NgModule({
  imports: [SharedModule, OrganismRoutingModule],
  declarations: [OrganismComponent, OrganismDetailComponent, OrganismUpdateComponent, OrganismDeleteDialogComponent],
})
export class OrganismModule {}
