import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDemographicCoding, NewDemographicCoding } from '../demographic-coding.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDemographicCoding for edit and NewDemographicCodingFormGroupInput for create.
 */
type DemographicCodingFormGroupInput = IDemographicCoding | PartialWithRequiredKeyOf<NewDemographicCoding>;

type DemographicCodingFormDefaults = Pick<NewDemographicCoding, 'id'>;

type DemographicCodingFormGroupContent = {
  id: FormControl<IDemographicCoding['id'] | NewDemographicCoding['id']>;
  name: FormControl<IDemographicCoding['name']>;
  code: FormControl<IDemographicCoding['code']>;
  description: FormControl<IDemographicCoding['description']>;
};

export type DemographicCodingFormGroup = FormGroup<DemographicCodingFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DemographicCodingFormService {
  createDemographicCodingFormGroup(demographicCoding: DemographicCodingFormGroupInput = { id: null }): DemographicCodingFormGroup {
    const demographicCodingRawValue = {
      ...this.getFormDefaults(),
      ...demographicCoding,
    };
    return new FormGroup<DemographicCodingFormGroupContent>({
      id: new FormControl(
        { value: demographicCodingRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(demographicCodingRawValue.name, {
        validators: [Validators.required],
      }),
      code: new FormControl(demographicCodingRawValue.code, {
        validators: [Validators.required],
      }),
      description: new FormControl(demographicCodingRawValue.description),
    });
  }

  getDemographicCoding(form: DemographicCodingFormGroup): IDemographicCoding | NewDemographicCoding {
    return form.getRawValue() as IDemographicCoding | NewDemographicCoding;
  }

  resetForm(form: DemographicCodingFormGroup, demographicCoding: DemographicCodingFormGroupInput): void {
    const demographicCodingRawValue = { ...this.getFormDefaults(), ...demographicCoding };
    form.reset(
      {
        ...demographicCodingRawValue,
        id: { value: demographicCodingRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DemographicCodingFormDefaults {
    return {
      id: null,
    };
  }
}
