export interface ICountry {
  id: number;
  name?: string | null;
  longitude?: string | null;
  latitude?: string | null;
}

export type NewCountry = Omit<ICountry, 'id'> & { id: null };
