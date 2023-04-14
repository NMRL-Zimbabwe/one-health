import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAntibioticClass, NewAntibioticClass } from '../antibiotic-class.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAntibioticClass for edit and NewAntibioticClassFormGroupInput for create.
 */
type AntibioticClassFormGroupInput = IAntibioticClass | PartialWithRequiredKeyOf<NewAntibioticClass>;

type AntibioticClassFormDefaults = Pick<NewAntibioticClass, 'id'>;

type AntibioticClassFormGroupContent = {
  id: FormControl<IAntibioticClass['id'] | NewAntibioticClass['id']>;
  name: FormControl<IAntibioticClass['name']>;
  description: FormControl<IAntibioticClass['description']>;
};

export type AntibioticClassFormGroup = FormGroup<AntibioticClassFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AntibioticClassFormService {
  createAntibioticClassFormGroup(antibioticClass: AntibioticClassFormGroupInput = { id: null }): AntibioticClassFormGroup {
    const antibioticClassRawValue = {
      ...this.getFormDefaults(),
      ...antibioticClass,
    };
    return new FormGroup<AntibioticClassFormGroupContent>({
      id: new FormControl(
        { value: antibioticClassRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(antibioticClassRawValue.name),
      description: new FormControl(antibioticClassRawValue.description),
    });
  }

  getAntibioticClass(form: AntibioticClassFormGroup): IAntibioticClass | NewAntibioticClass {
    return form.getRawValue() as IAntibioticClass | NewAntibioticClass;
  }

  resetForm(form: AntibioticClassFormGroup, antibioticClass: AntibioticClassFormGroupInput): void {
    const antibioticClassRawValue = { ...this.getFormDefaults(), ...antibioticClass };
    form.reset(
      {
        ...antibioticClassRawValue,
        id: { value: antibioticClassRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AntibioticClassFormDefaults {
    return {
      id: null,
    };
  }
}
