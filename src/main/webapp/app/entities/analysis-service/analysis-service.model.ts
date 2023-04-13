export interface IAnalysisService {
  id: number;
  name?: string | null;
  code?: string | null;
}

export type NewAnalysisService = Omit<IAnalysisService, 'id'> & { id: null };
