import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ClientAddressFormService, ClientAddressFormGroup } from './client-address-form.service';
import { IClientAddress } from '../client-address.model';
import { ClientAddressService } from '../service/client-address.service';

@Component({
  selector: 'jhi-client-address-update',
  templateUrl: './client-address-update.component.html',
})
export class ClientAddressUpdateComponent implements OnInit {
  isSaving = false;
  clientAddress: IClientAddress | null = null;

  editForm: ClientAddressFormGroup = this.clientAddressFormService.createClientAddressFormGroup();

  constructor(
    protected clientAddressService: ClientAddressService,
    protected clientAddressFormService: ClientAddressFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clientAddress }) => {
      this.clientAddress = clientAddress;
      if (clientAddress) {
        this.updateForm(clientAddress);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const clientAddress = this.clientAddressFormService.getClientAddress(this.editForm);
    if (clientAddress.id !== null) {
      this.subscribeToSaveResponse(this.clientAddressService.update(clientAddress));
    } else {
      this.subscribeToSaveResponse(this.clientAddressService.create(clientAddress));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClientAddress>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(clientAddress: IClientAddress): void {
    this.clientAddress = clientAddress;
    this.clientAddressFormService.resetForm(this.editForm, clientAddress);
  }
}
