import dayjs from 'dayjs/esm';

export interface IAnalysis {
  id: number;
  sampleId?: string | null;
  analysisServiceId?: string | null;
  result?: string | null;
  dateResulted?: dayjs.Dayjs | null;
}

export type NewAnalysis = Omit<IAnalysis, 'id'> & { id: null };
