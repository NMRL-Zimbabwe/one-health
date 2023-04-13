import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ILaboratoryRequest } from '../laboratory-request.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../laboratory-request.test-samples';

import { LaboratoryRequestService, RestLaboratoryRequest } from './laboratory-request.service';

const requireRestSample: RestLaboratoryRequest = {
  ...sampleWithRequiredData,
  dateCollected: sampleWithRequiredData.dateCollected?.format(DATE_FORMAT),
  dateReceived: sampleWithRequiredData.dateReceived?.format(DATE_FORMAT),
};

describe('LaboratoryRequest Service', () => {
  let service: LaboratoryRequestService;
  let httpMock: HttpTestingController;
  let expectedResult: ILaboratoryRequest | ILaboratoryRequest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LaboratoryRequestService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a LaboratoryRequest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const laboratoryRequest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(laboratoryRequest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LaboratoryRequest', () => {
      const laboratoryRequest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(laboratoryRequest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LaboratoryRequest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LaboratoryRequest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LaboratoryRequest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLaboratoryRequestToCollectionIfMissing', () => {
      it('should add a LaboratoryRequest to an empty array', () => {
        const laboratoryRequest: ILaboratoryRequest = sampleWithRequiredData;
        expectedResult = service.addLaboratoryRequestToCollectionIfMissing([], laboratoryRequest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(laboratoryRequest);
      });

      it('should not add a LaboratoryRequest to an array that contains it', () => {
        const laboratoryRequest: ILaboratoryRequest = sampleWithRequiredData;
        const laboratoryRequestCollection: ILaboratoryRequest[] = [
          {
            ...laboratoryRequest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLaboratoryRequestToCollectionIfMissing(laboratoryRequestCollection, laboratoryRequest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LaboratoryRequest to an array that doesn't contain it", () => {
        const laboratoryRequest: ILaboratoryRequest = sampleWithRequiredData;
        const laboratoryRequestCollection: ILaboratoryRequest[] = [sampleWithPartialData];
        expectedResult = service.addLaboratoryRequestToCollectionIfMissing(laboratoryRequestCollection, laboratoryRequest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(laboratoryRequest);
      });

      it('should add only unique LaboratoryRequest to an array', () => {
        const laboratoryRequestArray: ILaboratoryRequest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const laboratoryRequestCollection: ILaboratoryRequest[] = [sampleWithRequiredData];
        expectedResult = service.addLaboratoryRequestToCollectionIfMissing(laboratoryRequestCollection, ...laboratoryRequestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const laboratoryRequest: ILaboratoryRequest = sampleWithRequiredData;
        const laboratoryRequest2: ILaboratoryRequest = sampleWithPartialData;
        expectedResult = service.addLaboratoryRequestToCollectionIfMissing([], laboratoryRequest, laboratoryRequest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(laboratoryRequest);
        expect(expectedResult).toContain(laboratoryRequest2);
      });

      it('should accept null and undefined values', () => {
        const laboratoryRequest: ILaboratoryRequest = sampleWithRequiredData;
        expectedResult = service.addLaboratoryRequestToCollectionIfMissing([], null, laboratoryRequest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(laboratoryRequest);
      });

      it('should return initial array if no LaboratoryRequest is added', () => {
        const laboratoryRequestCollection: ILaboratoryRequest[] = [sampleWithRequiredData];
        expectedResult = service.addLaboratoryRequestToCollectionIfMissing(laboratoryRequestCollection, undefined, null);
        expect(expectedResult).toEqual(laboratoryRequestCollection);
      });
    });

    describe('compareLaboratoryRequest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLaboratoryRequest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLaboratoryRequest(entity1, entity2);
        const compareResult2 = service.compareLaboratoryRequest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLaboratoryRequest(entity1, entity2);
        const compareResult2 = service.compareLaboratoryRequest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLaboratoryRequest(entity1, entity2);
        const compareResult2 = service.compareLaboratoryRequest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
