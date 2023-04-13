export interface IClientPhone {
  id: number;
  number?: string | null;
  clientId?: string | null;
}

export type NewClientPhone = Omit<IClientPhone, 'id'> & { id: null };
