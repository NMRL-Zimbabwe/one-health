import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IClientPhone } from '../client-phone.model';
import { ClientPhoneService } from '../service/client-phone.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './client-phone-delete-dialog.component.html',
})
export class ClientPhoneDeleteDialogComponent {
  clientPhone?: IClientPhone;

  constructor(protected clientPhoneService: ClientPhoneService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.clientPhoneService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
