import dayjs from 'dayjs/esm';

import { IAnalysis, NewAnalysis } from './analysis.model';

export const sampleWithRequiredData: IAnalysis = {
  id: 10018,
  sampleId: 'Lakes Synchronised',
  analysisServiceId: 'connect Coordinator bypassing',
};

export const sampleWithPartialData: IAnalysis = {
  id: 53138,
  sampleId: 'Mexico Bahraini Colorado',
  analysisServiceId: 'transmitter West',
  result: 'XML digital Liechtenstein',
  dateResulted: dayjs('2023-04-11'),
};

export const sampleWithFullData: IAnalysis = {
  id: 7624,
  sampleId: 'metrics Mobility',
  analysisServiceId: 'enable AGP',
  result: 'generate',
  dateResulted: dayjs('2023-04-12'),
};

export const sampleWithNewData: NewAnalysis = {
  sampleId: 'Peso',
  analysisServiceId: 'backing',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
