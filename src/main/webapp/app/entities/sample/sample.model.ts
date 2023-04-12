export interface ISample {
  id: number;
  sampleTypeId?: string | null;
  analysisServiceId?: string | null;
  longitude?: string | null;
  latitude?: string | null;
}

export type NewSample = Omit<ISample, 'id'> & { id: null };
