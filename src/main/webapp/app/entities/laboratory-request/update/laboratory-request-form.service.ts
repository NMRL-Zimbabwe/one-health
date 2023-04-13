import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILaboratoryRequest, NewLaboratoryRequest } from '../laboratory-request.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILaboratoryRequest for edit and NewLaboratoryRequestFormGroupInput for create.
 */
type LaboratoryRequestFormGroupInput = ILaboratoryRequest | PartialWithRequiredKeyOf<NewLaboratoryRequest>;

type LaboratoryRequestFormDefaults = Pick<NewLaboratoryRequest, 'id'>;

type LaboratoryRequestFormGroupContent = {
  id: FormControl<ILaboratoryRequest['id'] | NewLaboratoryRequest['id']>;
  sampleId: FormControl<ILaboratoryRequest['sampleId']>;
  sampleTypeId: FormControl<ILaboratoryRequest['sampleTypeId']>;
  dateCollected: FormControl<ILaboratoryRequest['dateCollected']>;
  dateReceived: FormControl<ILaboratoryRequest['dateReceived']>;
  sampleCondition: FormControl<ILaboratoryRequest['sampleCondition']>;
  clientId: FormControl<ILaboratoryRequest['clientId']>;
  priority: FormControl<ILaboratoryRequest['priority']>;
  status: FormControl<ILaboratoryRequest['status']>;
  remarks: FormControl<ILaboratoryRequest['remarks']>;
  locationId: FormControl<ILaboratoryRequest['locationId']>;
  sectorId: FormControl<ILaboratoryRequest['sectorId']>;
  districtId: FormControl<ILaboratoryRequest['districtId']>;
  procinceId: FormControl<ILaboratoryRequest['procinceId']>;
};

export type LaboratoryRequestFormGroup = FormGroup<LaboratoryRequestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LaboratoryRequestFormService {
  createLaboratoryRequestFormGroup(laboratoryRequest: LaboratoryRequestFormGroupInput = { id: null }): LaboratoryRequestFormGroup {
    const laboratoryRequestRawValue = {
      ...this.getFormDefaults(),
      ...laboratoryRequest,
    };
    return new FormGroup<LaboratoryRequestFormGroupContent>({
      id: new FormControl(
        { value: laboratoryRequestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      sampleId: new FormControl(laboratoryRequestRawValue.sampleId, {
        validators: [Validators.required],
      }),
      sampleTypeId: new FormControl(laboratoryRequestRawValue.sampleTypeId, {
        validators: [Validators.required],
      }),
      dateCollected: new FormControl(laboratoryRequestRawValue.dateCollected),
      dateReceived: new FormControl(laboratoryRequestRawValue.dateReceived),
      sampleCondition: new FormControl(laboratoryRequestRawValue.sampleCondition),
      clientId: new FormControl(laboratoryRequestRawValue.clientId),
      priority: new FormControl(laboratoryRequestRawValue.priority),
      status: new FormControl(laboratoryRequestRawValue.status),
      remarks: new FormControl(laboratoryRequestRawValue.remarks),
      locationId: new FormControl(laboratoryRequestRawValue.locationId),
      sectorId: new FormControl(laboratoryRequestRawValue.sectorId),
      districtId: new FormControl(laboratoryRequestRawValue.districtId),
      procinceId: new FormControl(laboratoryRequestRawValue.procinceId),
    });
  }

  getLaboratoryRequest(form: LaboratoryRequestFormGroup): ILaboratoryRequest | NewLaboratoryRequest {
    return form.getRawValue() as ILaboratoryRequest | NewLaboratoryRequest;
  }

  resetForm(form: LaboratoryRequestFormGroup, laboratoryRequest: LaboratoryRequestFormGroupInput): void {
    const laboratoryRequestRawValue = { ...this.getFormDefaults(), ...laboratoryRequest };
    form.reset(
      {
        ...laboratoryRequestRawValue,
        id: { value: laboratoryRequestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LaboratoryRequestFormDefaults {
    return {
      id: null,
    };
  }
}
