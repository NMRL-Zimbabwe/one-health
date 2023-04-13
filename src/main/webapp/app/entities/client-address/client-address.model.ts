export interface IClientAddress {
  id: number;
  name?: string | null;
  clientId?: string | null;
}

export type NewClientAddress = Omit<IClientAddress, 'id'> & { id: null };
