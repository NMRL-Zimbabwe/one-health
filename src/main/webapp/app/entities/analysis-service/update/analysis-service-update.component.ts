import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AnalysisServiceFormService, AnalysisServiceFormGroup } from './analysis-service-form.service';
import { IAnalysisService } from '../analysis-service.model';
import { AnalysisServiceService } from '../service/analysis-service.service';

@Component({
  selector: 'jhi-analysis-service-update',
  templateUrl: './analysis-service-update.component.html',
})
export class AnalysisServiceUpdateComponent implements OnInit {
  isSaving = false;
  analysisService: IAnalysisService | null = null;

  editForm: AnalysisServiceFormGroup = this.analysisServiceFormService.createAnalysisServiceFormGroup();

  constructor(
    protected analysisServiceService: AnalysisServiceService,
    protected analysisServiceFormService: AnalysisServiceFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analysisService }) => {
      this.analysisService = analysisService;
      if (analysisService) {
        this.updateForm(analysisService);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const analysisService = this.analysisServiceFormService.getAnalysisService(this.editForm);
    if (analysisService.id !== null) {
      this.subscribeToSaveResponse(this.analysisServiceService.update(analysisService));
    } else {
      this.subscribeToSaveResponse(this.analysisServiceService.create(analysisService));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnalysisService>>): void {
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

  protected updateForm(analysisService: IAnalysisService): void {
    this.analysisService = analysisService;
    this.analysisServiceFormService.resetForm(this.editForm, analysisService);
  }
}
