import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AnalysisServiceComponent } from './list/analysis-service.component';
import { AnalysisServiceDetailComponent } from './detail/analysis-service-detail.component';
import { AnalysisServiceUpdateComponent } from './update/analysis-service-update.component';
import { AnalysisServiceDeleteDialogComponent } from './delete/analysis-service-delete-dialog.component';
import { AnalysisServiceRoutingModule } from './route/analysis-service-routing.module';

@NgModule({
  imports: [SharedModule, AnalysisServiceRoutingModule],
  declarations: [
    AnalysisServiceComponent,
    AnalysisServiceDetailComponent,
    AnalysisServiceUpdateComponent,
    AnalysisServiceDeleteDialogComponent,
  ],
})
export class AnalysisServiceModule {}
