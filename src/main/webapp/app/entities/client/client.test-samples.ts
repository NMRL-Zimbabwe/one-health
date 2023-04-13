import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 71655,
  name: 'hacking',
};

export const sampleWithPartialData: IClient = {
  id: 7178,
  name: 'Virginia Concrete',
  address: 'asynchronous Account Future',
  phone: '1-342-345-9877',
};

export const sampleWithFullData: IClient = {
  id: 30589,
  name: 'Reverse-engineered',
  address: 'Steel',
  phone: '519.874.3604 x1077',
};

export const sampleWithNewData: NewClient = {
  name: 'District',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
