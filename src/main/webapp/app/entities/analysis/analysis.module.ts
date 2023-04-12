import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AnalysisComponent } from './list/analysis.component';
import { AnalysisDetailComponent } from './detail/analysis-detail.component';
import { AnalysisUpdateComponent } from './update/analysis-update.component';
import { AnalysisDeleteDialogComponent } from './delete/analysis-delete-dialog.component';
import { AnalysisRoutingModule } from './route/analysis-routing.module';

@NgModule({
  imports: [SharedModule, AnalysisRoutingModule],
  declarations: [AnalysisComponent, AnalysisDetailComponent, AnalysisUpdateComponent, AnalysisDeleteDialogComponent],
})
export class AnalysisModule {}
