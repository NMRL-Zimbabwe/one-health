import { IAnalysisService, NewAnalysisService } from './analysis-service.model';

export const sampleWithRequiredData: IAnalysisService = {
  id: 11428,
  name: 'Kenya',
};

export const sampleWithPartialData: IAnalysisService = {
  id: 82166,
  name: 'panel',
};

export const sampleWithFullData: IAnalysisService = {
  id: 68534,
  name: 'Wooden Bedfordshire',
};

export const sampleWithNewData: NewAnalysisService = {
  name: 'Extensions',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
