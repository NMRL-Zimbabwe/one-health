import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISector, NewSector } from '../sector.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISector for edit and NewSectorFormGroupInput for create.
 */
type SectorFormGroupInput = ISector | PartialWithRequiredKeyOf<NewSector>;

type SectorFormDefaults = Pick<NewSector, 'id'>;

type SectorFormGroupContent = {
  id: FormControl<ISector['id'] | NewSector['id']>;
  name: FormControl<ISector['name']>;
  code: FormControl<ISector['code']>;
};

export type SectorFormGroup = FormGroup<SectorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SectorFormService {
  createSectorFormGroup(sector: SectorFormGroupInput = { id: null }): SectorFormGroup {
    const sectorRawValue = {
      ...this.getFormDefaults(),
      ...sector,
    };
    return new FormGroup<SectorFormGroupContent>({
      id: new FormControl(
        { value: sectorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(sectorRawValue.name, {
        validators: [Validators.required],
      }),
      code: new FormControl(sectorRawValue.code),
    });
  }

  getSector(form: SectorFormGroup): ISector | NewSector {
    return form.getRawValue() as ISector | NewSector;
  }

  resetForm(form: SectorFormGroup, sector: SectorFormGroupInput): void {
    const sectorRawValue = { ...this.getFormDefaults(), ...sector };
    form.reset(
      {
        ...sectorRawValue,
        id: { value: sectorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SectorFormDefaults {
    return {
      id: null,
    };
  }
}
