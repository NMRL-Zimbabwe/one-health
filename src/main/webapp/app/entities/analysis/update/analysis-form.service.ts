import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnalysis, NewAnalysis } from '../analysis.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnalysis for edit and NewAnalysisFormGroupInput for create.
 */
type AnalysisFormGroupInput = IAnalysis | PartialWithRequiredKeyOf<NewAnalysis>;

type AnalysisFormDefaults = Pick<NewAnalysis, 'id'>;

type AnalysisFormGroupContent = {
  id: FormControl<IAnalysis['id'] | NewAnalysis['id']>;
  sampleId: FormControl<IAnalysis['sampleId']>;
  analysisServiceId: FormControl<IAnalysis['analysisServiceId']>;
  result: FormControl<IAnalysis['result']>;
  dateResulted: FormControl<IAnalysis['dateResulted']>;
};

export type AnalysisFormGroup = FormGroup<AnalysisFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnalysisFormService {
  createAnalysisFormGroup(analysis: AnalysisFormGroupInput = { id: null }): AnalysisFormGroup {
    const analysisRawValue = {
      ...this.getFormDefaults(),
      ...analysis,
    };
    return new FormGroup<AnalysisFormGroupContent>({
      id: new FormControl(
        { value: analysisRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      sampleId: new FormControl(analysisRawValue.sampleId, {
        validators: [Validators.required],
      }),
      analysisServiceId: new FormControl(analysisRawValue.analysisServiceId, {
        validators: [Validators.required],
      }),
      result: new FormControl(analysisRawValue.result),
      dateResulted: new FormControl(analysisRawValue.dateResulted),
    });
  }

  getAnalysis(form: AnalysisFormGroup): IAnalysis | NewAnalysis {
    return form.getRawValue() as IAnalysis | NewAnalysis;
  }

  resetForm(form: AnalysisFormGroup, analysis: AnalysisFormGroupInput): void {
    const analysisRawValue = { ...this.getFormDefaults(), ...analysis };
    form.reset(
      {
        ...analysisRawValue,
        id: { value: analysisRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AnalysisFormDefaults {
    return {
      id: null,
    };
  }
}
