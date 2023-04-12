import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 71655,
  name: 'hacking',
};

export const sampleWithPartialData: IClient = {
  id: 7178,
  name: 'Virginia Concrete',
  longitude: 'asynchronous Account Future',
  latitude: 'Multi-tiered',
};

export const sampleWithFullData: IClient = {
  id: 17055,
  name: 'Washington synthesize',
  longitude: 'Reverse-engineered',
  latitude: 'Steel',
};

export const sampleWithNewData: NewClient = {
  name: 'reinvent PNG seamless',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
