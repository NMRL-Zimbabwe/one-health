import { IDistrict, NewDistrict } from './district.model';

export const sampleWithRequiredData: IDistrict = {
  id: 85653,
  name: 'Soap invoice Dong',
  provinceId: 'protocol compressing',
};

export const sampleWithPartialData: IDistrict = {
  id: 31911,
  name: 'navigating',
  provinceId: 'compressing Concrete overriding',
  latitude: 'deposit',
};

export const sampleWithFullData: IDistrict = {
  id: 81590,
  name: 'Checking',
  provinceId: 'transmitting',
  longitude: 'ivory Dollar',
  latitude: 'Italy Frozen defect',
};

export const sampleWithNewData: NewDistrict = {
  name: 'Estates',
  provinceId: 'action-items North',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
