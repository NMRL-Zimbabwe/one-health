import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILaboratoryRequest } from '../laboratory-request.model';
import { LaboratoryRequestService } from '../service/laboratory-request.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './laboratory-request-delete-dialog.component.html',
})
export class LaboratoryRequestDeleteDialogComponent {
  laboratoryRequest?: ILaboratoryRequest;

  constructor(protected laboratoryRequestService: LaboratoryRequestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.laboratoryRequestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
