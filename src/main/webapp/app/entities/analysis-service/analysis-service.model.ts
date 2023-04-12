export interface IAnalysisService {
  id: number;
  name?: string | null;
}

export type NewAnalysisService = Omit<IAnalysisService, 'id'> & { id: null };
