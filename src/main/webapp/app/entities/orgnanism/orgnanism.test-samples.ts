import { IOrgnanism, NewOrgnanism } from './orgnanism.model';

export const sampleWithRequiredData: IOrgnanism = {
  id: 99669,
  name: 'payment green',
};

export const sampleWithPartialData: IOrgnanism = {
  id: 51746,
  name: 'engage well-modulated parsing',
  code: 'Nevada',
};

export const sampleWithFullData: IOrgnanism = {
  id: 93525,
  name: 'Light',
  code: 'Cove Investment',
};

export const sampleWithNewData: NewOrgnanism = {
  name: 'adapter SCSI Garden',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
