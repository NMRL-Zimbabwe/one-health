export interface ISector {
  id: number;
  name?: string | null;
  code?: string | null;
}

export type NewSector = Omit<ISector, 'id'> & { id: null };
