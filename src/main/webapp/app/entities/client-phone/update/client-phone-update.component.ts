import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ClientPhoneFormService, ClientPhoneFormGroup } from './client-phone-form.service';
import { IClientPhone } from '../client-phone.model';
import { ClientPhoneService } from '../service/client-phone.service';

@Component({
  selector: 'jhi-client-phone-update',
  templateUrl: './client-phone-update.component.html',
})
export class ClientPhoneUpdateComponent implements OnInit {
  isSaving = false;
  clientPhone: IClientPhone | null = null;

  editForm: ClientPhoneFormGroup = this.clientPhoneFormService.createClientPhoneFormGroup();

  constructor(
    protected clientPhoneService: ClientPhoneService,
    protected clientPhoneFormService: ClientPhoneFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clientPhone }) => {
      this.clientPhone = clientPhone;
      if (clientPhone) {
        this.updateForm(clientPhone);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const clientPhone = this.clientPhoneFormService.getClientPhone(this.editForm);
    if (clientPhone.id !== null) {
      this.subscribeToSaveResponse(this.clientPhoneService.update(clientPhone));
    } else {
      this.subscribeToSaveResponse(this.clientPhoneService.create(clientPhone));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClientPhone>>): void {
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

  protected updateForm(clientPhone: IClientPhone): void {
    this.clientPhone = clientPhone;
    this.clientPhoneFormService.resetForm(this.editForm, clientPhone);
  }
}
