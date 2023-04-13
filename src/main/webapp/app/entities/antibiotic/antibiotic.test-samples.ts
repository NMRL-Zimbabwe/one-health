import { IAntibiotic, NewAntibiotic } from './antibiotic.model';

export const sampleWithRequiredData: IAntibiotic = {
  id: 66892,
  name: 'deposit proactive',
};

export const sampleWithPartialData: IAntibiotic = {
  id: 24754,
  name: 'Dynamic Avon',
  status: 'Architect Clothing relationships',
  description: 'yellow Fresh',
};

export const sampleWithFullData: IAntibiotic = {
  id: 31201,
  name: 'sensor neural Personal',
  code: 'Reunion payment withdrawal',
  status: 'Soft SCSI Club',
  description: 'collaborative bottom-line Senior',
};

export const sampleWithNewData: NewAntibiotic = {
  name: 'Montenegro',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
