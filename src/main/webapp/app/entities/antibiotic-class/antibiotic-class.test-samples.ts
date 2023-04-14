import { IAntibioticClass, NewAntibioticClass } from './antibiotic-class.model';

export const sampleWithRequiredData: IAntibioticClass = {
  id: 9532,
};

export const sampleWithPartialData: IAntibioticClass = {
  id: 18666,
};

export const sampleWithFullData: IAntibioticClass = {
  id: 72373,
  name: 'SMS Park',
  description: 'interface Graphical array',
};

export const sampleWithNewData: NewAntibioticClass = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
