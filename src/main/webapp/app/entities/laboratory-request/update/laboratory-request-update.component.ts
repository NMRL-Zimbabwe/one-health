import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LaboratoryRequestFormService, LaboratoryRequestFormGroup } from './laboratory-request-form.service';
import { ILaboratoryRequest } from '../laboratory-request.model';
import { LaboratoryRequestService } from '../service/laboratory-request.service';

@Component({
  selector: 'jhi-laboratory-request-update',
  templateUrl: './laboratory-request-update.component.html',
})
export class LaboratoryRequestUpdateComponent implements OnInit {
  isSaving = false;
  laboratoryRequest: ILaboratoryRequest | null = null;

  editForm: LaboratoryRequestFormGroup = this.laboratoryRequestFormService.createLaboratoryRequestFormGroup();

  constructor(
    protected laboratoryRequestService: LaboratoryRequestService,
    protected laboratoryRequestFormService: LaboratoryRequestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ laboratoryRequest }) => {
      this.laboratoryRequest = laboratoryRequest;
      if (laboratoryRequest) {
        this.updateForm(laboratoryRequest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const laboratoryRequest = this.laboratoryRequestFormService.getLaboratoryRequest(this.editForm);
    if (laboratoryRequest.id !== null) {
      this.subscribeToSaveResponse(this.laboratoryRequestService.update(laboratoryRequest));
    } else {
      this.subscribeToSaveResponse(this.laboratoryRequestService.create(laboratoryRequest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILaboratoryRequest>>): void {
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

  protected updateForm(laboratoryRequest: ILaboratoryRequest): void {
    this.laboratoryRequest = laboratoryRequest;
    this.laboratoryRequestFormService.resetForm(this.editForm, laboratoryRequest);
  }
}
