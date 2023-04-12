import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAntibiotic } from '../antibiotic.model';
import { AntibioticService } from '../service/antibiotic.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './antibiotic-delete-dialog.component.html',
})
export class AntibioticDeleteDialogComponent {
  antibiotic?: IAntibiotic;

  constructor(protected antibioticService: AntibioticService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.antibioticService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
