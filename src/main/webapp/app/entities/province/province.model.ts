export interface IProvince {
  id: number;
  name?: string | null;
  countryId?: string | null;
  longitude?: string | null;
  latitude?: string | null;
}

export type NewProvince = Omit<IProvince, 'id'> & { id: null };
