import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClientPhone } from '../client-phone.model';

@Component({
  selector: 'jhi-client-phone-detail',
  templateUrl: './client-phone-detail.component.html',
})
export class ClientPhoneDetailComponent implements OnInit {
  clientPhone: IClientPhone | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clientPhone }) => {
      this.clientPhone = clientPhone;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
