import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IClientAddress } from '../client-address.model';
import { ClientAddressService } from '../service/client-address.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './client-address-delete-dialog.component.html',
})
export class ClientAddressDeleteDialogComponent {
  clientAddress?: IClientAddress;

  constructor(protected clientAddressService: ClientAddressService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.clientAddressService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
