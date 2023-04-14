import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAntibioticClass } from '../antibiotic-class.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../antibiotic-class.test-samples';

import { AntibioticClassService } from './antibiotic-class.service';

const requireRestSample: IAntibioticClass = {
  ...sampleWithRequiredData,
};

describe('AntibioticClass Service', () => {
  let service: AntibioticClassService;
  let httpMock: HttpTestingController;
  let expectedResult: IAntibioticClass | IAntibioticClass[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AntibioticClassService);
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

    it('should create a AntibioticClass', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const antibioticClass = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(antibioticClass).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AntibioticClass', () => {
      const antibioticClass = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(antibioticClass).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AntibioticClass', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AntibioticClass', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AntibioticClass', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAntibioticClassToCollectionIfMissing', () => {
      it('should add a AntibioticClass to an empty array', () => {
        const antibioticClass: IAntibioticClass = sampleWithRequiredData;
        expectedResult = service.addAntibioticClassToCollectionIfMissing([], antibioticClass);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(antibioticClass);
      });

      it('should not add a AntibioticClass to an array that contains it', () => {
        const antibioticClass: IAntibioticClass = sampleWithRequiredData;
        const antibioticClassCollection: IAntibioticClass[] = [
          {
            ...antibioticClass,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAntibioticClassToCollectionIfMissing(antibioticClassCollection, antibioticClass);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AntibioticClass to an array that doesn't contain it", () => {
        const antibioticClass: IAntibioticClass = sampleWithRequiredData;
        const antibioticClassCollection: IAntibioticClass[] = [sampleWithPartialData];
        expectedResult = service.addAntibioticClassToCollectionIfMissing(antibioticClassCollection, antibioticClass);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(antibioticClass);
      });

      it('should add only unique AntibioticClass to an array', () => {
        const antibioticClassArray: IAntibioticClass[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const antibioticClassCollection: IAntibioticClass[] = [sampleWithRequiredData];
        expectedResult = service.addAntibioticClassToCollectionIfMissing(antibioticClassCollection, ...antibioticClassArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const antibioticClass: IAntibioticClass = sampleWithRequiredData;
        const antibioticClass2: IAntibioticClass = sampleWithPartialData;
        expectedResult = service.addAntibioticClassToCollectionIfMissing([], antibioticClass, antibioticClass2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(antibioticClass);
        expect(expectedResult).toContain(antibioticClass2);
      });

      it('should accept null and undefined values', () => {
        const antibioticClass: IAntibioticClass = sampleWithRequiredData;
        expectedResult = service.addAntibioticClassToCollectionIfMissing([], null, antibioticClass, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(antibioticClass);
      });

      it('should return initial array if no AntibioticClass is added', () => {
        const antibioticClassCollection: IAntibioticClass[] = [sampleWithRequiredData];
        expectedResult = service.addAntibioticClassToCollectionIfMissing(antibioticClassCollection, undefined, null);
        expect(expectedResult).toEqual(antibioticClassCollection);
      });
    });

    describe('compareAntibioticClass', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAntibioticClass(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAntibioticClass(entity1, entity2);
        const compareResult2 = service.compareAntibioticClass(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAntibioticClass(entity1, entity2);
        const compareResult2 = service.compareAntibioticClass(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAntibioticClass(entity1, entity2);
        const compareResult2 = service.compareAntibioticClass(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
