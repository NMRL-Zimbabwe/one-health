import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOrgnanism } from '../orgnanism.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../orgnanism.test-samples';

import { OrgnanismService } from './orgnanism.service';

const requireRestSample: IOrgnanism = {
  ...sampleWithRequiredData,
};

describe('Orgnanism Service', () => {
  let service: OrgnanismService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrgnanism | IOrgnanism[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrgnanismService);
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

    it('should create a Orgnanism', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const orgnanism = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(orgnanism).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Orgnanism', () => {
      const orgnanism = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(orgnanism).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Orgnanism', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Orgnanism', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Orgnanism', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrgnanismToCollectionIfMissing', () => {
      it('should add a Orgnanism to an empty array', () => {
        const orgnanism: IOrgnanism = sampleWithRequiredData;
        expectedResult = service.addOrgnanismToCollectionIfMissing([], orgnanism);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orgnanism);
      });

      it('should not add a Orgnanism to an array that contains it', () => {
        const orgnanism: IOrgnanism = sampleWithRequiredData;
        const orgnanismCollection: IOrgnanism[] = [
          {
            ...orgnanism,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrgnanismToCollectionIfMissing(orgnanismCollection, orgnanism);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Orgnanism to an array that doesn't contain it", () => {
        const orgnanism: IOrgnanism = sampleWithRequiredData;
        const orgnanismCollection: IOrgnanism[] = [sampleWithPartialData];
        expectedResult = service.addOrgnanismToCollectionIfMissing(orgnanismCollection, orgnanism);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orgnanism);
      });

      it('should add only unique Orgnanism to an array', () => {
        const orgnanismArray: IOrgnanism[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const orgnanismCollection: IOrgnanism[] = [sampleWithRequiredData];
        expectedResult = service.addOrgnanismToCollectionIfMissing(orgnanismCollection, ...orgnanismArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const orgnanism: IOrgnanism = sampleWithRequiredData;
        const orgnanism2: IOrgnanism = sampleWithPartialData;
        expectedResult = service.addOrgnanismToCollectionIfMissing([], orgnanism, orgnanism2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orgnanism);
        expect(expectedResult).toContain(orgnanism2);
      });

      it('should accept null and undefined values', () => {
        const orgnanism: IOrgnanism = sampleWithRequiredData;
        expectedResult = service.addOrgnanismToCollectionIfMissing([], null, orgnanism, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orgnanism);
      });

      it('should return initial array if no Orgnanism is added', () => {
        const orgnanismCollection: IOrgnanism[] = [sampleWithRequiredData];
        expectedResult = service.addOrgnanismToCollectionIfMissing(orgnanismCollection, undefined, null);
        expect(expectedResult).toEqual(orgnanismCollection);
      });
    });

    describe('compareOrgnanism', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrgnanism(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOrgnanism(entity1, entity2);
        const compareResult2 = service.compareOrgnanism(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOrgnanism(entity1, entity2);
        const compareResult2 = service.compareOrgnanism(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOrgnanism(entity1, entity2);
        const compareResult2 = service.compareOrgnanism(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
