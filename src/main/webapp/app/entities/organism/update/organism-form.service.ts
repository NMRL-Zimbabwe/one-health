import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOrganism, NewOrganism } from '../organism.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrganism for edit and NewOrganismFormGroupInput for create.
 */
type OrganismFormGroupInput = IOrganism | PartialWithRequiredKeyOf<NewOrganism>;

type OrganismFormDefaults = Pick<NewOrganism, 'id'>;

type OrganismFormGroupContent = {
  id: FormControl<IOrganism['id'] | NewOrganism['id']>;
  name: FormControl<IOrganism['name']>;
  code: FormControl<IOrganism['code']>;
};

export type OrganismFormGroup = FormGroup<OrganismFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrganismFormService {
  createOrganismFormGroup(organism: OrganismFormGroupInput = { id: null }): OrganismFormGroup {
    const organismRawValue = {
      ...this.getFormDefaults(),
      ...organism,
    };
    return new FormGroup<OrganismFormGroupContent>({
      id: new FormControl(
        { value: organismRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(organismRawValue.name, {
        validators: [Validators.required],
      }),
      code: new FormControl(organismRawValue.code),
    });
  }

  getOrganism(form: OrganismFormGroup): IOrganism | NewOrganism {
    return form.getRawValue() as IOrganism | NewOrganism;
  }

  resetForm(form: OrganismFormGroup, organism: OrganismFormGroupInput): void {
    const organismRawValue = { ...this.getFormDefaults(), ...organism };
    form.reset(
      {
        ...organismRawValue,
        id: { value: organismRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OrganismFormDefaults {
    return {
      id: null,
    };
  }
}
