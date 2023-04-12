import { IDemography, NewDemography } from './demography.model';

export const sampleWithRequiredData: IDemography = {
  id: 95772,
  recordId: 'TCP',
  demographicCodingId: 'deposit',
  value: 'dedicated Checking',
};

export const sampleWithPartialData: IDemography = {
  id: 55503,
  recordId: 'analyzer',
  demographicCodingId: 'viral evolve',
  value: 'Ports Borders',
};

export const sampleWithFullData: IDemography = {
  id: 65620,
  recordId: 'cohesive networks',
  demographicCodingId: 'success Ouguiya',
  value: 'modular',
};

export const sampleWithNewData: NewDemography = {
  recordId: 'silver executive Creative',
  demographicCodingId: 'Berkshire payment',
  value: 'Buckinghamshire protocol',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
