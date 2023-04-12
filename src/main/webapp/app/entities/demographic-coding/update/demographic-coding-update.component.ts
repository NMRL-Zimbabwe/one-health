import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DemographicCodingFormService, DemographicCodingFormGroup } from './demographic-coding-form.service';
import { IDemographicCoding } from '../demographic-coding.model';
import { DemographicCodingService } from '../service/demographic-coding.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-demographic-coding-update',
  templateUrl: './demographic-coding-update.component.html',
})
export class DemographicCodingUpdateComponent implements OnInit {
  isSaving = false;
  demographicCoding: IDemographicCoding | null = null;

  editForm: DemographicCodingFormGroup = this.demographicCodingFormService.createDemographicCodingFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
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

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('oneHealthApp.error', { ...err, key: 'error.file.' + err.key })),
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
