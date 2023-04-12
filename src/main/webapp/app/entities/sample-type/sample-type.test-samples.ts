import { ISampleType, NewSampleType } from './sample-type.model';

export const sampleWithRequiredData: ISampleType = {
  id: 64866,
  name: 'Steel',
};

export const sampleWithPartialData: ISampleType = {
  id: 3203,
  name: 'bandwidth Canada Cotton',
  code: 'SMTP mindshare Latvia',
};

export const sampleWithFullData: ISampleType = {
  id: 51765,
  name: 'solutions',
  code: 'Buckinghamshire Assurance embrace',
};

export const sampleWithNewData: NewSampleType = {
  name: 'Sleek grey',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
