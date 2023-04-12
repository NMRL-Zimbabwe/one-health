export interface IDistrict {
  id: number;
  name?: string | null;
  provinceId?: string | null;
  longitude?: string | null;
  latitude?: string | null;
}

export type NewDistrict = Omit<IDistrict, 'id'> & { id: null };
