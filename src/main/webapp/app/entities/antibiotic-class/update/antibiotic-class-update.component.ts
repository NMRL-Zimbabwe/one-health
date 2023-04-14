import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AntibioticClassFormService, AntibioticClassFormGroup } from './antibiotic-class-form.service';
import { IAntibioticClass } from '../antibiotic-class.model';
import { AntibioticClassService } from '../service/antibiotic-class.service';

@Component({
  selector: 'jhi-antibiotic-class-update',
  templateUrl: './antibiotic-class-update.component.html',
})
export class AntibioticClassUpdateComponent implements OnInit {
  isSaving = false;
  antibioticClass: IAntibioticClass | null = null;

  editForm: AntibioticClassFormGroup = this.antibioticClassFormService.createAntibioticClassFormGroup();

  constructor(
    protected antibioticClassService: AntibioticClassService,
    protected antibioticClassFormService: AntibioticClassFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ antibioticClass }) => {
      this.antibioticClass = antibioticClass;
      if (antibioticClass) {
        this.updateForm(antibioticClass);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const antibioticClass = this.antibioticClassFormService.getAntibioticClass(this.editForm);
    if (antibioticClass.id !== null) {
      this.subscribeToSaveResponse(this.antibioticClassService.update(antibioticClass));
    } else {
      this.subscribeToSaveResponse(this.antibioticClassService.create(antibioticClass));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAntibioticClass>>): void {
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

  protected updateForm(antibioticClass: IAntibioticClass): void {
    this.antibioticClass = antibioticClass;
    this.antibioticClassFormService.resetForm(this.editForm, antibioticClass);
  }
}
