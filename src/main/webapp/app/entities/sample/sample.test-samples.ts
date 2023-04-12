import { ISample, NewSample } from './sample.model';

export const sampleWithRequiredData: ISample = {
  id: 15510,
  sampleTypeId: 'Automotive Steel',
  analysisServiceId: 'payment object-oriented Rustic',
};

export const sampleWithPartialData: ISample = {
  id: 10479,
  sampleTypeId: 'Intelligent',
  analysisServiceId: 'relationships',
  longitude: 'Architect',
  latitude: 'Sports',
};

export const sampleWithFullData: ISample = {
  id: 14825,
  sampleTypeId: 'synthesizing Technician',
  analysisServiceId: 'partnerships',
  longitude: 'plum',
  latitude: 'sensor conglomeration',
};

export const sampleWithNewData: NewSample = {
  sampleTypeId: 'Awesome',
  analysisServiceId: 'invoice',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
