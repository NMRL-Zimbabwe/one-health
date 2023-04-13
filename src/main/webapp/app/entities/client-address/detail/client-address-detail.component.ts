import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClientAddress } from '../client-address.model';

@Component({
  selector: 'jhi-client-address-detail',
  templateUrl: './client-address-detail.component.html',
})
export class ClientAddressDetailComponent implements OnInit {
  clientAddress: IClientAddress | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clientAddress }) => {
      this.clientAddress = clientAddress;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
