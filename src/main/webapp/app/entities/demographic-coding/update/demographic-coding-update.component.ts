import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DemographicCodingFormService, DemographicCodingFormGroup } from './demographic-coding-form.service';
import { IDemographicCoding } from '../demographic-coding.model';
import { DemographicCodingService } from '../service/demographic-coding.service';

@Component({
  selector: 'jhi-demographic-coding-update',
  templateUrl: './demographic-coding-update.component.html',
})
export class DemographicCodingUpdateComponent implements OnInit {
  isSaving = false;
  demographicCoding: IDemographicCoding | null = null;

  editForm: DemographicCodingFormGroup = this.demographicCodingFormService.createDemographicCodingFormGroup();

  constructor(
    protected demographicCodingService: DemographicCodingService,
    protected demographicCodingFormService: DemographicCodingFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demographicCoding }) => {
      this.demographicCoding = demographicCoding;
      if (demographicCoding) {
        this.updateForm(demographicCoding);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demographicCoding = this.demographicCodingFormService.getDemographicCoding(this.editForm);
    if (demographicCoding.id !== null) {
      this.subscribeToSaveResponse(this.demographicCodingService.update(demographicCoding));
    } else {
      this.subscribeToSaveResponse(this.demographicCodingService.create(demographicCoding));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemographicCoding>>): void {
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

  protected updateForm(demographicCoding: IDemographicCoding): void {
    this.demographicCoding = demographicCoding;
    this.demographicCodingFormService.resetForm(this.editForm, demographicCoding);
  }
}
