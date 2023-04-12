export interface IOrgnanism {
  id: number;
  name?: string | null;
  code?: string | null;
}

export type NewOrgnanism = Omit<IOrgnanism, 'id'> & { id: null };
