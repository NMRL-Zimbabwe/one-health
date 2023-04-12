import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAnalysisService } from '../analysis-service.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../analysis-service.test-samples';

import { AnalysisServiceService } from './analysis-service.service';

const requireRestSample: IAnalysisService = {
  ...sampleWithRequiredData,
};

describe('AnalysisService Service', () => {
  let service: AnalysisServiceService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnalysisService | IAnalysisService[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AnalysisServiceService);
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

    it('should create a AnalysisService', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const analysisService = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(analysisService).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnalysisService', () => {
      const analysisService = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(analysisService).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnalysisService', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnalysisService', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnalysisService', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnalysisServiceToCollectionIfMissing', () => {
      it('should add a AnalysisService to an empty array', () => {
        const analysisService: IAnalysisService = sampleWithRequiredData;
        expectedResult = service.addAnalysisServiceToCollectionIfMissing([], analysisService);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(analysisService);
      });

      it('should not add a AnalysisService to an array that contains it', () => {
        const analysisService: IAnalysisService = sampleWithRequiredData;
        const analysisServiceCollection: IAnalysisService[] = [
          {
            ...analysisService,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnalysisServiceToCollectionIfMissing(analysisServiceCollection, analysisService);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnalysisService to an array that doesn't contain it", () => {
        const analysisService: IAnalysisService = sampleWithRequiredData;
        const analysisServiceCollection: IAnalysisService[] = [sampleWithPartialData];
        expectedResult = service.addAnalysisServiceToCollectionIfMissing(analysisServiceCollection, analysisService);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(analysisService);
      });

      it('should add only unique AnalysisService to an array', () => {
        const analysisServiceArray: IAnalysisService[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const analysisServiceCollection: IAnalysisService[] = [sampleWithRequiredData];
        expectedResult = service.addAnalysisServiceToCollectionIfMissing(analysisServiceCollection, ...analysisServiceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const analysisService: IAnalysisService = sampleWithRequiredData;
        const analysisService2: IAnalysisService = sampleWithPartialData;
        expectedResult = service.addAnalysisServiceToCollectionIfMissing([], analysisService, analysisService2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(analysisService);
        expect(expectedResult).toContain(analysisService2);
      });

      it('should accept null and undefined values', () => {
        const analysisService: IAnalysisService = sampleWithRequiredData;
        expectedResult = service.addAnalysisServiceToCollectionIfMissing([], null, analysisService, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(analysisService);
      });

      it('should return initial array if no AnalysisService is added', () => {
        const analysisServiceCollection: IAnalysisService[] = [sampleWithRequiredData];
        expectedResult = service.addAnalysisServiceToCollectionIfMissing(analysisServiceCollection, undefined, null);
        expect(expectedResult).toEqual(analysisServiceCollection);
      });
    });

    describe('compareAnalysisService', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnalysisService(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnalysisService(entity1, entity2);
        const compareResult2 = service.compareAnalysisService(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnalysisService(entity1, entity2);
        const compareResult2 = service.compareAnalysisService(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnalysisService(entity1, entity2);
        const compareResult2 = service.compareAnalysisService(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
