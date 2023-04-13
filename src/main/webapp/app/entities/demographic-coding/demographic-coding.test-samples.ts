import { IDemographicCoding, NewDemographicCoding } from './demographic-coding.model';

export const sampleWithRequiredData: IDemographicCoding = {
  id: 16231,
  name: 'Montana',
  code: 'Licensed',
};

export const sampleWithPartialData: IDemographicCoding = {
  id: 34724,
  name: 'Refined Polynesia',
  code: 'Account Planner Rustic',
};

export const sampleWithFullData: IDemographicCoding = {
  id: 4286,
  name: 'tangible grid-enabled',
  code: 'parsing navigate integrate',
  description: 'vertical Cotton',
};

export const sampleWithNewData: NewDemographicCoding = {
  name: 'copying haptic',
  code: 'Convertible',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
