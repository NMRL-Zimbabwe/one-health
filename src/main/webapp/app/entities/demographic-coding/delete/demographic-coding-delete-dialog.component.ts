import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemographicCoding } from '../demographic-coding.model';
import { DemographicCodingService } from '../service/demographic-coding.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './demographic-coding-delete-dialog.component.html',
})
export class DemographicCodingDeleteDialogComponent {
  demographicCoding?: IDemographicCoding;

  constructor(protected demographicCodingService: DemographicCodingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demographicCodingService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
