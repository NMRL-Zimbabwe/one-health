import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDemographicCoding } from '../demographic-coding.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../demographic-coding.test-samples';

import { DemographicCodingService } from './demographic-coding.service';

const requireRestSample: IDemographicCoding = {
  ...sampleWithRequiredData,
};

describe('DemographicCoding Service', () => {
  let service: DemographicCodingService;
  let httpMock: HttpTestingController;
  let expectedResult: IDemographicCoding | IDemographicCoding[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DemographicCodingService);
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

    it('should create a DemographicCoding', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const demographicCoding = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(demographicCoding).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DemographicCoding', () => {
      const demographicCoding = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(demographicCoding).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DemographicCoding', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DemographicCoding', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DemographicCoding', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDemographicCodingToCollectionIfMissing', () => {
      it('should add a DemographicCoding to an empty array', () => {
        const demographicCoding: IDemographicCoding = sampleWithRequiredData;
        expectedResult = service.addDemographicCodingToCollectionIfMissing([], demographicCoding);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demographicCoding);
      });

      it('should not add a DemographicCoding to an array that contains it', () => {
        const demographicCoding: IDemographicCoding = sampleWithRequiredData;
        const demographicCodingCollection: IDemographicCoding[] = [
          {
            ...demographicCoding,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDemographicCodingToCollectionIfMissing(demographicCodingCollection, demographicCoding);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DemographicCoding to an array that doesn't contain it", () => {
        const demographicCoding: IDemographicCoding = sampleWithRequiredData;
        const demographicCodingCollection: IDemographicCoding[] = [sampleWithPartialData];
        expectedResult = service.addDemographicCodingToCollectionIfMissing(demographicCodingCollection, demographicCoding);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demographicCoding);
      });

      it('should add only unique DemographicCoding to an array', () => {
        const demographicCodingArray: IDemographicCoding[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const demographicCodingCollection: IDemographicCoding[] = [sampleWithRequiredData];
        expectedResult = service.addDemographicCodingToCollectionIfMissing(demographicCodingCollection, ...demographicCodingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const demographicCoding: IDemographicCoding = sampleWithRequiredData;
        const demographicCoding2: IDemographicCoding = sampleWithPartialData;
        expectedResult = service.addDemographicCodingToCollectionIfMissing([], demographicCoding, demographicCoding2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demographicCoding);
        expect(expectedResult).toContain(demographicCoding2);
      });

      it('should accept null and undefined values', () => {
        const demographicCoding: IDemographicCoding = sampleWithRequiredData;
        expectedResult = service.addDemographicCodingToCollectionIfMissing([], null, demographicCoding, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demographicCoding);
      });

      it('should return initial array if no DemographicCoding is added', () => {
        const demographicCodingCollection: IDemographicCoding[] = [sampleWithRequiredData];
        expectedResult = service.addDemographicCodingToCollectionIfMissing(demographicCodingCollection, undefined, null);
        expect(expectedResult).toEqual(demographicCodingCollection);
      });
    });

    describe('compareDemographicCoding', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDemographicCoding(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDemographicCoding(entity1, entity2);
        const compareResult2 = service.compareDemographicCoding(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDemographicCoding(entity1, entity2);
        const compareResult2 = service.compareDemographicCoding(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDemographicCoding(entity1, entity2);
        const compareResult2 = service.compareDemographicCoding(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
