import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DemographyFormService, DemographyFormGroup } from './demography-form.service';
import { IDemography } from '../demography.model';
import { DemographyService } from '../service/demography.service';

@Component({
  selector: 'jhi-demography-update',
  templateUrl: './demography-update.component.html',
})
export class DemographyUpdateComponent implements OnInit {
  isSaving = false;
  demography: IDemography | null = null;

  editForm: DemographyFormGroup = this.demographyFormService.createDemographyFormGroup();

  constructor(
    protected demographyService: DemographyService,
    protected demographyFormService: DemographyFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ demography }) => {
      this.demography = demography;
      if (demography) {
        this.updateForm(demography);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const demography = this.demographyFormService.getDemography(this.editForm);
    if (demography.id !== null) {
      this.subscribeToSaveResponse(this.demographyService.update(demography));
    } else {
      this.subscribeToSaveResponse(this.demographyService.create(demography));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDemography>>): void {
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

  protected updateForm(demography: IDemography): void {
    this.demography = demography;
    this.demographyFormService.resetForm(this.editForm, demography);
  }
}
