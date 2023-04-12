export interface IClient {
  id: number;
  name?: string | null;
  longitude?: string | null;
  latitude?: string | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
