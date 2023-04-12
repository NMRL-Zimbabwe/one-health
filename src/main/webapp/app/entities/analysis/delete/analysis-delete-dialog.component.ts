import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnalysis } from '../analysis.model';
import { AnalysisService } from '../service/analysis.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './analysis-delete-dialog.component.html',
})
export class AnalysisDeleteDialogComponent {
  analysis?: IAnalysis;

  constructor(protected analysisService: AnalysisService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.analysisService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
