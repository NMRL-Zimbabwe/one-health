import { IClientAddress, NewClientAddress } from './client-address.model';

export const sampleWithRequiredData: IClientAddress = {
  id: 63095,
};

export const sampleWithPartialData: IClientAddress = {
  id: 76184,
  name: 'database reboot',
  clientId: 'deposit silver',
};

export const sampleWithFullData: IClientAddress = {
  id: 29132,
  name: 'invoice bypass',
  clientId: 'Sports Armenian',
};

export const sampleWithNewData: NewClientAddress = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
