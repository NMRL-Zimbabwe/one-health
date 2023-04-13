import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOrganism } from '../organism.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../organism.test-samples';

import { OrganismService } from './organism.service';

const requireRestSample: IOrganism = {
  ...sampleWithRequiredData,
};

describe('Organism Service', () => {
  let service: OrganismService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrganism | IOrganism[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrganismService);
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

    it('should create a Organism', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const organism = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(organism).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Organism', () => {
      const organism = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(organism).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Organism', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Organism', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Organism', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrganismToCollectionIfMissing', () => {
      it('should add a Organism to an empty array', () => {
        const organism: IOrganism = sampleWithRequiredData;
        expectedResult = service.addOrganismToCollectionIfMissing([], organism);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organism);
      });

      it('should not add a Organism to an array that contains it', () => {
        const organism: IOrganism = sampleWithRequiredData;
        const organismCollection: IOrganism[] = [
          {
            ...organism,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrganismToCollectionIfMissing(organismCollection, organism);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Organism to an array that doesn't contain it", () => {
        const organism: IOrganism = sampleWithRequiredData;
        const organismCollection: IOrganism[] = [sampleWithPartialData];
        expectedResult = service.addOrganismToCollectionIfMissing(organismCollection, organism);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organism);
      });

      it('should add only unique Organism to an array', () => {
        const organismArray: IOrganism[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const organismCollection: IOrganism[] = [sampleWithRequiredData];
        expectedResult = service.addOrganismToCollectionIfMissing(organismCollection, ...organismArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const organism: IOrganism = sampleWithRequiredData;
        const organism2: IOrganism = sampleWithPartialData;
        expectedResult = service.addOrganismToCollectionIfMissing([], organism, organism2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(organism);
        expect(expectedResult).toContain(organism2);
      });

      it('should accept null and undefined values', () => {
        const organism: IOrganism = sampleWithRequiredData;
        expectedResult = service.addOrganismToCollectionIfMissing([], null, organism, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(organism);
      });

      it('should return initial array if no Organism is added', () => {
        const organismCollection: IOrganism[] = [sampleWithRequiredData];
        expectedResult = service.addOrganismToCollectionIfMissing(organismCollection, undefined, null);
        expect(expectedResult).toEqual(organismCollection);
      });
    });

    describe('compareOrganism', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrganism(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOrganism(entity1, entity2);
        const compareResult2 = service.compareOrganism(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOrganism(entity1, entity2);
        const compareResult2 = service.compareOrganism(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOrganism(entity1, entity2);
        const compareResult2 = service.compareOrganism(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
