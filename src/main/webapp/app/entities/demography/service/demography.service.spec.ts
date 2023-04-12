import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDemography } from '../demography.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../demography.test-samples';

import { DemographyService } from './demography.service';

const requireRestSample: IDemography = {
  ...sampleWithRequiredData,
};

describe('Demography Service', () => {
  let service: DemographyService;
  let httpMock: HttpTestingController;
  let expectedResult: IDemography | IDemography[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DemographyService);
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

    it('should create a Demography', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const demography = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(demography).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Demography', () => {
      const demography = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(demography).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Demography', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Demography', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Demography', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDemographyToCollectionIfMissing', () => {
      it('should add a Demography to an empty array', () => {
        const demography: IDemography = sampleWithRequiredData;
        expectedResult = service.addDemographyToCollectionIfMissing([], demography);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demography);
      });

      it('should not add a Demography to an array that contains it', () => {
        const demography: IDemography = sampleWithRequiredData;
        const demographyCollection: IDemography[] = [
          {
            ...demography,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDemographyToCollectionIfMissing(demographyCollection, demography);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Demography to an array that doesn't contain it", () => {
        const demography: IDemography = sampleWithRequiredData;
        const demographyCollection: IDemography[] = [sampleWithPartialData];
        expectedResult = service.addDemographyToCollectionIfMissing(demographyCollection, demography);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demography);
      });

      it('should add only unique Demography to an array', () => {
        const demographyArray: IDemography[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const demographyCollection: IDemography[] = [sampleWithRequiredData];
        expectedResult = service.addDemographyToCollectionIfMissing(demographyCollection, ...demographyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const demography: IDemography = sampleWithRequiredData;
        const demography2: IDemography = sampleWithPartialData;
        expectedResult = service.addDemographyToCollectionIfMissing([], demography, demography2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(demography);
        expect(expectedResult).toContain(demography2);
      });

      it('should accept null and undefined values', () => {
        const demography: IDemography = sampleWithRequiredData;
        expectedResult = service.addDemographyToCollectionIfMissing([], null, demography, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(demography);
      });

      it('should return initial array if no Demography is added', () => {
        const demographyCollection: IDemography[] = [sampleWithRequiredData];
        expectedResult = service.addDemographyToCollectionIfMissing(demographyCollection, undefined, null);
        expect(expectedResult).toEqual(demographyCollection);
      });
    });

    describe('compareDemography', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDemography(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDemography(entity1, entity2);
        const compareResult2 = service.compareDemography(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDemography(entity1, entity2);
        const compareResult2 = service.compareDemography(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDemography(entity1, entity2);
        const compareResult2 = service.compareDemography(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
