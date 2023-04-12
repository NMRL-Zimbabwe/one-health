import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOrgnanism, NewOrgnanism } from '../orgnanism.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrgnanism for edit and NewOrgnanismFormGroupInput for create.
 */
type OrgnanismFormGroupInput = IOrgnanism | PartialWithRequiredKeyOf<NewOrgnanism>;

type OrgnanismFormDefaults = Pick<NewOrgnanism, 'id'>;

type OrgnanismFormGroupContent = {
  id: FormControl<IOrgnanism['id'] | NewOrgnanism['id']>;
  name: FormControl<IOrgnanism['name']>;
  code: FormControl<IOrgnanism['code']>;
};

export type OrgnanismFormGroup = FormGroup<OrgnanismFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrgnanismFormService {
  createOrgnanismFormGroup(orgnanism: OrgnanismFormGroupInput = { id: null }): OrgnanismFormGroup {
    const orgnanismRawValue = {
      ...this.getFormDefaults(),
      ...orgnanism,
    };
    return new FormGroup<OrgnanismFormGroupContent>({
      id: new FormControl(
        { value: orgnanismRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(orgnanismRawValue.name, {
        validators: [Validators.required],
      }),
      code: new FormControl(orgnanismRawValue.code),
    });
  }

  getOrgnanism(form: OrgnanismFormGroup): IOrgnanism | NewOrgnanism {
    return form.getRawValue() as IOrgnanism | NewOrgnanism;
  }

  resetForm(form: OrgnanismFormGroup, orgnanism: OrgnanismFormGroupInput): void {
    const orgnanismRawValue = { ...this.getFormDefaults(), ...orgnanism };
    form.reset(
      {
        ...orgnanismRawValue,
        id: { value: orgnanismRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OrgnanismFormDefaults {
    return {
      id: null,
    };
  }
}
