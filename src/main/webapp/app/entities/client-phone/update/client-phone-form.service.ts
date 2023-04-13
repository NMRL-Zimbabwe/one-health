import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IClientPhone, NewClientPhone } from '../client-phone.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IClientPhone for edit and NewClientPhoneFormGroupInput for create.
 */
type ClientPhoneFormGroupInput = IClientPhone | PartialWithRequiredKeyOf<NewClientPhone>;

type ClientPhoneFormDefaults = Pick<NewClientPhone, 'id'>;

type ClientPhoneFormGroupContent = {
  id: FormControl<IClientPhone['id'] | NewClientPhone['id']>;
  number: FormControl<IClientPhone['number']>;
  clientId: FormControl<IClientPhone['clientId']>;
};

export type ClientPhoneFormGroup = FormGroup<ClientPhoneFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ClientPhoneFormService {
  createClientPhoneFormGroup(clientPhone: ClientPhoneFormGroupInput = { id: null }): ClientPhoneFormGroup {
    const clientPhoneRawValue = {
      ...this.getFormDefaults(),
      ...clientPhone,
    };
    return new FormGroup<ClientPhoneFormGroupContent>({
      id: new FormControl(
        { value: clientPhoneRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      number: new FormControl(clientPhoneRawValue.number),
      clientId: new FormControl(clientPhoneRawValue.clientId),
    });
  }

  getClientPhone(form: ClientPhoneFormGroup): IClientPhone | NewClientPhone {
    return form.getRawValue() as IClientPhone | NewClientPhone;
  }

  resetForm(form: ClientPhoneFormGroup, clientPhone: ClientPhoneFormGroupInput): void {
    const clientPhoneRawValue = { ...this.getFormDefaults(), ...clientPhone };
    form.reset(
      {
        ...clientPhoneRawValue,
        id: { value: clientPhoneRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ClientPhoneFormDefaults {
    return {
      id: null,
    };
  }
}
