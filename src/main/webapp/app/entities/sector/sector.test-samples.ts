import { ISector, NewSector } from './sector.model';

export const sampleWithRequiredData: ISector = {
  id: 20670,
  name: 'Accountability green',
};

export const sampleWithPartialData: ISector = {
  id: 64817,
  name: 'Concrete Virginia Tunnel',
  code: 'Card one-to-one explicit',
};

export const sampleWithFullData: ISector = {
  id: 56054,
  name: 'Directives Handmade Mississippi',
  code: 'Concrete',
};

export const sampleWithNewData: NewSector = {
  name: 'Account Account',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
