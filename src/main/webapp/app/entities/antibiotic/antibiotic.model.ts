export interface IAntibiotic {
  id: number;
  name?: string | null;
  code?: string | null;
  status?: string | null;
  description?: string | null;
  classId?: string | null;
}

export type NewAntibiotic = Omit<IAntibiotic, 'id'> & { id: null };
