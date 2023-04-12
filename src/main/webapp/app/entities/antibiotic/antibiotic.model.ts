export interface IAntibiotic {
  id: number;
  name?: string | null;
  code?: string | null;
}

export type NewAntibiotic = Omit<IAntibiotic, 'id'> & { id: null };
