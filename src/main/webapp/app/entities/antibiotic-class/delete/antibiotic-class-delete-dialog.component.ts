import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAntibioticClass } from '../antibiotic-class.model';
import { AntibioticClassService } from '../service/antibiotic-class.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './antibiotic-class-delete-dialog.component.html',
})
export class AntibioticClassDeleteDialogComponent {
  antibioticClass?: IAntibioticClass;

  constructor(protected antibioticClassService: AntibioticClassService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.antibioticClassService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
