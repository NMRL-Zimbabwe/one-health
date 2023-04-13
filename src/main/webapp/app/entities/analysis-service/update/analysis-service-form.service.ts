import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnalysisService, NewAnalysisService } from '../analysis-service.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnalysisService for edit and NewAnalysisServiceFormGroupInput for create.
 */
type AnalysisServiceFormGroupInput = IAnalysisService | PartialWithRequiredKeyOf<NewAnalysisService>;

type AnalysisServiceFormDefaults = Pick<NewAnalysisService, 'id'>;

type AnalysisServiceFormGroupContent = {
  id: FormControl<IAnalysisService['id'] | NewAnalysisService['id']>;
  name: FormControl<IAnalysisService['name']>;
  code: FormControl<IAnalysisService['code']>;
};

export type AnalysisServiceFormGroup = FormGroup<AnalysisServiceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnalysisServiceFormService {
  createAnalysisServiceFormGroup(analysisService: AnalysisServiceFormGroupInput = { id: null }): AnalysisServiceFormGroup {
    const analysisServiceRawValue = {
      ...this.getFormDefaults(),
      ...analysisService,
    };
    return new FormGroup<AnalysisServiceFormGroupContent>({
      id: new FormControl(
        { value: analysisServiceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(analysisServiceRawValue.name, {
        validators: [Validators.required],
      }),
      code: new FormControl(analysisServiceRawValue.code),
    });
  }

  getAnalysisService(form: AnalysisServiceFormGroup): IAnalysisService | NewAnalysisService {
    return form.getRawValue() as IAnalysisService | NewAnalysisService;
  }

  resetForm(form: AnalysisServiceFormGroup, analysisService: AnalysisServiceFormGroupInput): void {
    const analysisServiceRawValue = { ...this.getFormDefaults(), ...analysisService };
    form.reset(
      {
        ...analysisServiceRawValue,
        id: { value: analysisServiceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AnalysisServiceFormDefaults {
    return {
      id: null,
    };
  }
}
