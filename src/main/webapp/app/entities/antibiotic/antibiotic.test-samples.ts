import { IAntibiotic, NewAntibiotic } from './antibiotic.model';

export const sampleWithRequiredData: IAntibiotic = {
  id: 66892,
  name: 'deposit proactive',
  classId: 'Analyst experiences',
};

export const sampleWithPartialData: IAntibiotic = {
  id: 53134,
  name: 'Architect Clothing relationships',
  classId: 'yellow Fresh',
};

export const sampleWithFullData: IAntibiotic = {
  id: 31201,
  name: 'sensor neural Personal',
  code: 'Reunion payment withdrawal',
  status: 'Soft SCSI Club',
  description: 'collaborative bottom-line Senior',
  classId: 'Montenegro',
};

export const sampleWithNewData: NewAntibiotic = {
  name: 'Orchestrator Analyst payment',
  classId: 'payment Berkshire Supervisor',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
