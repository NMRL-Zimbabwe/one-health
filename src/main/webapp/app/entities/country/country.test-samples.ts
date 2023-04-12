import { ICountry, NewCountry } from './country.model';

export const sampleWithRequiredData: ICountry = {
  id: 4746,
  name: 'SCSI Configuration deposit',
};

export const sampleWithPartialData: ICountry = {
  id: 63753,
  name: 'content 1080p',
};

export const sampleWithFullData: ICountry = {
  id: 12215,
  name: 'e-tailers',
  longitude: 'deposit',
  latitude: 'Progressive Martinique',
};

export const sampleWithNewData: NewCountry = {
  name: 'Oklahoma',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
