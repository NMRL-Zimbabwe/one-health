import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDemography, NewDemography } from '../demography.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDemography for edit and NewDemographyFormGroupInput for create.
 */
type DemographyFormGroupInput = IDemography | PartialWithRequiredKeyOf<NewDemography>;

type DemographyFormDefaults = Pick<NewDemography, 'id'>;

type DemographyFormGroupContent = {
  id: FormControl<IDemography['id'] | NewDemography['id']>;
  recordId: FormControl<IDemography['recordId']>;
  demographicCodingId: FormControl<IDemography['demographicCodingId']>;
  value: FormControl<IDemography['value']>;
};

export type DemographyFormGroup = FormGroup<DemographyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DemographyFormService {
  createDemographyFormGroup(demography: DemographyFormGroupInput = { id: null }): DemographyFormGroup {
    const demographyRawValue = {
      ...this.getFormDefaults(),
      ...demography,
    };
    return new FormGroup<DemographyFormGroupContent>({
      id: new FormControl(
        { value: demographyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      recordId: new FormControl(demographyRawValue.recordId, {
        validators: [Validators.required],
      }),
      demographicCodingId: new FormControl(demographyRawValue.demographicCodingId, {
        validators: [Validators.required],
      }),
      value: new FormControl(demographyRawValue.value, {
        validators: [Validators.required],
      }),
    });
  }

  getDemography(form: DemographyFormGroup): IDemography | NewDemography {
    return form.getRawValue() as IDemography | NewDemography;
  }

  resetForm(form: DemographyFormGroup, demography: DemographyFormGroupInput): void {
    const demographyRawValue = { ...this.getFormDefaults(), ...demography };
    form.reset(
      {
        ...demographyRawValue,
        id: { value: demographyRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DemographyFormDefaults {
    return {
      id: null,
    };
  }
}
