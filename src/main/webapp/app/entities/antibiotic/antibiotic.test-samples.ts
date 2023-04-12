import { IAntibiotic, NewAntibiotic } from './antibiotic.model';

export const sampleWithRequiredData: IAntibiotic = {
  id: 66892,
  name: 'deposit proactive',
};

export const sampleWithPartialData: IAntibiotic = {
  id: 96820,
  name: 'Mandatory Latvian',
};

export const sampleWithFullData: IAntibiotic = {
  id: 11449,
  name: 'Strategist Books',
  code: 'relationships sticky Liaison',
};

export const sampleWithNewData: NewAntibiotic = {
  name: 'synergy',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
