import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISampleType } from '../sample-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../sample-type.test-samples';

import { SampleTypeService } from './sample-type.service';

const requireRestSample: ISampleType = {
  ...sampleWithRequiredData,
};

describe('SampleType Service', () => {
  let service: SampleTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: ISampleType | ISampleType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SampleTypeService);
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

    it('should create a SampleType', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const sampleType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sampleType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SampleType', () => {
      const sampleType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sampleType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SampleType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SampleType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SampleType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSampleTypeToCollectionIfMissing', () => {
      it('should add a SampleType to an empty array', () => {
        const sampleType: ISampleType = sampleWithRequiredData;
        expectedResult = service.addSampleTypeToCollectionIfMissing([], sampleType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sampleType);
      });

      it('should not add a SampleType to an array that contains it', () => {
        const sampleType: ISampleType = sampleWithRequiredData;
        const sampleTypeCollection: ISampleType[] = [
          {
            ...sampleType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSampleTypeToCollectionIfMissing(sampleTypeCollection, sampleType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SampleType to an array that doesn't contain it", () => {
        const sampleType: ISampleType = sampleWithRequiredData;
        const sampleTypeCollection: ISampleType[] = [sampleWithPartialData];
        expectedResult = service.addSampleTypeToCollectionIfMissing(sampleTypeCollection, sampleType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sampleType);
      });

      it('should add only unique SampleType to an array', () => {
        const sampleTypeArray: ISampleType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sampleTypeCollection: ISampleType[] = [sampleWithRequiredData];
        expectedResult = service.addSampleTypeToCollectionIfMissing(sampleTypeCollection, ...sampleTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sampleType: ISampleType = sampleWithRequiredData;
        const sampleType2: ISampleType = sampleWithPartialData;
        expectedResult = service.addSampleTypeToCollectionIfMissing([], sampleType, sampleType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sampleType);
        expect(expectedResult).toContain(sampleType2);
      });

      it('should accept null and undefined values', () => {
        const sampleType: ISampleType = sampleWithRequiredData;
        expectedResult = service.addSampleTypeToCollectionIfMissing([], null, sampleType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sampleType);
      });

      it('should return initial array if no SampleType is added', () => {
        const sampleTypeCollection: ISampleType[] = [sampleWithRequiredData];
        expectedResult = service.addSampleTypeToCollectionIfMissing(sampleTypeCollection, undefined, null);
        expect(expectedResult).toEqual(sampleTypeCollection);
      });
    });

    describe('compareSampleType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSampleType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSampleType(entity1, entity2);
        const compareResult2 = service.compareSampleType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSampleType(entity1, entity2);
        const compareResult2 = service.compareSampleType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSampleType(entity1, entity2);
        const compareResult2 = service.compareSampleType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
