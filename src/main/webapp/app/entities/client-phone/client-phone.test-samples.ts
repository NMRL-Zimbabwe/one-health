import { IClientPhone, NewClientPhone } from './client-phone.model';

export const sampleWithRequiredData: IClientPhone = {
  id: 81799,
};

export const sampleWithPartialData: IClientPhone = {
  id: 43356,
  number: 'primary JSON',
};

export const sampleWithFullData: IClientPhone = {
  id: 64416,
  number: 'Implemented',
  clientId: 'Washington solution firewall',
};

export const sampleWithNewData: NewClientPhone = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
