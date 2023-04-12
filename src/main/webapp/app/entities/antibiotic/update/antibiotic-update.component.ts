import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AntibioticFormService, AntibioticFormGroup } from './antibiotic-form.service';
import { IAntibiotic } from '../antibiotic.model';
import { AntibioticService } from '../service/antibiotic.service';

@Component({
  selector: 'jhi-antibiotic-update',
  templateUrl: './antibiotic-update.component.html',
})
export class AntibioticUpdateComponent implements OnInit {
  isSaving = false;
  antibiotic: IAntibiotic | null = null;

  editForm: AntibioticFormGroup = this.antibioticFormService.createAntibioticFormGroup();

  constructor(
    protected antibioticService: AntibioticService,
    protected antibioticFormService: AntibioticFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ antibiotic }) => {
      this.antibiotic = antibiotic;
      if (antibiotic) {
        this.updateForm(antibiotic);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const antibiotic = this.antibioticFormService.getAntibiotic(this.editForm);
    if (antibiotic.id !== null) {
      this.subscribeToSaveResponse(this.antibioticService.update(antibiotic));
    } else {
      this.subscribeToSaveResponse(this.antibioticService.create(antibiotic));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAntibiotic>>): void {
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

  protected updateForm(antibiotic: IAntibiotic): void {
    this.antibiotic = antibiotic;
    this.antibioticFormService.resetForm(this.editForm, antibiotic);
  }
}
