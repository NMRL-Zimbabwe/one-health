import { IAnalysisService, NewAnalysisService } from './analysis-service.model';

export const sampleWithRequiredData: IAnalysisService = {
  id: 11428,
  name: 'Kenya',
};

export const sampleWithPartialData: IAnalysisService = {
  id: 10982,
  name: 'Granite Connecticut Shoes',
  code: 'Granite navigating',
};

export const sampleWithFullData: IAnalysisService = {
  id: 93014,
  name: 'Small',
  code: 'real-time bus',
};

export const sampleWithNewData: NewAnalysisService = {
  name: 'loyalty',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
