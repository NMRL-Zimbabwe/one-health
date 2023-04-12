import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAntibiotic } from '../antibiotic.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../antibiotic.test-samples';

import { AntibioticService } from './antibiotic.service';

const requireRestSample: IAntibiotic = {
  ...sampleWithRequiredData,
};

describe('Antibiotic Service', () => {
  let service: AntibioticService;
  let httpMock: HttpTestingController;
  let expectedResult: IAntibiotic | IAntibiotic[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AntibioticService);
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

    it('should create a Antibiotic', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const antibiotic = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(antibiotic).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Antibiotic', () => {
      const antibiotic = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(antibiotic).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Antibiotic', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Antibiotic', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Antibiotic', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAntibioticToCollectionIfMissing', () => {
      it('should add a Antibiotic to an empty array', () => {
        const antibiotic: IAntibiotic = sampleWithRequiredData;
        expectedResult = service.addAntibioticToCollectionIfMissing([], antibiotic);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(antibiotic);
      });

      it('should not add a Antibiotic to an array that contains it', () => {
        const antibiotic: IAntibiotic = sampleWithRequiredData;
        const antibioticCollection: IAntibiotic[] = [
          {
            ...antibiotic,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAntibioticToCollectionIfMissing(antibioticCollection, antibiotic);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Antibiotic to an array that doesn't contain it", () => {
        const antibiotic: IAntibiotic = sampleWithRequiredData;
        const antibioticCollection: IAntibiotic[] = [sampleWithPartialData];
        expectedResult = service.addAntibioticToCollectionIfMissing(antibioticCollection, antibiotic);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(antibiotic);
      });

      it('should add only unique Antibiotic to an array', () => {
        const antibioticArray: IAntibiotic[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const antibioticCollection: IAntibiotic[] = [sampleWithRequiredData];
        expectedResult = service.addAntibioticToCollectionIfMissing(antibioticCollection, ...antibioticArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const antibiotic: IAntibiotic = sampleWithRequiredData;
        const antibiotic2: IAntibiotic = sampleWithPartialData;
        expectedResult = service.addAntibioticToCollectionIfMissing([], antibiotic, antibiotic2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(antibiotic);
        expect(expectedResult).toContain(antibiotic2);
      });

      it('should accept null and undefined values', () => {
        const antibiotic: IAntibiotic = sampleWithRequiredData;
        expectedResult = service.addAntibioticToCollectionIfMissing([], null, antibiotic, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(antibiotic);
      });

      it('should return initial array if no Antibiotic is added', () => {
        const antibioticCollection: IAntibiotic[] = [sampleWithRequiredData];
        expectedResult = service.addAntibioticToCollectionIfMissing(antibioticCollection, undefined, null);
        expect(expectedResult).toEqual(antibioticCollection);
      });
    });

    describe('compareAntibiotic', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAntibiotic(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAntibiotic(entity1, entity2);
        const compareResult2 = service.compareAntibiotic(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAntibiotic(entity1, entity2);
        const compareResult2 = service.compareAntibiotic(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAntibiotic(entity1, entity2);
        const compareResult2 = service.compareAntibiotic(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
