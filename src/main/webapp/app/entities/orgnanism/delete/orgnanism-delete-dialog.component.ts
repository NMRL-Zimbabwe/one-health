import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrgnanism } from '../orgnanism.model';
import { OrgnanismService } from '../service/orgnanism.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './orgnanism-delete-dialog.component.html',
})
export class OrgnanismDeleteDialogComponent {
  orgnanism?: IOrgnanism;

  constructor(protected orgnanismService: OrgnanismService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orgnanismService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
