import { IOrganism, NewOrganism } from './organism.model';

export const sampleWithRequiredData: IOrganism = {
  id: 84265,
  name: 'model niches',
};

export const sampleWithPartialData: IOrganism = {
  id: 86955,
  name: 'Guilder',
  code: 'aggregate Rubber system',
};

export const sampleWithFullData: IOrganism = {
  id: 29511,
  name: 'primary',
  code: 'revolutionize Loan unleash',
};

export const sampleWithNewData: NewOrganism = {
  name: 'Peso Rial',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
