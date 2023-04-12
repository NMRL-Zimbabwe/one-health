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
  description: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewDemographicCoding = {
  name: 'vertical Cotton',
  code: 'copying haptic',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
