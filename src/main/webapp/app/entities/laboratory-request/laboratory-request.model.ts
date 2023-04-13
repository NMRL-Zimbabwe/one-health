import dayjs from 'dayjs/esm';

export interface ILaboratoryRequest {
  id: number;
  sampleId?: string | null;
  sampleTypeId?: string | null;
  dateCollected?: dayjs.Dayjs | null;
  dateReceived?: dayjs.Dayjs | null;
  sampleCondition?: string | null;
  clientId?: string | null;
  priority?: number | null;
  status?: string | null;
  remarks?: string | null;
  locationId?: string | null;
  sectorId?: string | null;
  districtId?: string | null;
  procinceId?: string | null;
}

export type NewLaboratoryRequest = Omit<ILaboratoryRequest, 'id'> & { id: null };
