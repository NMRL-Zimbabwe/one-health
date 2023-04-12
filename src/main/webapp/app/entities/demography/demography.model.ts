export interface IDemography {
  id: number;
  recordId?: string | null;
  demographicCodingId?: string | null;
  value?: string | null;
}

export type NewDemography = Omit<IDemography, 'id'> & { id: null };
