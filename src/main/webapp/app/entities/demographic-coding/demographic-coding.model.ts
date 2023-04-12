export interface IDemographicCoding {
  id: number;
  name?: string | null;
  code?: string | null;
  description?: string | null;
}

export type NewDemographicCoding = Omit<IDemographicCoding, 'id'> & { id: null };
