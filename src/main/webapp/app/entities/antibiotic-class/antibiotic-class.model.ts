export interface IAntibioticClass {
  id: number;
  name?: string | null;
  description?: string | null;
}

export type NewAntibioticClass = Omit<IAntibioticClass, 'id'> & { id: null };
