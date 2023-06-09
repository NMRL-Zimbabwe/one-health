export interface IClient {
  id: number;
  name?: string | null;
  address?: string | null;
  phone?: string | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
