import { ILocation, NewLocation } from './location.model';

export const sampleWithRequiredData: ILocation = {
  id: 91847,
  name: 'parsing',
};

export const sampleWithPartialData: ILocation = {
  id: 92404,
  name: 'Island',
  code: 'indigo Virginia Arkansas',
  longitude: 'Unbranded Unbranded integrate',
  latitude: 'Checking',
};

export const sampleWithFullData: ILocation = {
  id: 85052,
  name: 'enable network Steel',
  code: 'Business-focused Shoes',
  locationType: 'viral Ball real-time',
  longitude: 'Data program hacking',
  latitude: 'Books capacitor',
};

export const sampleWithNewData: NewLocation = {
  name: 'Assimilated Cambridgeshire up',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
