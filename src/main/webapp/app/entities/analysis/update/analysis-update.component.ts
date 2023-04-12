import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AnalysisFormService, AnalysisFormGroup } from './analysis-form.service';
import { IAnalysis } from '../analysis.model';
import { AnalysisService } from '../service/analysis.service';

@Component({
  selector: 'jhi-analysis-update',
  templateUrl: './analysis-update.component.html',
})
export class AnalysisUpdateComponent implements OnInit {
  isSaving = false;
  analysis: IAnalysis | null = null;

  editForm: AnalysisFormGroup = this.analysisFormService.createAnalysisFormGroup();

  constructor(
    protected analysisService: AnalysisService,
    protected analysisFormService: AnalysisFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ analysis }) => {
      this.analysis = analysis;
      if (analysis) {
        this.updateForm(analysis);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const analysis = this.analysisFormService.getAnalysis(this.editForm);
    if (analysis.id !== null) {
      this.subscribeToSaveResponse(this.analysisService.update(analysis));
    } else {
      this.subscribeToSaveResponse(this.analysisService.create(analysis));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnalysis>>): void {
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

  protected updateForm(analysis: IAnalysis): void {
    this.analysis = analysis;
    this.analysisFormService.resetForm(this.editForm, analysis);
  }
}
