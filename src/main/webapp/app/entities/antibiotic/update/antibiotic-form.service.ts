import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAntibiotic, NewAntibiotic } from '../antibiotic.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAntibiotic for edit and NewAntibioticFormGroupInput for create.
 */
type AntibioticFormGroupInput = IAntibiotic | PartialWithRequiredKeyOf<NewAntibiotic>;

type AntibioticFormDefaults = Pick<NewAntibiotic, 'id'>;

type AntibioticFormGroupContent = {
  id: FormControl<IAntibiotic['id'] | NewAntibiotic['id']>;
  name: FormControl<IAntibiotic['name']>;
  code: FormControl<IAntibiotic['code']>;
  status: FormControl<IAntibiotic['status']>;
  description: FormControl<IAntibiotic['description']>;
  classId: FormControl<IAntibiotic['classId']>;
};

export type AntibioticFormGroup = FormGroup<AntibioticFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AntibioticFormService {
  createAntibioticFormGroup(antibiotic: AntibioticFormGroupInput = { id: null }): AntibioticFormGroup {
    const antibioticRawValue = {
      ...this.getFormDefaults(),
      ...antibiotic,
    };
    return new FormGroup<AntibioticFormGroupContent>({
      id: new FormControl(
        { value: antibioticRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(antibioticRawValue.name, {
        validators: [Validators.required],
      }),
      code: new FormControl(antibioticRawValue.code),
      status: new FormControl(antibioticRawValue.status),
      description: new FormControl(antibioticRawValue.description),
      classId: new FormControl(antibioticRawValue.classId, {
        validators: [Validators.required],
      }),
    });
  }

  getAntibiotic(form: AntibioticFormGroup): IAntibiotic | NewAntibiotic {
    return form.getRawValue() as IAntibiotic | NewAntibiotic;
  }

  resetForm(form: AntibioticFormGroup, antibiotic: AntibioticFormGroupInput): void {
    const antibioticRawValue = { ...this.getFormDefaults(), ...antibiotic };
    form.reset(
      {
        ...antibioticRawValue,
        id: { value: antibioticRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AntibioticFormDefaults {
    return {
      id: null,
    };
  }
}
