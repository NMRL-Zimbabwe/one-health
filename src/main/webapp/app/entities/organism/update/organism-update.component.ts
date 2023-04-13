import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { OrganismFormService, OrganismFormGroup } from './organism-form.service';
import { IOrganism } from '../organism.model';
import { OrganismService } from '../service/organism.service';

@Component({
  selector: 'jhi-organism-update',
  templateUrl: './organism-update.component.html',
})
export class OrganismUpdateComponent implements OnInit {
  isSaving = false;
  organism: IOrganism | null = null;

  editForm: OrganismFormGroup = this.organismFormService.createOrganismFormGroup();

  constructor(
    protected organismService: OrganismService,
    protected organismFormService: OrganismFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organism }) => {
      this.organism = organism;
      if (organism) {
        this.updateForm(organism);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organism = this.organismFormService.getOrganism(this.editForm);
    if (organism.id !== null) {
      this.subscribeToSaveResponse(this.organismService.update(organism));
    } else {
      this.subscribeToSaveResponse(this.organismService.create(organism));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganism>>): void {
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

  protected updateForm(organism: IOrganism): void {
    this.organism = organism;
    this.organismFormService.resetForm(this.editForm, organism);
  }
}
