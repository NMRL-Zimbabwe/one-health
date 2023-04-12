import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { OrgnanismFormService, OrgnanismFormGroup } from './orgnanism-form.service';
import { IOrgnanism } from '../orgnanism.model';
import { OrgnanismService } from '../service/orgnanism.service';

@Component({
  selector: 'jhi-orgnanism-update',
  templateUrl: './orgnanism-update.component.html',
})
export class OrgnanismUpdateComponent implements OnInit {
  isSaving = false;
  orgnanism: IOrgnanism | null = null;

  editForm: OrgnanismFormGroup = this.orgnanismFormService.createOrgnanismFormGroup();

  constructor(
    protected orgnanismService: OrgnanismService,
    protected orgnanismFormService: OrgnanismFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orgnanism }) => {
      this.orgnanism = orgnanism;
      if (orgnanism) {
        this.updateForm(orgnanism);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orgnanism = this.orgnanismFormService.getOrgnanism(this.editForm);
    if (orgnanism.id !== null) {
      this.subscribeToSaveResponse(this.orgnanismService.update(orgnanism));
    } else {
      this.subscribeToSaveResponse(this.orgnanismService.create(orgnanism));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrgnanism>>): void {
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

  protected updateForm(orgnanism: IOrgnanism): void {
    this.orgnanism = orgnanism;
    this.orgnanismFormService.resetForm(this.editForm, orgnanism);
  }
}
