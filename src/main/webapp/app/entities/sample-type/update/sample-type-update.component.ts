import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SampleTypeFormService, SampleTypeFormGroup } from './sample-type-form.service';
import { ISampleType } from '../sample-type.model';
import { SampleTypeService } from '../service/sample-type.service';

@Component({
  selector: 'jhi-sample-type-update',
  templateUrl: './sample-type-update.component.html',
})
export class SampleTypeUpdateComponent implements OnInit {
  isSaving = false;
  sampleType: ISampleType | null = null;

  editForm: SampleTypeFormGroup = this.sampleTypeFormService.createSampleTypeFormGroup();

  constructor(
    protected sampleTypeService: SampleTypeService,
    protected sampleTypeFormService: SampleTypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sampleType }) => {
      this.sampleType = sampleType;
      if (sampleType) {
        this.updateForm(sampleType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sampleType = this.sampleTypeFormService.getSampleType(this.editForm);
    if (sampleType.id !== null) {
      this.subscribeToSaveResponse(this.sampleTypeService.update(sampleType));
    } else {
      this.subscribeToSaveResponse(this.sampleTypeService.create(sampleType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISampleType>>): void {
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

  protected updateForm(sampleType: ISampleType): void {
    this.sampleType = sampleType;
    this.sampleTypeFormService.resetForm(this.editForm, sampleType);
  }
}
