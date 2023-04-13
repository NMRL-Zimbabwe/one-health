export interface ILocation {
  id: number;
  name?: string | null;
  code?: string | null;
  locationType?: string | null;
  longitude?: string | null;
  latitude?: string | null;
}

export type NewLocation = Omit<ILocation, 'id'> & { id: null };
