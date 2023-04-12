import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISampleType, NewSampleType } from '../sample-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISampleType for edit and NewSampleTypeFormGroupInput for create.
 */
type SampleTypeFormGroupInput = ISampleType | PartialWithRequiredKeyOf<NewSampleType>;

type SampleTypeFormDefaults = Pick<NewSampleType, 'id'>;

type SampleTypeFormGroupContent = {
  id: FormControl<ISampleType['id'] | NewSampleType['id']>;
  name: FormControl<ISampleType['name']>;
  code: FormControl<ISampleType['code']>;
};

export type SampleTypeFormGroup = FormGroup<SampleTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SampleTypeFormService {
  createSampleTypeFormGroup(sampleType: SampleTypeFormGroupInput = { id: null }): SampleTypeFormGroup {
    const sampleTypeRawValue = {
      ...this.getFormDefaults(),
      ...sampleType,
    };
    return new FormGroup<SampleTypeFormGroupContent>({
      id: new FormControl(
        { value: sampleTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(sampleTypeRawValue.name, {
        validators: [Validators.required],
      }),
      code: new FormControl(sampleTypeRawValue.code),
    });
  }

  getSampleType(form: SampleTypeFormGroup): ISampleType | NewSampleType {
    return form.getRawValue() as ISampleType | NewSampleType;
  }

  resetForm(form: SampleTypeFormGroup, sampleType: SampleTypeFormGroupInput): void {
    const sampleTypeRawValue = { ...this.getFormDefaults(), ...sampleType };
    form.reset(
      {
        ...sampleTypeRawValue,
        id: { value: sampleTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SampleTypeFormDefaults {
    return {
      id: null,
    };
  }
}
