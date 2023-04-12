import { IProvince, NewProvince } from './province.model';

export const sampleWithRequiredData: IProvince = {
  id: 16991,
  name: 'models repurpose',
  countryId: 'input',
};

export const sampleWithPartialData: IProvince = {
  id: 96572,
  name: 'Salad Concrete',
  countryId: 'holistic',
  longitude: 'yellow Buckinghamshire maroon',
  latitude: 'impactful',
};

export const sampleWithFullData: IProvince = {
  id: 10823,
  name: 'orange Account',
  countryId: 'Japan hub Chair',
  longitude: 'Managed',
  latitude: 'green bypassing Tools',
};

export const sampleWithNewData: NewProvince = {
  name: 'eyeballs',
  countryId: 'Trinidad Practical',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
