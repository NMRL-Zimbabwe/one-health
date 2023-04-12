import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SectorFormService, SectorFormGroup } from './sector-form.service';
import { ISector } from '../sector.model';
import { SectorService } from '../service/sector.service';

@Component({
  selector: 'jhi-sector-update',
  templateUrl: './sector-update.component.html',
})
export class SectorUpdateComponent implements OnInit {
  isSaving = false;
  sector: ISector | null = null;

  editForm: SectorFormGroup = this.sectorFormService.createSectorFormGroup();

  constructor(
    protected sectorService: SectorService,
    protected sectorFormService: SectorFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sector }) => {
      this.sector = sector;
      if (sector) {
        this.updateForm(sector);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sector = this.sectorFormService.getSector(this.editForm);
    if (sector.id !== null) {
      this.subscribeToSaveResponse(this.sectorService.update(sector));
    } else {
      this.subscribeToSaveResponse(this.sectorService.create(sector));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISector>>): void {
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

  protected updateForm(sector: ISector): void {
    this.sector = sector;
    this.sectorFormService.resetForm(this.editForm, sector);
  }
}
