import dayjs from 'dayjs/esm';

import { ILaboratoryRequest, NewLaboratoryRequest } from './laboratory-request.model';

export const sampleWithRequiredData: ILaboratoryRequest = {
  id: 84517,
  sampleId: 'Chair Rustic Handmade',
  sampleTypeId: 'Fresh Granite',
};

export const sampleWithPartialData: ILaboratoryRequest = {
  id: 60977,
  sampleId: 'Bedfordshire Rubber',
  sampleTypeId: 'back-end synergies transitional',
  dateCollected: dayjs('2023-04-12'),
  dateReceived: dayjs('2023-04-12'),
  sampleCondition: 'Cotton',
  priority: 56034,
  locationId: 'analyzer Accountability index',
  procinceId: 'Renminbi',
};

export const sampleWithFullData: ILaboratoryRequest = {
  id: 39450,
  sampleId: 'Plastic Land',
  sampleTypeId: 'index Future',
  dateCollected: dayjs('2023-04-13'),
  dateReceived: dayjs('2023-04-12'),
  sampleCondition: 'navigating',
  clientId: 'Niger reboot',
  priority: 30938,
  status: 'Integration New bypassing',
  remarks: 'magenta firewall',
  locationId: 'Switchable deposit',
  sectorId: 'challenge',
  districtId: 'deposit Bedfordshire',
  procinceId: 'matrix',
};

export const sampleWithNewData: NewLaboratoryRequest = {
  sampleId: 'User-friendly systems database',
  sampleTypeId: 'Auto incentivize',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
