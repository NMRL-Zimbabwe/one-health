export interface IOrganism {
  id: number;
  name?: string | null;
  code?: string | null;
}

export type NewOrganism = Omit<IOrganism, 'id'> & { id: null };
