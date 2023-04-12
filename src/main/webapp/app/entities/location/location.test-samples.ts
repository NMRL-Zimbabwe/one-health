import { ILocation, NewLocation } from './location.model';

export const sampleWithRequiredData: ILocation = {
  id: 91847,
  name: 'parsing',
};

export const sampleWithPartialData: ILocation = {
  id: 73848,
  name: 'Beauty open 1080p',
  code: 'hard Slovakia Directives',
  latitude: 'navigating solutions Fresh',
};

export const sampleWithFullData: ILocation = {
  id: 14345,
  name: 'network',
  code: 'Small',
  longitude: 'Shoes',
  latitude: 'viral Ball real-time',
};

export const sampleWithNewData: NewLocation = {
  name: 'Data program hacking',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
